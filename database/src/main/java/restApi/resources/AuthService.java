package restApi.resources;

import org.eclipse.microprofile.jwt.JsonWebToken;
import restApi.model.entity.User;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Path("auth")
public class AuthService {

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("login")
    public Response login(@QueryParam("username") String username, @QueryParam("password") String password) {
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        try {
            String token = TokenGenerator.generateToken(username, roles);
            return Response.ok(token).build();
        } catch (Exception e) {
           return null;
        }
    }

}
