package com.roisoftstudio.godutch.login.paths;

import com.google.inject.Inject;
import com.roisoftstudio.godutch.authentication.Secured;
import com.roisoftstudio.godutch.login.model.Credentials;
import com.roisoftstudio.godutch.login.services.InvalidCredentialsException;
import com.roisoftstudio.godutch.login.services.SignService;
import com.roisoftstudio.godutch.login.services.SignServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.*;


@Path("/sign")
public class SignPath {
    private final Logger logger = LoggerFactory.getLogger(SignPath.class);

    @Inject
    private SignService signService;

    @GET
    @Secured
    @Path("/protected")
    public Response getProtected() {
        return Response.ok("This is working").build();
    }


    @PUT
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    @Path("/up")
    public Response signUp(@NotNull Credentials credentials) {
        checkNotNull(credentials.getEmail(), "email"); // try to make work @NotNull annotation on param
        checkNotNull(credentials.getPassword(), "password");
        try {
            signService.signUp(credentials.getEmail(), credentials.getPassword());
            return Response.ok("User created successfully.").status(CREATED).build();
        } catch (SignServiceException e) {
            logger.error("An error occurred while signing up. ", e);
            return Response.ok("User Already exists.").status(CONFLICT).build();
        }
    }

    @POST
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    @Path("/in")
    public Response signIn(Credentials credentials) {
        checkNotNull(credentials.getEmail(), "email"); // try to make work @NotNull annotation on param
        checkNotNull(credentials.getPassword(), "password");
        try {
            String token = signService.signIn(credentials.getEmail(), credentials.getPassword());
            return Response.ok(token).status(OK).build();
        } catch (SignServiceException e) {
            String msg = "An error occurred while signing in. ";
            logger.error(msg, e);
            return Response.ok(msg + e.getMessage()).status(INTERNAL_SERVER_ERROR).build();
        } catch (InvalidCredentialsException e) {
            logger.error("Error Unauthorized user: " + credentials.getEmail());
            return Response.ok("Unauthorized User. ").status(UNAUTHORIZED).build();
        }
    }

    @POST
    @Path("/out")
    @Secured
    public Response signOut(@Context HttpHeaders headers) {
        String token = headers.getHeaderString(AUTHORIZATION);
        try {
            if (signService.signOut(token)) {
                return Response.ok("Successfully logged out.").status(OK).build();
            } else {
                return Response.ok("User cannot log out. Is not signed in.").status(UNAUTHORIZED).build();
            }
        } catch (SignServiceException e) {
            logger.error("An error occurred while signing out. ", e);
            return Response.status(INTERNAL_SERVER_ERROR).build();
        }
    }


    private void checkNotNull(String parameter, String parameterName) {
        if (parameter == null || parameter.equals("")) {
            throw new BadRequestException("Required parameter was null: " + parameterName);
        }
    }
}
