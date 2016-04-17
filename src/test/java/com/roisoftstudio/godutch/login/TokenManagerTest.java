package com.roisoftstudio.godutch.login;

import com.roisoftstudio.godutch.login.model.Account;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TokenManagerTest {


    private TokenManager tokenManager;

    @Before
    public void setUp() throws Exception {
        tokenManager = new TokenManager();
    }

    @Test
    public void whenEncrypting_shouldReturnEncryptedEmail() throws Exception {
        String token = tokenManager.createToken(new Account("email", "password"));

        assertThat(tokenManager.decodeToken(token),is("email"));
    }

    @Test
    public void createToken2Times_shouldReturnSameToken() throws Exception {
        String token = tokenManager.createToken(new Account("email", "password"));
        String token2 = tokenManager.createToken(new Account("email", "password"));

        assertThat(token, is(token2));
    }
}