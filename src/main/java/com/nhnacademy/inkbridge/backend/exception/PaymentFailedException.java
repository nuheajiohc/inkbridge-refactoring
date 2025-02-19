package com.nhnacademy.inkbridge.backend.exception;

/**
 * class: PaymentFailedException.
 *
 * @author jangjaehun
 * @version 2024/03/23
 */
public class PaymentFailedException extends RuntimeException {

    public PaymentFailedException(String message) {
        super(message);
    }
}
