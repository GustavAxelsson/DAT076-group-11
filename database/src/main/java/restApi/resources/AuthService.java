package restApi.resources;

import org.eclipse.microprofile.jwt.JsonWebToken;
import restApi.model.entity.User;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

@Path("auth")
public class AuthService {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("login")
    public Response login(@FormParam("username") String username, @FormParam("password")String password,
                          @Context HttpServletRequest request) {
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        try {
            String token = TokenGenerator.generateToken(username, roles);

            return Response.status(Response.Status.OK)
                    .header(AUTHORIZATION, "Bearer ".concat(token))
                    .entity(token)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .build();        }
    }

}
