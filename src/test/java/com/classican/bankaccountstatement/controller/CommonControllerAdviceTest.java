package com.classican.bankaccountstatement.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.classican.bankaccountstatement.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class CommonControllerAdviceTest {

    private static final String ERROR_MESSAGE = "An error occurred";

    @Test
    void testOnBadRequestException() {
        CommonControllerAdvice commonControllerAdvice = new CommonControllerAdvice();
        ResponseEntity<Object> actual = commonControllerAdvice
                .onBadRequestException(new Exception(ERROR_MESSAGE));
        assertTrue(actual.hasBody());
        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    void testOnBadRequestException2() {
        CommonControllerAdvice commonControllerAdvice = new CommonControllerAdvice();
        ResponseEntity<Object> actual = commonControllerAdvice
                .onBadRequestException(new Exception(ERROR_MESSAGE));
        assertTrue(actual.hasBody());
        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    void testOnException() {
        CommonControllerAdvice commonControllerAdvice = new CommonControllerAdvice();
        ResponseEntity<Object> actual = commonControllerAdvice
                .onException(new Exception(ERROR_MESSAGE));
        assertTrue(actual.hasBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actual.getStatusCode());
    }

    @Test
    void testOnResourceNotFoundException() {
        CommonControllerAdvice commonControllerAdvice = new CommonControllerAdvice();
        ResponseEntity<Object> actual = commonControllerAdvice
                .onResourceNotFoundException(new ResourceNotFoundException(ERROR_MESSAGE));
        assertTrue(actual.hasBody());
        assertEquals(HttpStatus.NOT_FOUND, actual.getStatusCode());
    }
}

