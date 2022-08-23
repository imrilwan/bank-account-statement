package com.classican.bankaccountstatement.controller;

import com.classican.bankaccountstatement.model.dto.request.ParameterFilter;
import com.classican.bankaccountstatement.model.dto.response.AccountStatement;
import com.classican.bankaccountstatement.model.dto.response.StatementResponse;
import com.classican.bankaccountstatement.model.enums.FilterType;
import com.classican.bankaccountstatement.service.StatementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Statement Test Controller
 */
@ContextConfiguration(classes = {StatementController.class})
@ExtendWith(SpringExtension.class)
class StatementControllerTest {

    private static final String PATH = "/api/v1/statement";
    private static final long ACCOUNT_ID = 1L;
    private static final String ACCOUNT_NUMBER = "001846681047";

    @Autowired
    private StatementController statementController;

    @MockBean
    private StatementService statementService;

    @MockBean
    private RequestSpecification requestSpecification;

    @MockBean
    private HttpServletRequest request;

    @Test
    public void testGetStatements() throws Exception {
        Mockito.doNothing().when(requestSpecification).validateUserRole(any());
        when(requestSpecification.validateRequestParams(any(), any(), any(), any(), any())).thenReturn(FilterType.DEFAULT);

        StatementResponse statementResponse = StatementResponse.builder()
                .id(1L)
                .date(new Date())
                .amount(new BigDecimal(702))
                .build();

        AccountStatement accountStatement = AccountStatement.builder()
                .accountId(ACCOUNT_ID)
                .accountNumber(ACCOUNT_NUMBER)
                .accountType(ACCOUNT_NUMBER)
                .statements(Arrays.asList(statementResponse))
                .build();

        when(statementService.getStatement(anyLong(), any(ParameterFilter.class), any(FilterType.class)))
                .thenReturn(accountStatement);

        MockMvcBuilders.standaloneSetup(statementController)
                .build()
                .perform(MockMvcRequestBuilders.get(PATH+"/"+ACCOUNT_ID)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.accountId").value(accountStatement.getAccountId()));
    }
}
