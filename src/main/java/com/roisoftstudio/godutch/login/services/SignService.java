package com.roisoftstudio.godutch.login.services;

import com.roisoftstudio.godutch.login.exceptions.SignServiceException;

public interface SignService {

    String signUp(String email, String password) throws SignServiceException;
    boolean signIn(String email, String password) throws SignServiceException;
    boolean isSignedIn(String token);
}
