package com.classican.bankaccountstatement.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Request Parameter Filter
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
public class ParameterFilter {

    private Date fromDate;
    private Date toDate;
    private BigDecimal fromAmount;
    private BigDecimal toAmount;
}
