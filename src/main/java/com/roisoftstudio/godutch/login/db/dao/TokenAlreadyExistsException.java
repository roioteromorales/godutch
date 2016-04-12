package com.roisoftstudio.godutch.login.db.dao;

public class TokenAlreadyExistsException extends Exception {
    public TokenAlreadyExistsException(String message) {
        super(message);
    }
}
