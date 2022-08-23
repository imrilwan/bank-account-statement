package com.classican.bankaccountstatement.repository;

import com.classican.bankaccountstatement.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * Account Dao Implementation
 */
@Slf4j
@Repository
public class AccountDaoImpl implements AccountDao {

    private static final String ID = "id";
    private static final String ACCOUNT_TYPE = "account_type";
    private static final String ACCOUNT_NUMBER = "account_number";
    private static final String SELECT_WHERE = "SELECT * FROM account WHERE "+ID+"=? ORDER BY id";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Account getAccount(long accountId) {
        log.debug("Fetching Account Details... Account Id: {}", accountId);
        try {
            return jdbcTemplate.queryForObject(SELECT_WHERE, (rs, rowNum) -> Account.builder()
                    .id(rs.getLong(ID))
                    .accountNumber(rs.getString(ACCOUNT_TYPE))
                    .accountType(rs.getString(ACCOUNT_NUMBER))
                    .build(), accountId);
        } catch (EmptyResultDataAccessException e) {
            log.error("Account Not Found. Account Id: {}", accountId);
            return null;
        }
    }
}
