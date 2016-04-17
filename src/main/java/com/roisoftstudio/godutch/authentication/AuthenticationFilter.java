package com.roisoftstudio.godutch.authentication;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.roisoftstudio.godutch.login.services.SignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
@Singleton
public class AuthenticationFilter implements ContainerRequestFilter {
    private final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Inject
    private SignService signService;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String token = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        try {
            validateToken(token);
        } catch (NotAuthorizedException e) {
            logger.error("Unauthorized Token: [" + token + "]");
            requestContext.abortWith(getUnauthorizedResponse());
        }
    }

    private Response getUnauthorizedResponse() {
        return Response.ok("Unauthorized: Invalid token.").status(Response.Status.UNAUTHORIZED).build();
    }

    private void validateToken(String token) throws NotAuthorizedException {
        if (token == null || token.equals("") || !signService.isSignedIn(token))
            throw new NotAuthorizedException("Invalid Token");
    }
}