package com.classican.bankaccountstatement.utils;

import lombok.SneakyThrows;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

/**
 * Statement Util
 */
public final class StatementUtils {

    private static final String[] DATE_FIELD_FORMAT = new String[] {"dd.MM.yyyy"};

    @SneakyThrows
    public static Date convertToDate(String dateInString) {
        return DateUtils.parseDate(dateInString, DATE_FIELD_FORMAT);
    }

    private StatementUtils() {

    }
}
