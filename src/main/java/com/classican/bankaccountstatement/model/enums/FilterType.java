package com.classican.bankaccountstatement.model.enums;

import lombok.AllArgsConstructor;

/**
 * Search Filter Enum
 */
@AllArgsConstructor
public enum FilterType {
    DEFAULT, DATE_FILTER, AMOUNT_FILTER, DATE_AND_AMOUNT_FILTER;
}
