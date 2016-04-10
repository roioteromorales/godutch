package com.roisoftstudio.godutch.login.services;

import com.roisoftstudio.godutch.login.TokenManager;
import com.roisoftstudio.godutch.login.db.dao.TokenDao;
import com.roisoftstudio.godutch.login.db.dao.UserDao;
import com.roisoftstudio.godutch.login.exceptions.SignServiceException;
import com.roisoftstudio.godutch.login.exceptions.UserAlreadyExistsException;
import com.roisoftstudio.godutch.login.model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class DefaultSignServiceTest {

    private SignService signService;

    @Mock
    private UserDao userDao;

    @Mock
    private TokenManager tokenManager;

    @Mock
    private TokenDao tokenDao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        signService = new DefaultSignService(userDao, tokenManager, tokenDao);
    }

    @Test
    public void signUp_shouldCreateNewUser() throws Exception {
        String user = "user";
        String email = "email";

        when(userDao.contains(any(User.class))).thenReturn(true);
        when(tokenManager.createToken(any(User.class))).thenReturn(user + email);
        String token = signService.signUp(user, email);

        when(tokenDao.hasToken(token)).thenReturn(true);

        assertThat(signService.isSignedIn(token), is(true));
    }

    @Test(expected = SignServiceException.class)
    public void signUp_shouldShouldFailIfUserExists() throws Exception {
        doNothing().doThrow(new UserAlreadyExistsException("Error ")).when(userDao).addUser(any(User.class));

        signService.signUp("user", "email");
        signService.signUp("user", "email");
    }
}