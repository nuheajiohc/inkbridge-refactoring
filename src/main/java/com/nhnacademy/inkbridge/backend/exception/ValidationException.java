package com.nhnacademy.inkbridge.backend.exception;

/**
 * class: ValidationException.
 *
 * @author minm063
 * @version 2024/02/15
 */
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}
