package com.roisoftstudio.godutch.login.paths;

import com.google.inject.Inject;
import com.roisoftstudio.godutch.json.JsonSerializer;
import com.roisoftstudio.godutch.login.exceptions.SignServiceException;
import com.roisoftstudio.godutch.login.model.Credentials;
import com.roisoftstudio.godutch.login.services.SignService;
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

    @Inject
    private JsonSerializer jsonSerializer;

    @GET
    @Path("/help")
    public Response getHelp() {
        return Response.ok("This is working").build();
    }

    @PUT
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    @Path("/up")
    public Response signUp(Credentials credentials) {
        checkNotNull(credentials.getEmail(), "email"); // try to make work @NotNull annotation on param
        checkNotNull(credentials.getPassword(), "password");
        String token;
        try {
            token = signService.signUp(credentials.getEmail(), credentials.getPassword());
        } catch (SignServiceException e) {
            logger.error("An error occurred while signing in. ", e);
            return Response.status(CONFLICT).build();
        }
        return Response.ok(token).status(CREATED).build();

    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/in")
    public Response signIn(@NotNull @FormParam("email") String email, @NotNull @FormParam("password") String password) {
        checkNotNull(email, "email"); // try to make work @NotNull annotation on param
        checkNotNull(password, "password");
        try {
            if (signService.signIn(email, password)) {
                return Response.ok(true).status(OK).build();
            } else {
                logger.info("Error Unauthorized user: " + email);
                return Response.ok(false).status(UNAUTHORIZED).build();
            }
        } catch (SignServiceException e) {
            logger.error("An error occurred while signing in. ", e);
            return Response.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/out")
    public Response signOut(@NotNull @FormParam("token") String token) {
        checkNotNull(token, "token"); // try to make work @NotNull annotation on param

        if(signService.signOut(token)){
            return Response.ok(true).status(OK).build();
        }else{
            return Response.ok(false).status(UNAUTHORIZED).build();
        }
    }


    private void checkNotNull(String parameter, String parameterName) {
        if (parameter == null || parameter.equals("")) {
            throw new BadRequestException("Required parameter was null: " + parameterName);
        }
    }
}
