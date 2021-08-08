package com.techtalksteve.quarkus.jwt.rest;

import com.techtalksteve.quarkus.jwt.exception.AuthenticationPasswordException;
import com.techtalksteve.quarkus.jwt.exception.AuthenticationUsernameException;
import com.techtalksteve.quarkus.jwt.service.UserService;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.StringJoiner;

@Path("/api/authentication")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@AllArgsConstructor(onConstructor = @__(@Inject))
public class UserResource {
    private final UserService userService;

    @Inject
    JsonWebToken jwt;

    @PermitAll
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(AuthenticationRequest authRequest) throws AuthenticationUsernameException, AuthenticationPasswordException {
        final String token = userService.authenticate(authRequest);
        return Response.ok(new AuthenticationResponse(token)).build();
    }

    @RolesAllowed("ROLE_APPLICATION")
    @GET
    @Path("roles")
    @Produces(MediaType.APPLICATION_JSON)
    public String checkRolesAllowed(){
        return String.format("User %s has the following roles: %s", jwt.getName(), String.join(", ", jwt.getGroups()));
    }

}
