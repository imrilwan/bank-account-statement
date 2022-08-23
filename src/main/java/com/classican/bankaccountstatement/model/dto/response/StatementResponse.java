package com.classican.bankaccountstatement.model.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * Account Statement Response
 */
@Data
@Builder
public class StatementResponse {
    private long id;
    private Date date;
    private BigDecimal amount;
}
