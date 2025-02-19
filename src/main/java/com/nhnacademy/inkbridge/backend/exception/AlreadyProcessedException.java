package com.nhnacademy.inkbridge.backend.exception;

/**
 * class: AlreadyProcessedException.
 *
 * @author jangjaehun
 * @version 2024/03/17
 */
public class AlreadyProcessedException extends RuntimeException {

    public AlreadyProcessedException(String message) {
        super(message);
    }
}
