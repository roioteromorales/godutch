package com.roisoftstudio.godutch.integration.login.paths;

import com.github.kevinsawicki.http.HttpRequest;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.roisoftstudio.godutch.config.ConfigurationConstants.CONTAINER_URL;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.OK;
import static org.assertj.core.api.Assertions.assertThat;

public class SignPathTest {

    public static final String PATH = "sign/";

    @Test
    public void getHelpIsWorking() throws Exception {
        String response = HttpRequest.get(CONTAINER_URL + PATH + "help").body();
        assertThat(response).isEqualTo("This is working");
    }

    @Test
    public void signUpWithWrongFormData_shouldFail() throws Exception {
        Map<String, String> formParameters = new HashMap<>();

        HttpRequest request = HttpRequest.put(CONTAINER_URL + PATH + "up")
                .contentType("application/x-www-form-urlencoded")
                .form(formParameters);

        assertThat(request.code()).isEqualTo(BAD_REQUEST.getStatusCode());
    }

    @Test
    public void signUp_shouldCreateAnUser() throws Exception {
        Map<String, String> formParameters = new HashMap<>();
        formParameters.put("email", "email@mail.com");
        formParameters.put("password", "pass");

        HttpRequest request = HttpRequest.put(CONTAINER_URL + PATH + "up").form(formParameters);

        assertThat(request.code()).isEqualTo(OK.getStatusCode());
    }
}