package com.roisoftstudio.godutch.integration.login.paths;

import com.github.kevinsawicki.http.HttpRequest;
import com.roisoftstudio.godutch.integration.login.LoginHelpers;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Random;

import static com.roisoftstudio.godutch.config.ConfigurationConstants.CONTAINER_URL;
import static com.roisoftstudio.godutch.integration.login.LoginHelpers.*;
import static javax.ws.rs.core.Response.Status.*;
import static org.assertj.core.api.Assertions.assertThat;

public class SignPathTest {

    @Ignore
    @Test
    public void signUpWithNullBody_shouldFailWithBadRequest() throws Exception {
        HttpRequest signUpRequest = HttpRequest.put(CONTAINER_URL + PATH + "up")
                .contentType("application/json");
        assertThat(signUpRequest.code()).isEqualTo(BAD_REQUEST.getStatusCode());
    }

    @Test
    public void signUpWithNullData_shouldFail() throws Exception {
        HttpRequest signUpRequest = signUpRequest(null, null);
        assertThat(signUpRequest.code()).isEqualTo(BAD_REQUEST.getStatusCode());
    }

    @Test
    public void signUpWithEmptyData_shouldFail() throws Exception {
        HttpRequest signUpRequest = signUpRequest("", "");
        assertThat(signUpRequest.code()).isEqualTo(BAD_REQUEST.getStatusCode());
    }

    @Test
    public void signUp_shouldCreateAnAccount() throws Exception {
        HttpRequest signUpRequest = signUpRequest(aRandomEmail(), "pass");
        assertThat(signUpRequest.code()).isEqualTo(CREATED.getStatusCode());
    }

    @Test
    public void whenSignUpTwiceWithSameEmail_shouldFailOnSecondSignUp() throws Exception {
        String emailValue = aRandomEmail();
        HttpRequest signUpRequest = signUpRequest(emailValue, "pass");
        assertThat(signUpRequest.code()).isEqualTo(CREATED.getStatusCode());

        HttpRequest signUpRequest2 = signUpRequest(emailValue, "pass");
        assertThat(signUpRequest2.code()).isEqualTo(CONFLICT.getStatusCode());
        //TODO THIS FAILS WITH MONGO BECAUSE WE NEED TO CREATE THE PRIMARY KEY UNIQUE ON EMAIL.
    }

    @Test
    public void signIn_shouldReturnBadRequestStatusCode_ifNullParameters() throws Exception {
        HttpRequest signInRequest = signInRequest("", "");

        assertThat(signInRequest.code()).isEqualTo(BAD_REQUEST.getStatusCode());
    }

    @Test
    public void signIn_shouldReturnUnauthorizedStatusCode_ifNotLoggedIn() throws Exception {
        HttpRequest signInRequest = signInRequest("notLoggedInEmail", "pass");

        assertThat(signInRequest.code()).isEqualTo(UNAUTHORIZED.getStatusCode());
    }

    @Test
    public void signIn_shouldReturnTrue_ifIsSignedIn() throws Exception {
        String emailValue = aRandomEmail();
        HttpRequest signUpRequest = signUpRequest(emailValue, "pass");
        assertThat(signUpRequest.code()).isEqualTo(CREATED.getStatusCode());

        HttpRequest signInRequest = signInRequest(emailValue, "pass");
        assertThat(signInRequest.code()).isEqualTo(OK.getStatusCode());
    }

    @Test
    public void signOut_shouldReturnFalse_ifSignsOutWithoutBeingSignedIn() throws Exception {
        HttpRequest signOutRequest = signOutRequest("TOKEN");
        assertThat(signOutRequest.code()).isEqualTo(UNAUTHORIZED.getStatusCode());
    }

    @Test
    public void signOut_shouldReturnOk_ifSignsOutBeingSignedIn() throws Exception {
        String emailValue = aRandomEmail();
        assertThat(signUpRequest(emailValue, "pass").code()).isEqualTo(CREATED.getStatusCode());

        HttpRequest signInResponse = signInRequest(emailValue, "pass");
        assertThat(signInResponse.code()).isEqualTo(OK.getStatusCode());

        String token = signInResponse.body();
        HttpRequest signOutRequest = signOutRequest(token);
        assertThat(signOutRequest.code()).isEqualTo(OK.getStatusCode());
    }



}