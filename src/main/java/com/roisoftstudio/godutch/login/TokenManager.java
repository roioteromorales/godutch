package com.roisoftstudio.godutch.login;

import com.roisoftstudio.godutch.login.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;

public class TokenManager {

    private Key key = MacProvider.generateKey();

    public String createToken(User user) {
        return Jwts.builder().setSubject(user.getEmail()).signWith(SignatureAlgorithm.HS512, key).compact();
    }

    public String decodeToken(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
    }
}
