package com.classican.bankaccountstatement.config;

import com.classican.bankaccountstatement.exception.UnauthorizedAccessException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.server.ExportException;

/**
 *
 * JWT FIlter
 */
@Slf4j
@CrossOrigin
@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String AUTHENTICATE = "authenticate";

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws UnauthorizedAccessException, ServletException, IOException {

        String token = httpServletRequest.getHeader(AUTHORIZATION);
        String userName = null;

        if (StringUtils.isNotEmpty(token)) {
            try {
                userName = jwtUtil.extractUsername(token);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Unable to get JWT Token", e);
            } catch (ExpiredJwtException e) {
                throw new ExportException("JWT Token has expired", e);
            }
        }
        if (StringUtils.isNotEmpty(userName) && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            if (jwtUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        String requestURI = httpServletRequest.getRequestURI();

        if (requestURI != null && !requestURI.equals(AUTHENTICATE)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }
}
