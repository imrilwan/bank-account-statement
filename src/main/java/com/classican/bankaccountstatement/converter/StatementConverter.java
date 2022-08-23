package com.classican.bankaccountstatement.converter;

import com.classican.bankaccountstatement.model.Account;
import com.classican.bankaccountstatement.model.Statement;
import com.classican.bankaccountstatement.model.dto.response.AccountStatement;
import com.classican.bankaccountstatement.model.dto.response.StatementResponse;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * Account Statement Converter
 */
public class StatementConverter {

    public static AccountStatement convertToAccountStatement(Account account, List<Statement> statements) {
        return AccountStatement.builder()
                .accountId(account.getId())
                .accountType(account.getAccountType())
                .accountNumber(account.getAccountNumber())
                .statements(statements.stream().map(StatementConverter::convertToStatementResponse).collect(Collectors.toList()))
                .build();
    }

    public static StatementResponse convertToStatementResponse(Statement statement) {
        return StatementResponse.builder()
                .id(statement.getId())
                .date(statement.getDateField())
                .amount(statement.getAmount())
                .build();
    }

    private StatementConverter() {
    }
}
