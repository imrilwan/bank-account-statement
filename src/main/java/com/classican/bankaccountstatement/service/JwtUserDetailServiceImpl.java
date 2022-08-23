package com.classican.bankaccountstatement.service;

import com.classican.bankaccountstatement.model.ApplicationUser;
import com.classican.bankaccountstatement.model.dto.request.ApplicationUserDTO;
import com.classican.bankaccountstatement.repository.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * JWT User Detail Implementation
 */
@Slf4j
@Service
public class JwtUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser user = userDao.getApplicationUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        List<GrantedAuthority> adminAuthority = new ArrayList<>();
        adminAuthority.add(new SimpleGrantedAuthority("ROLE_"+ user.getRole()));

        ApplicationUserDTO userDTO = new ApplicationUserDTO(user.getUsername(), user.getPassword(), adminAuthority);
        userDTO.setUserId(user.getId());
        return userDTO;
    }
}
