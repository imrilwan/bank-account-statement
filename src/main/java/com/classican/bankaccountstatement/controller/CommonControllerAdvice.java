package com.classican.bankaccountstatement.controller;

import com.classican.bankaccountstatement.exception.BadRequestException;
import com.classican.bankaccountstatement.exception.ResourceNotFoundException;
import com.classican.bankaccountstatement.exception.UnauthorizedAccessException;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.rmi.server.ExportException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handler for exceptions induced by Controller
 */
@ControllerAdvice(basePackages = {"com.classican.bankaccountstatement"})
public class CommonControllerAdvice extends ResponseEntityExceptionHandler {

    private static final String COMMA_AND_SPACE = ", ";

    @ExceptionHandler({BadRequestException.class, IllegalArgumentException.class})
    @ResponseBody
    public ResponseEntity<Object> onBadRequestException(Exception e) {
        return error(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<Object> onException(Exception e) {
        return new ResponseEntity<>(getErrorFor500Exception(e), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public ResponseEntity<Object> onResourceNotFoundException(ResourceNotFoundException e) {
        return error(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UnauthorizedAccessException.class, IOException.class, ExportException.class,
            InsufficientAuthenticationException.class})
    @ResponseBody
    public ResponseEntity<Object> onUnauthorizedAccessException(Exception e) {
        return error(e, HttpStatus.UNAUTHORIZED);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        return error(e, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        Error error = Error.builder()
                .code(HttpStatus.BAD_REQUEST.toString())
                .message(assembleValidationErrorReport(e))
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    private String assembleValidationErrorReport(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();
        return fieldErrors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(COMMA_AND_SPACE));
    }

    private Error getErrorFor500Exception(Exception e) {
        return Error.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .message(e.getMessage() == null ? e.getClass().getName() : e.getMessage())
                .build();
    }

    private ResponseEntity<Object> error(Exception e, HttpStatus httpStatus) {
        Error error = Error.builder()
                .code(httpStatus.name())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(error, httpStatus);
    }

    @Builder
    @Data
    private static class Error {

        @NotNull
        private String code;

        @NotNull
        private String message;

        private String fields;
    }
}
