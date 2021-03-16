package restApi.resources;

import org.eclipse.microprofile.jwt.JsonWebToken;
import restApi.model.dao.CustomerDAO;
import restApi.model.dao.UserDAO;
import restApi.model.entity.Customer;
import restApi.model.entity.WebshopUser;
import restApi.model.external.model.ExternalUser;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("user")
public class UserService {
    @EJB
    UserDAO userDao;

    @EJB
    CustomerDAO customerDAO;

    @Inject
    JsonWebToken token;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("get-all-users")
    @RolesAllowed("admin")
    public Response getAllUsers() {
        List<WebshopUser> users = userDao.findAll();
        if (users == null || users.size() < 1) {
            return Response.ok(new ArrayList<>()).build();
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
    @RolesAllowed("admin")
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
    @RolesAllowed("admin")
    public Response deleteUser(@NotNull@ QueryParam("username") String username) {
        WebshopUser user = userDao.getUserFromUsername(username);
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        userDao.remove(user);
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("user")
    @Path("set-customer")
    public Response setCustomerOnUser(@NotNull Customer customer) {
        String username = token.getSubject();
        if (username == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        WebshopUser user = userDao.getUserFromUsername(username);

        customerDAO.create(customer);
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        userDao.updateCustomer(user, customer);
        return Response.ok().build();
    }
}
