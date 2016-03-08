package com.roisoftstudio.godutch.login.paths;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/sign")
public class SignPath {

    @PUT
    @Path("/up")
    public Response signUp() {
        return Response.ok("Sign Up").build();
    }
    @PUT
    @Path("/in")
    public Response signIn() {
        return Response.ok("Sign In").build();
    }

    @PUT
    @Path("/out")
    public Response signOut() {
        return Response.ok("Sign Out").build();
    }
}
