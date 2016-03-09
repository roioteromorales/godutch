package com.roisoftstudio.godutch.login.services;

import com.roisoftstudio.godutch.login.db.dao.InMemoryUserDao;
import com.roisoftstudio.godutch.login.exceptions.UserAlreadyExistsException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SignServiceTest {

    private SignService signService;

    @Before
    public void setUp() throws Exception {
        signService = new SignService(new InMemoryUserDao());
    }

    @Test
    public void signUp_shouldCreateNewUser() throws Exception {
        String token = signService.signUp("user", "email");
        assertThat(signService.isSignedIn(token), is(true));
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void signUp_shouldShouldFailIfUserExists() throws Exception {
        signService.signUp("user", "email");
        signService.signUp("user", "email");
    }
}