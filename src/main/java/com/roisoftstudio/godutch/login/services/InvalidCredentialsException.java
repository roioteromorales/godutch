package com.roisoftstudio.godutch.login.services;

public class InvalidCredentialsException extends Exception {
    public InvalidCredentialsException(String msg) {
        super(msg);
    }
}
