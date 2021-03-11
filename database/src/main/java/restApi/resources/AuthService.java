package restApi.resources;

import restApi.model.dao.UserDao;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.Set;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

@Path("auth")
public class AuthService {
    @EJB
    UserDao userDao;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("register")
    public Response register(@FormParam("username") String username, @FormParam("password")String password,
                             @Context HttpServletRequest request) {
        if (username == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            userDao.registerUser(username, password);
            Set<String> roles = new HashSet<>();
            roles.add("user");
            String token = TokenGenerator.generateToken(username, roles);

            return Response.status(Response.Status.CREATED)
                    .header(AUTHORIZATION, "Bearer ".concat(token))
                    .entity(token)
                    .build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("login")
    public Response login(@FormParam("username") String username, @FormParam("password")String password,
                          @Context HttpServletRequest request) {
        Set<String> roles = new HashSet<>();
        String role = userDao.validate(username, password);
        roles.add(role);
        if (role == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .build();
        }
        try {
            String token = TokenGenerator.generateToken(username, roles);

            return Response.status(Response.Status.OK)
                    .header(AUTHORIZATION, "Bearer ".concat(token))
                    .entity(token)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .build();
        }
    }

}
