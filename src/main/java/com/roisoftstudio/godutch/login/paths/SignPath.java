package com.roisoftstudio.godutch.login.paths;

import com.google.inject.Inject;
import com.roisoftstudio.godutch.json.JsonSerializer;
import com.roisoftstudio.godutch.login.exceptions.SignServiceException;
import com.roisoftstudio.godutch.login.model.JsonResponse;
import com.roisoftstudio.godutch.login.services.SignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;


@Path("/sign")
public class SignPath {
    final Logger logger = LoggerFactory.getLogger(SignPath.class);

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
            token = signService.signUp(getOrDefault(email), getOrDefault(password));
        } catch (SignServiceException e) {
            logger.error("An error occurred while signing in. ", e);
            return Response.status(Response.Status.CONFLICT).build();
        }
        String jsonResponse = jsonSerializer.toJson(new JsonResponse("Email: " + email + "\n" +
                "password: " + password + "\n" +
                "Registered Successfully. Your session token is: " + token));

        return Response.ok(jsonResponse).build();

    }

    //find out @default value for parameters annotation to remove this
    private String getOrDefault(String string) {
        return string != null ? string : "DEFAULTVALUE";
    }

    @POST
    @Path("/in")
    public Response signIn(@FormParam("token") String token) {
        if (signService.isSignedIn(token)) {
            return Response.ok("You are Signed in.").build();
        } else {
            return Response.ok("Your token is invalid: " + token).build();
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
