package com.roisoftstudio.godutch.login.paths;

import com.google.inject.Inject;
import com.roisoftstudio.godutch.json.JsonSerializer;
import com.roisoftstudio.godutch.login.exceptions.SignServiceException;
import com.roisoftstudio.godutch.login.services.SignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.CONFLICT;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;


@Path("/sign")
public class SignPath {
    private final Logger logger = LoggerFactory.getLogger(SignPath.class);

    @Inject
    private SignService signService;

    @Inject
    private JsonSerializer jsonSerializer;

    @GET
    @Path("/help")
    public Response getHelp() {
        return Response.ok("This is working").build();
    }

    @PUT
    @Consumes("application/x-www-form-urlencoded")
    @Path("/up")
    public Response signUp(@NotNull @FormParam("email") String email, @NotNull @FormParam("password") String password) {
        checkNotNull(email, "email"); // try to make work @NotNull annotation on param
        checkNotNull(password, "password");
        String token;
        try {
            token = signService.signUp(email, password);
        } catch (SignServiceException e) {
            logger.error("An error occurred while signing in. ", e);
            return Response.status(CONFLICT).build();
        }
        return Response.ok(token).build();

    }

    @POST
    @Path("/in")
    public Response signIn(@FormParam("token") String token) {
        if (signService.isSignedIn(token)) {
            return Response.ok("You are Signed in.").build();
        } else {
            return Response.ok("Your token is invalid: " + token).status(UNAUTHORIZED).build();
        }

    }

    @GET
    @Path("/out")
    public Response signOut() {
        return Response.ok("Sign Out").build();
    }


    private void checkNotNull(String parameter, String parameterName) {
        if (parameter == null) {
            throw new BadRequestException("Required parameter was null: " + parameterName);
        }
    }
}
