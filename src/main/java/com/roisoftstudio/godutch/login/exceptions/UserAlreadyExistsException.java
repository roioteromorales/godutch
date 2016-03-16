package com.roisoftstudio.godutch.login.exceptions;

public class UserAlreadyExistsException extends SignServiceException {
    public UserAlreadyExistsException() {
        super();

    }
    public UserAlreadyExistsException(String message) {
        super(message);
    }
    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
