package com.roisoftstudio.godutch.Users;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/users")
public class UsersPath {

    @GET
    public Response getAllUsers() {
        return Response.ok("Hi!").build();
    }

}
