package com.roisoftstudio.godutch.authentication;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.roisoftstudio.godutch.login.paths.SignPath;
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
        // Get the HTTP Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        // Check if the HTTP Authorization header is present and formatted correctly
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }
        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring("Bearer".length()).trim();
        try {
            // Validate the token
            validateToken(token);
        } catch (Exception e) {
            logger.error("Unauthorized Token: [" + token + "]");
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private void validateToken(String token) throws Exception {
        if (!signService.isSignedIn(token))
            throw new UnauthorizedException("Invalid Token");
    }
}