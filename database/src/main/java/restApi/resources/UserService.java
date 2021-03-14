package restApi.resources;

import restApi.model.dao.UserDao;
import restApi.model.entity.WebshopUser;
import restApi.model.external.model.ExternalUser;

import javax.ejb.EJB;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("user")
public class UserService {
    @EJB
    UserDao userDao;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("get-all-users")
    public Response getAllUsers() {
        List<WebshopUser> users = userDao.findAll();
        if (users == null || users.size() < 1) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        List<ExternalUser> externalUsers = users.stream()
                .map(user -> {
                    return new ExternalUser(user.getUsername(), user.getRole());
                }).collect(Collectors.toList());
        return Response.ok(externalUsers).build();
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("update-user-role")
    public Response updateUserRole(
            @QueryParam("username") @NotNull String username,
            @QueryParam("role") @NotNull String role) {
        if (!(role.equals("user") || role.equals("admin"))) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        WebshopUser user = userDao.getUserFromUsername(username);
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        userDao.updateRoleOnUser(user, role);
        return Response.ok().build();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@NotNull@ QueryParam("username") String username) {
        WebshopUser user = userDao.getUserFromUsername(username);
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        userDao.remove(user);
        return Response.ok().build();
    }

}
