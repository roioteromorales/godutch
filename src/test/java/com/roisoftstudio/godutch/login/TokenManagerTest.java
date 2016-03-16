package com.roisoftstudio.godutch.login;

import com.roisoftstudio.godutch.login.model.User;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TokenManagerTest {


    @Test
    public void whenEncrypting_shouldReturnEncryptedEmail() throws Exception {
        TokenManager tokenManager = new TokenManager();
        String token = tokenManager.createToken(new User("email", "password"));

        assertThat(tokenManager.decodeToken(token),is("email"));
    }
}