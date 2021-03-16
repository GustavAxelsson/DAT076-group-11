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

    @GET
    @Path("test")
    public void test() {
        String asd = token.getName();
        System.out.println(asd);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("user")
    @Path("set-customer")
    public Response setCustomerOnUser(@NotNull Customer customer) {

        System.out.println("woop");

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

// curl -i -H 'Authorization : Bearer eyJraWQiOiJqd3Qua2V5IiwidHlwIjoiSldUIiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJ1c2VyMTIiLCJpc3MiOiJ3ZWJzaG9wLWNvbXBhbnkiLCJncm91cHMiOlsidXNlciJdLCJleHAiOjE2MTU3NTI3MjMsInVzZXJJZCI6NTEsImlhdCI6MTYxNTc1MjEyMywianRpIjoiNDIifQ.WuAjgC5yXjt0cBEzEulDRXuXj6T-HXv9hLSpaC_fk5C176Kpf2ay1pIctZCetscEdchjqhuifxSxd4VGrNRv47haR-kHxpozrt_9zxHLab1-ur_MBe6jrA6A41mQ2-w6hb3-u3rXV-0hk56a7JGOEva7JlbGge8b4dzJGbi4Sift_VzCeIvzu5UAQvqLx5ek-e4hA_mQ0g_ChhFAJXoUc-WyKy0CLOTEQX_dKCJqDr19GE2yAevrWfHiT-W4XIQ_taGVvBba51qiGIIriazxRNinSK2GMkLvG-K-a7476dHGZdoekDlCIobXY9oTdbAjInZVCdpzkjvbwlZTgnVXgg'  http://localhost:8080/database-2.0/api/user/test
//Bearer eyJraWQiOiJqd3Qua2V5IiwidHlwIjoiSldUIiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJzZGFzZGFzZCIsImlzcyI6IndlYnNob3AtY29tcGFueSIsImdyb3VwcyI6WyJ1c2VyIl0sImV4cCI6MTYxNTc1MDkwOCwidXNlcklkIjoyLCJpYXQiOjE2MTU3NTAzMDgsImp0aSI6IjQyIn0.C6HQp9mPpAqIFeeWu9zhhpG3-o2q0ANMjPDkpkYh0Xmlj-phfyyTlptoQKljuc1aLkGyDYSrnVWm1Fq4CwnL-xbawFfwMcezbXUZh3nuFgTKkCw7HzY4ulf6RpMfONWI1LVr98jSn_gkEEB4S9jwMnVNMeBYwo7cnYh65NJC9fy2__q8I31eQWJ78LtzqBQq5RkDejYrI2Z2mdZBm77uk5OuUvNAU6Xs_EavXZPCT87bbkWy0raZYS0c19fAXuGiZVp-T3rAZ1uzXEhXFR3_H8PEUEd8TP8AYhYqx1olh7w1oLA7v8Aa-xvD3iJZu0mu7mBG3Dhb9Dz5nRCGro46Rg


//http://localhost:8080/database-2.0/api/user/set-customer
