package com.roisoftstudio.godutch.login.services;

public interface SignService {

    void signUp(String email, String password) throws SignServiceException;
    String signIn(String email, String password) throws SignServiceException, InvalidCredentialsException;
    boolean signOut(String token) throws SignServiceException;
    boolean isSignedIn(String token);
}
