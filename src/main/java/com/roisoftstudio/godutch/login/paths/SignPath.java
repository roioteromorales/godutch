package com.roisoftstudio.godutch.login.paths;

import com.google.inject.Inject;
import com.roisoftstudio.godutch.responses.ResponseError;
import com.roisoftstudio.godutch.responses.ResponseSucceed;
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
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
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
    @Path("/up")
    public Response signUp(@NotNull Credentials credentials) {
        checkNotNull(credentials.getEmail(), "email"); // try to make work @NotNull annotation on param
        checkNotNull(credentials.getPassword(), "password");
        try {
            signService.signUp(credentials.getEmail(), credentials.getPassword());
          /*
            Now returns this {status: CREATED, message:"Account created..."}
            But i think its better return only HTTP code if its OK
            And if an error has occurred, return HTTP + message (with ResponseError)
          */
            return Response
                    .ok(new ResponseSucceed("Account created successfully.", CREATED))
                    .status(CREATED)
                    .build();
        } catch (final SignServiceException e) {
            logger.error("An error occurred while signing up. ", e);
            return Response.status(CONFLICT).entity(new ResponseError(e.getMessage(), CONFLICT)).build();
        }
    }

    @POST
    @Path("/in")
    public Response signIn(Credentials credentials) {
        checkNotNull(credentials.getEmail(), "email"); // try to make work @NotNull annotation on param
        checkNotNull(credentials.getPassword(), "password");
        try {
            String token = signService.signIn(credentials.getEmail(), credentials.getPassword());
            return Response.ok(token).build();
        } catch (final SignServiceException e) {
            String msg = "An error occurred while signing in. ";
            logger.error(msg, e);
            return Response
                    .status(INTERNAL_SERVER_ERROR)
                    .entity(new ResponseError(e.getMessage()))
                    .build();
        } catch (final InvalidCredentialsException e) {
            logger.error("Error Unauthorized account: " + credentials.getEmail());
            return Response.ok(new ResponseError(e.getMessage(), UNAUTHORIZED)).status(UNAUTHORIZED).build();
        }
    }

    @POST
    @Path("/out")
    @Secured
    public Response signOut(@Context HttpHeaders headers) {
        String token = headers.getHeaderString(AUTHORIZATION);
        try {
            if (signService.signOut(token)) {
                return Response.ok(new ResponseSucceed("Successfully logged out.")).build();
            } else {
                return Response
                        .status(UNAUTHORIZED)
                        .entity(new ResponseError("Account cannot log out. Is not signed in.", UNAUTHORIZED))
                        .build();
            }
        } catch (final SignServiceException e) {
            logger.error("An error occurred while signing out. ", e);
            return Response
                    .status(INTERNAL_SERVER_ERROR)
                    .entity(new ResponseError(e.getMessage()))
                    .build();
        }
    }


    private void checkNotNull(String parameter, String parameterName) {
        if (parameter == null || parameter.equals("")) {
            throw new BadRequestException("Required parameter was null: " + parameterName);
        }
    }
}
