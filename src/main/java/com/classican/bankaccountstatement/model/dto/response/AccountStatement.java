package com.classican.bankaccountstatement.model.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 *
 * Account Statement Response
 */
@Data
@Builder
public class AccountStatement {

    private long accountId;
    private String accountNumber;
    private String accountType;
    private List<StatementResponse> statements;

    public String getAccountNumber() {
        return !accountNumber.isEmpty() ? String.valueOf(accountNumber.hashCode()) : accountNumber;
    }
}
