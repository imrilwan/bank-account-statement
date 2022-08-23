package com.classican.bankaccountstatement.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Statement Model
 */
@Data
@Builder
public class Statement {

    private long id;
    private long accountId;
    private Date dateField;
    private BigDecimal amount;
}
