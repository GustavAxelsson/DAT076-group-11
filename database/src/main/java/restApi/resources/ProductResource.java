package restApi.resources;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import restApi.model.dao.*;
import restApi.model.entity.*;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Path("products")
public class ProductResource {
    @EJB
    ProductDAO productDAO;

    @EJB
    CategoryDAO categoryDAO;

    @EJB
    ProductImageDAO productImageDAO;

    @EJB
    CustomerDAO customerDAO;

    @EJB
    ProductOrderDAO productOrderDAO;

    @EJB
    UserDao userDao;

    @Inject
    JsonWebToken token;

    @Inject
    Principal principal;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("list-all-products")
    public List<Product> list() {
        List<Product> products = productDAO.findAll();
        if (products == null) {
            return new ArrayList<>();
        }
        return products;
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("list-category-products")
    public Response allProductsByCategory(@NotNull String name) throws FileNotFoundException {
        List<Product> products = categoryDAO.getAllProductsForCategory(name);
        if (products == null || products.size() < 1) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(products).build();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("product-id")
    public Response getProductById(@QueryParam("id")@NotNull Long id) throws FileNotFoundException{
        Product product = productDAO.getProductById(id);
        if (product == null) {
            Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(product).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("add-product")
    public void addNewProduct(@NotNull Product product) {
            productDAO.addNewProduct(product);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("user")
    @Path("purchase")
    public Response purchase(@NotNull List<Product> products) {

        Set<Product> productSet = new HashSet<>(products);

        System.out.println(token.getName());
        System.out.println(token.getAudience());
        System.out.println(token.getIssuedAtTime());
        System.out.println(token.getExpirationTime());
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
     @Path("list-my-orders")
     public Response getMyOrders() {
       String username =  token.getSubject();
       if (username == null) {
           return Response.ok(new ArrayList<>()).build();
       }
      WebshopUser user = userDao.getUserFromUsername(username);
       if (user == null) {
           return Response.ok(new ArrayList<>()).build();
       }

       return Response.ok(user.getCustomer().getProductOrderList()).build();
     }


    @POST
    @Path("/upload-image")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@FormDataParam("file") InputStream inputStream,
                           @FormDataParam("file") FormDataContentDisposition fileDetail) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;

            while ((length = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, length);
            }

            ProductImage upload = new ProductImage(fileDetail.getName(),
                    byteArrayOutputStream.toByteArray());

            long id = Long.parseLong(fileDetail.getFileName());

            Product product = productDAO.getProductById(id);

            if (product == null) {
                Response.status(Response.Status.NOT_FOUND);
            }

            productDAO.updateProductImage(product, upload);

            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("download-image")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getProductImageById(@QueryParam("id") long id) {
            ProductImage randomFile = productImageDAO.getProductImageById(id);
            if (randomFile == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(randomFile.getData(), MediaType.APPLICATION_OCTET_STREAM)
                    .header("Content-Disposition",
                            "attachment; filename=" + randomFile.getFileName()).build();

    }
}
