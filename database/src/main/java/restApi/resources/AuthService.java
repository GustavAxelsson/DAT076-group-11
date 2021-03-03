package restApi.resources;

import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.security.Principal;

@Path("auth")
public class AuthService {

    @Inject
    JsonWebToken jsonWebToken;

    @Inject
    Principal principal;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    @Path("get-roles")
    public String getRoles() {
        return principal.getName() + " " + jsonWebToken.getGroups();
    }

}