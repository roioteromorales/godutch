package com.roisoftstudio.godutch.login.services;

import com.roisoftstudio.godutch.login.TokenManager;
import com.roisoftstudio.godutch.login.db.dao.TokenDao;
import com.roisoftstudio.godutch.login.db.dao.AccountDao;
import com.roisoftstudio.godutch.login.db.dao.AccountAlreadyExistsException;
import com.roisoftstudio.godutch.login.model.Account;
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
    private AccountDao accountDao;

    @Mock
    private TokenManager tokenManager;

    @Mock
    private TokenDao tokenDao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        signService = new DefaultSignService(accountDao, tokenManager, tokenDao);
    }

    @Test
    public void signUp_shouldCreateAccount() throws Exception {
        signService.signUp(SAMPLE_EMAIL, SAMPLE_PASSWORD);
        verify(accountDao, atLeastOnce()).addAccount(new Account(SAMPLE_EMAIL, SAMPLE_PASSWORD));
    }

    @Test
    public void signUpTwice_shouldThrowSignServiceExceptionIfAccountExists() throws Exception {
        AccountAlreadyExistsException alreadyExistsException = new AccountAlreadyExistsException("Error ");
        doNothing().doThrow(alreadyExistsException).when(accountDao).addAccount(any(Account.class));

        signService.signUp(SAMPLE_EMAIL, SAMPLE_PASSWORD);

        assertThatExceptionOfType(SignServiceException.class)
                .isThrownBy(() -> signService.signUp(SAMPLE_EMAIL, SAMPLE_PASSWORD))
                .withMessageContaining("An error occurred")
                .withCause(alreadyExistsException);
    }

    @Test
    public void signInWithNotExistingAccount_shouldThrowInvalidCredentialsException() throws Exception {
        assertThatExceptionOfType(InvalidCredentialsException.class)
                .isThrownBy(() -> signService.signIn(SAMPLE_EMAIL, SAMPLE_PASSWORD))
                .withMessageContaining("Invalid Credentials");
    }

    @Test
    public void signInWithExistingAccount_shouldReturnAValidToken() throws Exception {
        Account account = new Account(SAMPLE_EMAIL, SAMPLE_PASSWORD);
        when(accountDao.contains(account)).thenReturn(true);
        when(tokenManager.createToken(account)).thenReturn("tokenValue");

        assertThat(signService.signIn(SAMPLE_EMAIL, SAMPLE_PASSWORD)).isEqualTo("tokenValue");
    }

    @Test
    public void signOutWithNotSignedAccountToken_shouldReturnFalse() throws Exception {
        assertThat(signService.signOut("Token")).isFalse();
    }

    @Test
    public void signOutWithSignedInAccountToken_shouldReturnTrue() throws Exception {
        when(tokenDao.hasToken("tokenValue")).thenReturn(true);

        assertThat(signService.signOut("tokenValue")).isTrue();
    }
}