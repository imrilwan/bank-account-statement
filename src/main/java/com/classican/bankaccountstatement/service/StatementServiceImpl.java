package com.classican.bankaccountstatement.service;

import com.classican.bankaccountstatement.converter.StatementConverter;
import com.classican.bankaccountstatement.exception.ResourceNotFoundException;
import com.classican.bankaccountstatement.model.enums.FilterType;
import com.classican.bankaccountstatement.model.dto.request.ParameterFilter;
import com.classican.bankaccountstatement.model.dto.response.AccountStatement;
import com.classican.bankaccountstatement.repository.StatementDao;
import com.classican.bankaccountstatement.model.Statement;
import com.classican.bankaccountstatement.repository.AccountDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Statement Service Implementation
 */
@Slf4j
@Service
public class StatementServiceImpl implements StatementService {

    @Autowired
    private AccountDao accountDao;
    
    @Autowired
    private StatementDao statementDao;

    @Override
    public AccountStatement getStatement(long accountId, ParameterFilter parameterFilter, FilterType filterType) {

        var account = accountDao.getAccount(accountId);
        if (account == null) {
            throw new ResourceNotFoundException("Account Not Found. Account Id: "+ accountId);
        }

        var statements = statementDao.getStatement(accountId);
        Predicate<Statement> filterPredicate;

        switch(filterType) {
            case DATE_FILTER: {
                log.info("Filtering statement result with Date Range {} - {}", parameterFilter.getFromDate(),
                        parameterFilter.getToDate());
                filterPredicate = getStatementByDateRangePredicate(parameterFilter.getFromDate(), 
                        parameterFilter.getToDate());
                break;
            }
            case AMOUNT_FILTER: {
                log.info("Filtering statement result with Amount Range {} - {}", parameterFilter.getFromAmount(),
                        parameterFilter.getToAmount());
                filterPredicate = getStatementByAmountRangePredicate(parameterFilter.getFromAmount(),
                        parameterFilter.getToAmount());
                break;
            }
            case DATE_AND_AMOUNT_FILTER: {
                log.info("Filtering statement result with Date Range {} - {} and Amount Range {} - {}",
                        parameterFilter.getFromDate(),
                        parameterFilter.getToDate(), parameterFilter.getFromAmount(), parameterFilter.getToAmount());
                filterPredicate = getStatementByDateRangePredicate(parameterFilter.getFromDate(),
                        parameterFilter.getToDate()).and(getStatementByAmountRangePredicate(
                                parameterFilter.getFromAmount(),
                        parameterFilter.getToAmount()));
                break;
            }
            default:
                log.info("Filtering last 3 months statement results");
                filterPredicate = getStatementByDateRangePredicate(getThreeMonthBackDate(), new Date());
        }

        var filteredStatements = statements.stream().filter(filterPredicate).collect(Collectors.toList());
        return StatementConverter.convertToAccountStatement(account, filteredStatements);
    }

    private Date getThreeMonthBackDate() {
        LocalDate localDate = LocalDate.now().minusMonths(3);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private Predicate<Statement> getStatementByDateRangePredicate(@NonNull Date startDate, @NonNull Date endDate) {
        return i -> i.getDateField().after(startDate) && i.getDateField().before(endDate);
    }

    private Predicate<Statement> getStatementByAmountRangePredicate(@NonNull BigDecimal startAmount,
                                                                    @NonNull BigDecimal endAmount) {
        return i -> startAmount.compareTo(endAmount) > 0 ? i.getAmount().compareTo(endAmount) >= 0
                && i.getAmount().compareTo(startAmount) <= 0 : i.getAmount().compareTo(startAmount) >= 0
                && i.getAmount().compareTo(endAmount) <= 0;
    }
}
