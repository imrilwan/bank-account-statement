package com.classican.bankaccountstatement.controller;

import com.classican.bankaccountstatement.controller.logging.ControllerLogging;
import com.classican.bankaccountstatement.model.dto.request.ParameterFilter;
import com.classican.bankaccountstatement.model.dto.response.AccountStatement;
import com.classican.bankaccountstatement.model.enums.FilterType;
import com.classican.bankaccountstatement.service.StatementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Statement Controller
 */
@RestController
@RequestMapping("/api/v1/statement")
public class StatementController {

    private static final String ACCOUNT_ID = "accountId";
    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private static final String NUMBER_FORMAT = "###.###,##";

    @Autowired
    private StatementService statementService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RequestSpecification requestSpecification;

    @ControllerLogging
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{" + ACCOUNT_ID + "}")
    public AccountStatement getStatements(@PathVariable(ACCOUNT_ID) long accountId,
                                          @RequestParam(name = "fromDate", required = false)
                                          @DateTimeFormat(pattern = DATE_FORMAT) @Valid Date fromDate,
                                          @RequestParam(name = "toDate", required = false)
                                          @DateTimeFormat(pattern = DATE_FORMAT) @Valid Date toDate,
                                          @RequestParam(name = "fromAmount", required = false)
                                          @NumberFormat(pattern = NUMBER_FORMAT) @Valid BigDecimal fromAmount,
                                          @RequestParam(name = "toAmount", required = false)
                                          @NumberFormat(pattern = NUMBER_FORMAT) @Valid BigDecimal toAmount) {

        // Validate User Role
        requestSpecification.validateUserRole(request);

        // Request Param Validation
        FilterType filterType = requestSpecification.validateRequestParams(fromDate, toDate, fromAmount, toAmount, request);

        // Construct Param Filter
        ParameterFilter param = ParameterFilter.builder()
                .fromDate(fromDate).toDate(toDate).fromAmount(fromAmount).toAmount(toAmount).build();

        return statementService.getStatement(accountId, param, filterType);
    }
}
