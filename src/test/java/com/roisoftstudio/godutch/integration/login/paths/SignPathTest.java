package com.roisoftstudio.godutch.integration.login.paths;

import com.github.kevinsawicki.http.HttpRequest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SignPathTest {

    @Test
    public void simpleIntegrationTest() throws Exception {
        String response = HttpRequest.get("http://192.168.99.100:18080/godutch-0.1/sign/help").body();
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("@@           INTEGRATION TEST IS RUNNING          @@");
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        assertThat(response).isEqualTo("This is working");
    }
}