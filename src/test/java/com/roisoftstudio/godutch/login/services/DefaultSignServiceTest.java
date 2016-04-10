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
        String email = "email";
        String password = "password";

        when(userDao.contains(any(User.class))).thenReturn(true);
        when(tokenManager.createToken(any(User.class))).thenReturn(email + password);
        String token = signService.signUp(email, password);

        when(tokenDao.hasToken(token)).thenReturn(true);

        assertThat(signService.isSignedIn(token), is(true));
    }

    @Test(expected = SignServiceException.class)
    public void signUpTwice_shouldShouldFailIfUserExists() throws Exception {
        doNothing().doThrow(new UserAlreadyExistsException("Error ")).when(userDao).addUser(any(User.class));

        signService.signUp("email", "password");
        signService.signUp("email", "password");
    }

    @Test
    public void signInWithNotExistingUser_shouldReturnFalse() throws Exception {
        assertThat(signService.signIn("email", "password"), is(false));
    }

    @Test
    public void signInWithExistingUser_shouldReturnTrue() throws Exception {
        User user = new User("email", "password");
        when(tokenManager.createToken(user)).thenReturn("tokenValue");
        when(tokenDao.hasToken("tokenValue")).thenReturn(true);

        assertThat(signService.signIn("email", "password"), is(true));
    }
}