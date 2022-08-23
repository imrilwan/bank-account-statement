package com.classican.bankaccountstatement.controller;

import com.classican.bankaccountstatement.config.JwtUtil;
import com.classican.bankaccountstatement.model.dto.request.AuthUser;
import com.classican.bankaccountstatement.model.dto.response.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * User Authentication Controller
 */
@RestController
@RequestMapping
public class UserAuthenticationController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public JwtResponse createAuthToken(@RequestBody @Valid AuthUser authUser) {
        authenticate(authUser.getUsername(), authUser.getPassword());
        return new JwtResponse(jwtUtil.generateToken(authUser.getUsername()));
    }

    private void authenticate(String username, String password) throws DisabledException, BadCredentialsException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new DisabledException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", e);
        }
    }
}
