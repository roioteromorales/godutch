package com.roisoftstudio.godutch.login.services;

import com.roisoftstudio.godutch.login.TokenManager;
import com.roisoftstudio.godutch.login.db.dao.TokenDao;
import com.roisoftstudio.godutch.login.db.dao.UserDao;
import com.roisoftstudio.godutch.login.db.dao.UserAlreadyExistsException;
import com.roisoftstudio.godutch.login.model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class DefaultSignServiceTest {

    private static final String SAMPLE_EMAIL = "email";
    private static final String SAMPLE_PASSWORD = "password";
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
    public void signUp_shouldCreateUser() throws Exception {
        signService.signUp(SAMPLE_EMAIL, SAMPLE_PASSWORD);
        verify(userDao, atLeastOnce()).addUser(new User(SAMPLE_EMAIL, SAMPLE_PASSWORD));
    }

    @Test
    public void signUpTwice_shouldThrowSignServiceExceptionIfUserExists() throws Exception {
        UserAlreadyExistsException alreadyExistsException = new UserAlreadyExistsException("Error ");
        doNothing().doThrow(alreadyExistsException).when(userDao).addUser(any(User.class));

        signService.signUp(SAMPLE_EMAIL, SAMPLE_PASSWORD);

        assertThatExceptionOfType(SignServiceException.class)
                .isThrownBy(() -> signService.signUp(SAMPLE_EMAIL, SAMPLE_PASSWORD))
                .withMessageContaining("An error occurred")
                .withCause(alreadyExistsException);
    }

    @Test
    public void signInWithNotExistingUser_shouldThrowInvalidCredentialsException() throws Exception {
        assertThatExceptionOfType(InvalidCredentialsException.class)
                .isThrownBy(() -> signService.signIn(SAMPLE_EMAIL, SAMPLE_PASSWORD))
                .withMessageContaining("Invalid Credentials");
    }

    @Test
    public void signInWithExistingUser_shouldReturnAValidToken() throws Exception {
        User user = new User(SAMPLE_EMAIL, SAMPLE_PASSWORD);
        when(userDao.contains(user)).thenReturn(true);
        when(tokenManager.createToken(user)).thenReturn("tokenValue");

        assertThat(signService.signIn(SAMPLE_EMAIL, SAMPLE_PASSWORD)).isEqualTo("tokenValue");
    }

    @Test
    public void signOutWithNotSignedUserToken_shouldReturnFalse() throws Exception {
        assertThat(signService.signOut("Token")).isFalse();
    }

    @Test
    public void signOutWithSignedInUserToken_shouldReturnTrue() throws Exception {
        when(tokenDao.hasToken("tokenValue")).thenReturn(true);

        assertThat(signService.signOut("tokenValue")).isTrue();
    }
}