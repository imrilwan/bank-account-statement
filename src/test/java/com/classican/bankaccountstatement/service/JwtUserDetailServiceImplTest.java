package com.classican.bankaccountstatement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.classican.bankaccountstatement.model.ApplicationUser;
import com.classican.bankaccountstatement.model.dto.request.ApplicationUserDTO;
import com.classican.bankaccountstatement.repository.UserDao;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ContextConfiguration(classes = {JwtUserDetailServiceImpl.class})
@ExtendWith(SpringExtension.class)
class JwtUserDetailServiceImplTest {

    private static final String ADMIN = "admin";
    private static final String ADMIN_ROLE = "ADMIN";

    @Autowired
    private JwtUserDetailServiceImpl jwtUserDetailServiceImpl;

    @MockBean
    private UserDao userDao;

    @Test
    void testLoadUserByUsername() throws UsernameNotFoundException {
        ApplicationUser applicationUser = mock(ApplicationUser.class);
        when(applicationUser.getId()).thenReturn(1L);
        when(applicationUser.getPassword()).thenReturn(ADMIN);
        when(applicationUser.getUsername()).thenReturn(ADMIN);
        when(applicationUser.getRole()).thenReturn(ADMIN_ROLE);
        when(userDao.getApplicationUserByUsername(anyString())).thenReturn(applicationUser);

        List<GrantedAuthority> adminAuthority = new ArrayList<>();
        adminAuthority.add(new SimpleGrantedAuthority("ROLE_"+ ADMIN_ROLE));
        ApplicationUserDTO dto = new ApplicationUserDTO(ADMIN, ADMIN, adminAuthority);
        dto.setUserId(1L);

        UserDetails actual = jwtUserDetailServiceImpl.loadUserByUsername(ADMIN);

        assertEquals(dto.getUsername(), actual.getUsername());
        assertEquals(dto.getUsername(), actual.getPassword());
        assertEquals(1, ((ApplicationUserDTO) actual).getUserId());

        verify(userDao).getApplicationUserByUsername(ADMIN);
        verify(applicationUser).getId();
        verify(applicationUser).getUsername();
        verify(applicationUser).getPassword();
        verify(applicationUser).getRole();
    }
}

