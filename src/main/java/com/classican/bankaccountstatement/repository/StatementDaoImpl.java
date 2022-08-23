package com.classican.bankaccountstatement.repository;

import com.classican.bankaccountstatement.utils.StatementUtils;
import com.classican.bankaccountstatement.model.Statement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Statement Data Access layer Implementation
 */
@Slf4j
@Repository
public class StatementDaoImpl implements StatementDao {

    private static final String ID = "id";
    private static final String DATE_FIELD = "datefield";
    private static final String AMOUNT = "amount";
    private static final String ACCOUNT_ID = "account_id";
    private static final String SELECT_WHERE = "SELECT * FROM statement WHERE "+ACCOUNT_ID+"=? ORDER BY id";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Statement> getStatement(long accountId) {
        log.debug("Fetching Account Statement Details... Account Id: {}", accountId);
        return jdbcTemplate.query(SELECT_WHERE, (rs, rowNum) -> Statement.builder()
                .id(rs.getLong(ID))
                .accountId(rs.getLong(ACCOUNT_ID))
                .dateField(StatementUtils.convertToDate(rs.getString(DATE_FIELD)))
                .amount(new BigDecimal(rs.getString(AMOUNT)))
                .build(), accountId);
    }
}
