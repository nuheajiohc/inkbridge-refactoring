package com.nhnacademy.inkbridge.backend.exception;

/**
 * class: NotFoundException.
 *
 * @author minm063
 * @version 2024/02/15
 */
public class AlreadyExistException extends RuntimeException {

    public AlreadyExistException(String message) {
        super(message);
    }
}
