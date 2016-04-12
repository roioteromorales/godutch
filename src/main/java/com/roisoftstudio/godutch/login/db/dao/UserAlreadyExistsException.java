package com.roisoftstudio.godutch.login.db.dao;

import com.roisoftstudio.godutch.login.services.SignServiceException;

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
