package com.classican.bankaccountstatement.controller;

import com.classican.bankaccountstatement.exception.BadRequestException;
import com.classican.bankaccountstatement.exception.UnauthorizedAccessException;
import com.classican.bankaccountstatement.model.enums.FilterType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Component
public class RequestSpecification {

    public FilterType validateRequestParams(Date fromDate, Date toDate, BigDecimal fromAmount, BigDecimal toAmount, HttpServletRequest request) {
        FilterType filterType = FilterType.DEFAULT;
        if (!request.getParameterMap().isEmpty()) {
            filterType = validateDateRange(fromDate, toDate, filterType);
            filterType = validateAmountRange(fromAmount, toAmount, filterType);
        }
        return filterType;
    }

    public FilterType validateAmountRange(BigDecimal fromAmount, BigDecimal toAmount, FilterType filterType) {
        if (fromAmount != null && toAmount != null) {
            filterType = FilterType.DATE_FILTER.equals(filterType) ? FilterType.DATE_AND_AMOUNT_FILTER :
                    FilterType.AMOUNT_FILTER;
        } else if ((fromAmount == null && toAmount != null) || (fromAmount != null)) {
            throw new BadRequestException("Invalid request param amount range selected");
        }
        return filterType;
    }

    public FilterType validateDateRange(Date fromDate, Date toDate, FilterType filterType) {
        if (fromDate != null && toDate != null) {
            if (fromDate.compareTo(toDate) > 0) {
                throw new BadRequestException("fromDate must be an earlier date");
            } else {
                filterType = FilterType.DATE_FILTER;
            }
        } else if ((fromDate == null && toDate != null) || (fromDate != null)) {
            throw new BadRequestException("Invalid request param date range selected");
        }
        return filterType;
    }

    public void validateUserRole(HttpServletRequest request) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication auth = securityContext.getAuthentication();
        List<GrantedAuthority> authorities = new ArrayList<>(auth.getAuthorities());

        if (authorities.stream().anyMatch(i -> i.getAuthority().contains("ROLE_USER")) && !request.getParameterMap().isEmpty()) {
            throw new UnauthorizedAccessException("Unauthorized Access");
        }
    }
}
