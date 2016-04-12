package com.roisoftstudio.godutch.login.db.dao;

public interface TokenDao {
    void addToken(String token);
    void removeToken(String token);
    boolean hasToken(String token);
}
