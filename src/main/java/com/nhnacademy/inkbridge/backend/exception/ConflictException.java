package com.nhnacademy.inkbridge.backend.exception;

/**
 *  class: ConflictException.
 *
 *  @author minm063
 *  @version 2024/03/22
 */
public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}
