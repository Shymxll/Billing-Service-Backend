package com.service.Billing.security.exception;

/**
 * @author
 */

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
