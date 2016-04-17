package com.roisoftstudio.godutch.login.db.dao;

import com.roisoftstudio.godutch.login.services.SignServiceException;

public class AccountAlreadyExistsException extends SignServiceException {
    public AccountAlreadyExistsException() {
        super();

    }
    public AccountAlreadyExistsException(String message) {
        super(message);
    }
    public AccountAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
