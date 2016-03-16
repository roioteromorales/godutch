package com.roisoftstudio.godutch.integration.login.paths;

import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;

public class SignPathTest extends JerseyTest {

    @Override
    protected AppDescriptor configure() {
        return new WebAppDescriptor.Builder().build();
    }

    //todo fix tests with good ip and parameters
//    @Test
//    public void userCanSignUp() throws JSONException, URISyntaxException {
//        WebResource webResource = client().resource("http://192.168.99.100:18080/");
//        JSONObject json = webResource.path("godutch-0.1/sign/up").put(JSONObject.class);
//        assertThat(json.get("id"), is("12"));
//        assertThat(json.get("firstName"), is("Tim"));
//        assertThat(json.get("lastName"), is("Tester"));
//        assertThat(json.get("birthday"), is("1970-01-16T07:56:49.871+01:00"));
//    }
//
//    @Test(expected = UniformInterfaceException.class)
//    public void testUserNotFound() {
//        WebResource webResource = client().resource("http://localhost:8080/");
//        JSONObject json = webResource.path("/rest-test-tutorial/user/id/666").get(JSONObject.class);
//    }
}