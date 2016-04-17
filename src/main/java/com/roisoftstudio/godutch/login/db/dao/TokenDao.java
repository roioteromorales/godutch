package com.roisoftstudio.godutch.login.db.dao;

public interface TokenDao {
    void addToken(String email, String token) throws TokenAlreadyExistsException;

    void removeTokenFor(String token);

    boolean hasToken(String token);
}
