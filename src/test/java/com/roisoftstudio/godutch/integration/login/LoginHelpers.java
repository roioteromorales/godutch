package com.roisoftstudio.godutch.integration.login;

import com.github.kevinsawicki.http.HttpRequest;
import com.roisoftstudio.godutch.json.GsonSerializer;
import com.roisoftstudio.godutch.login.model.Account;
import com.roisoftstudio.godutch.login.model.Credentials;

import java.util.Random;

import static com.roisoftstudio.godutch.config.ConfigurationConstants.CONTAINER_URL;

public class LoginHelpers {
    public static final String PATH = "sign/";

    public static HttpRequest signUpRequest(String emailValue, String passValue) {
        System.out.println(getCredentialsFrom(emailValue, passValue));
        return HttpRequest.put(CONTAINER_URL + PATH + "up")
                .contentType("application/json")
                .send(getCredentialsFrom(emailValue, passValue));
    }

    public static HttpRequest signInRequest(String emailValue, String passValue) {
        return HttpRequest.post(CONTAINER_URL + PATH + "in")
                .contentType("application/json")
                .send(getCredentialsFrom(emailValue, passValue));
    }

    public static HttpRequest signOutRequest(String token) {
        return HttpRequest.post(CONTAINER_URL + PATH + "out")
                .contentType("application/json")
                .header("Authorization", token);
    }

    public static Account aRandomAccount() {
        return new Account(aRandomEmail(), aRandomPassword());
    }

    public static String aRandomEmail() {
        return new Random().nextInt() + "@mail.com";
    }

    private static String aRandomPassword() {
        return "pass" + new Random().nextInt();
    }


    public static String getCredentialsFrom(String emailValue, String passValue) {
        return new GsonSerializer().toJson(new Credentials(emailValue, passValue));
    }

}
