package com.classican.bankaccountstatement.model;

import lombok.Builder;
import lombok.Data;

/**
 * Account model
 */
@Data
@Builder
public class Account {

    private long id;
    private String accountType;
    private String accountNumber;
}
