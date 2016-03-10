package com.roisoftstudio.godutch.login.paths;

import com.google.inject.Inject;
import com.roisoftstudio.godutch.login.services.SignService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;


@Path("/sign")
public class SignPath {

    @Inject
    SignService signService;

    @GET
    @Path("/up")
    public Response signUp() {
        return Response.ok("Sign Up: ").build();
    }

    @GET
    @Path("/in")
    public Response signIn() {
        boolean signedIn = signService.isSignedIn("");
        return Response.ok("Sign In: " + signedIn).build();
    }

    @GET
    @Path("/out")
    public Response signOut() {
        return Response.ok("Sign Out").build();
    }
}
