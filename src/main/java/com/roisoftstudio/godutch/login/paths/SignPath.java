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
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.*;


@Path("/sign")
public class SignPath {
    private final Logger logger = LoggerFactory.getLogger(SignPath.class);

    @Inject
    private SignService signService;

    @GET
    @Path("/help")
    public Response getHelp() {
        return Response.ok("This is working").build();
    }

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
    public Response signUp(Credentials credentials) {
        checkNotNull(credentials.getEmail(), "email"); // try to make work @NotNull annotation on param
        checkNotNull(credentials.getPassword(), "password");
        try {
            signService.signUp(credentials.getEmail(), credentials.getPassword());
            return Response.status(CREATED).build();
        } catch (SignServiceException e) {
            logger.error("An error occurred while signing up. ", e);
            return Response.status(CONFLICT).build();
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
            logger.error("An error occurred while signing in. ", e);
            return Response.status(INTERNAL_SERVER_ERROR).build();
        } catch (InvalidCredentialsException e) {
            logger.error("Error Unauthorized user: " + credentials.getEmail());
            return Response.status(UNAUTHORIZED).build();
        }
    }

    @POST
    @Path("/out")
    @Secured//TODO try to use context authorization header instead of parameter
    public Response signOut(@NotNull @FormParam("token") String token) {
        checkNotNull(token, "token"); // try to make work @NotNull annotation on param
        try {
            if (signService.signOut(token)) {
                return Response.status(OK).build();
            } else {
                return Response.status(UNAUTHORIZED).build();
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
