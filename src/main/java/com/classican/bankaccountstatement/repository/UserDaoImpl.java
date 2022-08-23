package com.classican.bankaccountstatement.repository;

import com.classican.bankaccountstatement.model.ApplicationUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * User Data Access layer Implementation
 */
@Slf4j
@Repository
public class UserDaoImpl implements UserDao {

    private static final String ID = "id";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String ROLE = "role";
    private static final String SELECT_WHERE = "SELECT u.id, u.username, u.password, r.role FROM user u INNER JOIN role r ON r.id = u.role_id where username =?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ApplicationUser getApplicationUserByUsername(String username) {
        log.debug("Fetching User Details... username: {}", username);
        try {
            return jdbcTemplate.queryForObject(SELECT_WHERE, (rs, rowNum) -> ApplicationUser.builder()
                    .id(rs.getLong(ID))
                    .username(rs.getString(USERNAME))
                    .password(rs.getString(PASSWORD))
                    .role(rs.getString(ROLE))
                    .build(), username);
        } catch (EmptyResultDataAccessException e) {
            log.error("User Not Found. Username: {}", username);
            return null;
        }
    }
}
