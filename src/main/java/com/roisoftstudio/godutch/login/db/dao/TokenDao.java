package com.roisoftstudio.godutch.login.db.dao;

public interface TokenDao {
    void addToken(String token);
    boolean hasToken(String token);
}
