package com.roisoftstudio.godutch.users;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/users")
public class UsersPath {

    @GET
    public Response getAllUsers() {
        return Response.ok(new UsersService().getAllUsers()).build();
    }

}
