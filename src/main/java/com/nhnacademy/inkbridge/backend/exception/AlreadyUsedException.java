package com.nhnacademy.inkbridge.backend.exception;

/**
 * class: UsedException.
 *
 * @author JBum
 * @version 2024/02/19
 */
public class AlreadyUsedException extends RuntimeException {

    public AlreadyUsedException(String message) {
        super(message);
    }

}
