package com.roisoftstudio.godutch.integration.login.paths;

import com.github.kevinsawicki.http.HttpRequest;
import org.junit.Test;

import static com.roisoftstudio.godutch.config.ConfigurationConstants.CONTAINER_URL;
import static org.assertj.core.api.Assertions.assertThat;

public class SignPathTest {


    @Test
    public void simpleIntegrationTest() throws Exception {
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("@@           INTEGRATION TEST IS RUNNING          @@");
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("URL: " + CONTAINER_URL);

        String response = HttpRequest.get(CONTAINER_URL + "sign/help").body();
        assertThat(response).isEqualTo("This is working");
    }
}