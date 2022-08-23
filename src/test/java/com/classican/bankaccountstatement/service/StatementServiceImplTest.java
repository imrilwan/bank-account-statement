package com.classican.bankaccountstatement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.classican.bankaccountstatement.model.Account;
import com.classican.bankaccountstatement.model.Statement;
import com.classican.bankaccountstatement.model.dto.request.ParameterFilter;
import com.classican.bankaccountstatement.model.dto.response.AccountStatement;
import com.classican.bankaccountstatement.model.dto.response.StatementResponse;
import com.classican.bankaccountstatement.model.enums.FilterType;
import com.classican.bankaccountstatement.repository.AccountDao;
import com.classican.bankaccountstatement.repository.StatementDao;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {StatementServiceImpl.class})
@ExtendWith(SpringExtension.class)
class StatementServiceImplTest {

    private static final Long ACCOUNT_ID = 1L;
    private static final String ACCOUNT_NUMBER = "001846681047";
    private static final String ACCOUNT_TYPE = "savings";

    @MockBean
    private AccountDao accountDao;

    @MockBean
    private StatementDao statementDao;

    @Autowired
    private StatementServiceImpl statementServiceImpl;

    @Test
    void testGetStatementDefaultFilter() {
        ArrayList<Statement> statementList = new ArrayList<>();
        when(statementDao.getStatement(ACCOUNT_ID)).thenReturn(statementList);

        Account account = mock(Account.class);
        when(account.getAccountNumber()).thenReturn(ACCOUNT_NUMBER);
        when(account.getAccountType()).thenReturn(ACCOUNT_TYPE);
        when(account.getId()).thenReturn(ACCOUNT_ID);
        when(accountDao.getAccount(ACCOUNT_ID)).thenReturn(account);

        AccountStatement actualStatement = statementServiceImpl.getStatement(ACCOUNT_ID, new ParameterFilter(),
                FilterType.DEFAULT);

        assertEquals(ACCOUNT_ID, actualStatement.getAccountId());
        assertEquals(statementList, actualStatement.getStatements());
        assertEquals(ACCOUNT_TYPE, actualStatement.getAccountType());
        assertEquals(String.valueOf(ACCOUNT_NUMBER.hashCode()), actualStatement.getAccountNumber());

        verify(statementDao).getStatement(ACCOUNT_ID);
        verify(accountDao).getAccount(ACCOUNT_ID);
        verify(account).getAccountNumber();
        verify(account).getAccountType();
        verify(account).getId();
    }

    @Test
    void testGetStatementAmountFilter() {
        ArrayList<Statement> statementList = new ArrayList<>();
        when(statementDao.getStatement(ACCOUNT_ID)).thenReturn(statementList);

        Account account = mock(Account.class);
        when(account.getAccountNumber()).thenReturn(ACCOUNT_NUMBER);
        when(account.getAccountType()).thenReturn(ACCOUNT_TYPE);
        when(account.getId()).thenReturn(ACCOUNT_ID);
        when(accountDao.getAccount(ACCOUNT_ID)).thenReturn(account);

        ParameterFilter param = new ParameterFilter();
        param.setFromAmount(BigDecimal.TEN);
        param.setToAmount(new BigDecimal(100));

        AccountStatement actualStatement = statementServiceImpl.getStatement(ACCOUNT_ID, param, FilterType.AMOUNT_FILTER);

        assertEquals(ACCOUNT_ID, actualStatement.getAccountId());
        assertEquals(statementList, actualStatement.getStatements());
        assertEquals(ACCOUNT_TYPE, actualStatement.getAccountType());
        assertEquals(String.valueOf(ACCOUNT_NUMBER.hashCode()), actualStatement.getAccountNumber());

        verify(statementDao).getStatement(ACCOUNT_ID);
        verify(accountDao).getAccount(ACCOUNT_ID);
        verify(account).getAccountNumber();
        verify(account).getAccountType();
        verify(account).getId();
    }

    @Test
    void testGetStatementDateFilter() {
        ArrayList<Statement> statementList = new ArrayList<>();
        when(statementDao.getStatement(ACCOUNT_ID)).thenReturn(statementList);

        Account account = mock(Account.class);
        when(account.getAccountNumber()).thenReturn(ACCOUNT_NUMBER);
        when(account.getAccountType()).thenReturn(ACCOUNT_TYPE);
        when(account.getId()).thenReturn(ACCOUNT_ID);
        when(accountDao.getAccount(ACCOUNT_ID)).thenReturn(account);

        ParameterFilter param = new ParameterFilter();
        param.setToDate(new Date());
        param.setToDate(new Date());

        AccountStatement actualStatement = statementServiceImpl.getStatement(ACCOUNT_ID, param, FilterType.DATE_FILTER);

        assertEquals(ACCOUNT_ID, actualStatement.getAccountId());
        assertEquals(statementList, actualStatement.getStatements());
        assertEquals(ACCOUNT_TYPE, actualStatement.getAccountType());
        assertEquals(String.valueOf(ACCOUNT_NUMBER.hashCode()), actualStatement.getAccountNumber());

        verify(statementDao).getStatement(ACCOUNT_ID);
        verify(accountDao).getAccount(ACCOUNT_ID);
        verify(account).getAccountNumber();
        verify(account).getAccountType();
        verify(account).getId();
    }
}

