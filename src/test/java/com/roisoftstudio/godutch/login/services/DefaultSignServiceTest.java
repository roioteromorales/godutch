package com.roisoftstudio.godutch.login.services;

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
    UserDao userDao;

//    Injector injector = Guice.createInjector(new LoginModule());
//    UserDao userDao = injector.getInstance(UserDao.class);
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        signService = new DefaultSignService(userDao);
    }

    @Test
    public void signUp_shouldCreateNewUser() throws Exception {
        when(userDao.contains(any(User.class))).thenReturn(true);

        String token = signService.signUp("user", "email");

        assertThat(signService.isSignedIn(token), is(true));
    }

    @Test(expected = SignServiceException.class)
    public void signUp_shouldShouldFailIfUserExists() throws Exception {
        doNothing().doThrow(new UserAlreadyExistsException("Error ")).when(userDao).addUser(any(User.class));

        signService.signUp("user", "email");
        signService.signUp("user", "email");
    }
}