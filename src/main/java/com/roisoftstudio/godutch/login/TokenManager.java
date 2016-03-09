package com.roisoftstudio.godutch.login;

import com.roisoftstudio.godutch.login.model.User;

//// TODO: 09-Mar-16 create a token storage or something think about it
public class TokenManager {
    public String createToken(User user) {
        return user.getEmail() + "%" + user.getPassword();
    }

    public User decodeToken(String token) {
        String[] split = token.split("%");
        return new User(split[0], split[1]);
    }
}
