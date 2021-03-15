package restApi.resources;

import org.eclipse.microprofile.jwt.JsonWebToken;
import restApi.model.dao.ProductOrderDAO;
import restApi.model.dao.UserDao;
import restApi.model.entity.Customer;
import restApi.model.entity.Product;
import restApi.model.entity.ProductOrder;
import restApi.model.entity.WebshopUser;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Path("order")
public class OrderService {
    @EJB
    ProductOrderDAO productOrderDAO;

    @EJB
    UserDao userDao;

    @Inject
    JsonWebToken token;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("user")
    @Path("purchase")
    public Response purchase(@NotNull List<Product> products) {
        Set<Product> productSet = new HashSet<>(products);
        String username = token.getSubject();
        if (username == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        WebshopUser user = userDao.getUserFromUsername(username);

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Customer customer = user.getCustomer();

        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        ProductOrder productOrder = new ProductOrder();
        productOrder.setCustomer(customer);
        productOrder.setProductList(productSet);


        productOrderDAO.updateProductOrder(productOrder);

        return Response.ok().build();
    }


    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("my-orders")
    @RolesAllowed("user")
    public Response getMyOrders() {
        try {
            String username =  token.getSubject();
            if (username == null) {
                return Response.ok(new ArrayList<>()).build();
            }
            WebshopUser user = userDao.getUserFromUsername(username);
            if (user == null) {
                return Response.ok(new ArrayList<>()).build();
            }

            List<ProductOrder> productOrder = productOrderDAO.getProductOrdersByCustomerEmail(user.getCustomer().getEmail());

            if (productOrder == null) {
                return Response.ok(new ArrayList<>()).build();
            }

            return Response.ok(productOrder).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
