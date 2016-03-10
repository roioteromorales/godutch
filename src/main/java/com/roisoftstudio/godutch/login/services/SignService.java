package com.roisoftstudio.godutch.login.services;

import com.roisoftstudio.godutch.login.exceptions.UserAlreadyExistsException;

public interface SignService {

    String signUp(String email, String password) throws UserAlreadyExistsException;

    boolean isSignedIn(String token);
}
