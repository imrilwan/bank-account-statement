package com.classican.bankaccountstatement.controller.logging;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Controller Login
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ControllerLogging {
    @AliasFor("entityType")
    String value() default "";

    @AliasFor("value")
    String entityType() default "";

    boolean skipRequestData() default false;

    boolean skipResponseData() default false;
}
