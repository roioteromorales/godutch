package com.roisoftstudio.godutch.integration.login.paths;

import com.github.kevinsawicki.http.HttpRequest;
import com.roisoftstudio.godutch.json.GsonSerializer;
import com.roisoftstudio.godutch.login.model.Credentials;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.roisoftstudio.godutch.config.ConfigurationConstants.CONTAINER_URL;
import static javax.ws.rs.core.Response.Status.*;
import static org.assertj.core.api.Assertions.assertThat;

public class SignPathTest {

    private static final String PATH = "sign/";

    @Test
    public void getHelpIsWorking() throws Exception {
        HttpRequest helpRequest = HttpRequest.get(CONTAINER_URL + PATH + "help");
        assertThat(helpRequest.body()).isEqualTo("This is working");
    }

    @Test
    public void signUpWithNullFormData_shouldFail() throws Exception {
        HttpRequest signUpRequest = signUpRequest(null, null);
        assertThat(signUpRequest.code()).isEqualTo(BAD_REQUEST.getStatusCode());
    }

    @Test
    public void signUpWithEmptyFormData_shouldFail() throws Exception {
        HttpRequest signUpRequest = signUpRequest("", "");
        assertThat(signUpRequest.code()).isEqualTo(BAD_REQUEST.getStatusCode());
    }

    @Test
    public void signUp_shouldCreateAnUser() throws Exception {
        HttpRequest signUpRequest = signUpRequest(aRandomEmail(), "pass");
        assertThat(signUpRequest.code()).isEqualTo(CREATED.getStatusCode());
    }

    private String aRandomEmail() {
        return new Random().nextInt() + "@mail.com";
    }

    @Test
    public void whenSignUpTwiceWithSameEmail_shouldFailOnSecondSignUp() throws Exception {
        String emailValue = aRandomEmail();
        HttpRequest signUpRequest = signUpRequest(emailValue, "pass");
        assertThat(signUpRequest.code()).isEqualTo(CREATED.getStatusCode());

        HttpRequest signUpRequest2 = signUpRequest(emailValue, "pass");
        assertThat(signUpRequest2.code()).isEqualTo(CONFLICT.getStatusCode());
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
        assertThat(signOutRequest.body()).contains("HTTP Status 401 - Unauthorized");
    }

    @Test
    public void signOut_shouldReturnTrue_ifSignsOutBeingSignedIn() throws Exception {
        String emailValue = aRandomEmail();
        assertThat(signUpRequest(emailValue, "pass").code()).isEqualTo(CREATED.getStatusCode());

        HttpRequest signInResponse = signInRequest(emailValue, "pass");
        assertThat(signInResponse.code()).isEqualTo(OK.getStatusCode());


        String token = signInResponse.body();
        HttpRequest signOutRequest = signOutRequest(token);
        assertThat(signOutRequest.code()).isEqualTo(OK.getStatusCode());
    }

    private HttpRequest signInRequest(String emailValue, String passValue) {
        return HttpRequest.post(CONTAINER_URL + PATH + "in")
                .contentType("application/x-www-form-urlencoded")
                .form(getFormParameters(emailValue, passValue));
    }

    private HttpRequest signUpRequest(String emailValue, String passValue) {
        return HttpRequest.put(CONTAINER_URL + PATH + "up")
                .contentType("application/json")
                .send(new GsonSerializer().toJson(new Credentials(emailValue, passValue)));
    }

    private HttpRequest signOutRequest(String token) {
        Map<String, String> formParameters = new HashMap<>();
        if (token != null) {
            formParameters.put("token", token);
        }
        return HttpRequest.post(CONTAINER_URL + PATH + "out")
                .contentType("application/x-www-form-urlencoded")
                .form(formParameters);
    }

    private Map<String, String> getFormParameters(String emailValue, String passValue) {
        Map<String, String> formParameters = new HashMap<>();
        if (emailValue != null) {
            formParameters.put("email", emailValue);
        }
        if (passValue != null) {
            formParameters.put("password", passValue);
        }
        return formParameters;
    }
}