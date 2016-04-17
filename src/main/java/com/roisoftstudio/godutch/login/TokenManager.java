package com.roisoftstudio.godutch.login;

import com.google.inject.Singleton;
import com.roisoftstudio.godutch.login.model.Account;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;

@Singleton
public class TokenManager {

    private Key key = MacProvider.generateKey();

    public String createToken(Account account) {
        return Jwts.builder().setSubject(account.getEmail()).signWith(SignatureAlgorithm.HS512, key).compact();
    }

    public String decodeToken(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
    }
}
