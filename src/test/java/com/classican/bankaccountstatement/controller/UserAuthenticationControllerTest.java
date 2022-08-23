package com.classican.bankaccountstatement.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.classican.bankaccountstatement.config.JwtUtil;
import com.classican.bankaccountstatement.model.dto.request.AuthUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserAuthenticationController.class})
@ExtendWith(SpringExtension.class)
class UserAuthenticationControllerTest {

    private static final String JWT_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibm" +
            "FtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
    private static final String AUTHENTICATE = "/authenticate";

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private UserAuthenticationController userAuthenticationController;

    @Test
    void testCreateAuthToken() throws Exception {
        when(jwtUtil.generateToken(anyString())).thenReturn(JWT_TOKEN);
        when(authenticationManager.authenticate(any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));

        AuthUser authUser = new AuthUser();
        authUser.setPassword("admin");
        authUser.setUsername("admin");

        String content = (new ObjectMapper()).writeValueAsString(authUser);

        MockMvcBuilders.standaloneSetup(userAuthenticationController)
                .build()
                .perform(MockMvcRequestBuilders.post(AUTHENTICATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.content().string("{\"token\":\"" + JWT_TOKEN + "\"}"));
    }
}

