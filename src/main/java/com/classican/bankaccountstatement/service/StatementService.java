package com.classican.bankaccountstatement.service;

import com.classican.bankaccountstatement.model.enums.FilterType;
import com.classican.bankaccountstatement.model.dto.request.ParameterFilter;
import com.classican.bankaccountstatement.model.dto.response.AccountStatement;

/**
 * Statement Service
 */
public interface StatementService {

    AccountStatement getStatement(long accountId, ParameterFilter parameterFilter, FilterType filterType);
}
