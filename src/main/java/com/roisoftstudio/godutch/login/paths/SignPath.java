package com.roisoftstudio.godutch.login.paths;

import com.roisoftstudio.godutch.login.db.dao.InMemoryUserDao;
import com.roisoftstudio.godutch.login.exceptions.SignServiceException;
import com.roisoftstudio.godutch.login.services.DefaultSignService;
import com.roisoftstudio.godutch.login.services.SignService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;


@Path("/sign")
public class SignPath {
    final Logger logger = LogManager.getLogger(SignPath.class);

    //  @Inject
    private SignService signService = new DefaultSignService(new InMemoryUserDao());

    @PUT
    @Path("/up")
    public Response signUp(@FormParam("email") String email, @FormParam("password") String password) {
        String token;
        try {
            token = signService.signUp(getOrDefault(email), getOrDefault(password));
        } catch (SignServiceException e) {
            logger.error("An error occurred while signing in. ", e);
            return Response.status(Response.Status.CONFLICT).build();
        }
        return Response.ok(
                "Email: " + email + "\n" +
                        "password: " + password + "\n" +
                        "Registered Successfully. Your session token is: " + token).build();

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
}
