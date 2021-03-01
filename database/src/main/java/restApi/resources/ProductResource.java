package restApi.resources;

import restApi.model.dao.ProductDAO;
import restApi.model.entity.Product;

import javax.ejb.EJB;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;



@Path("products")
public class ProductResource {
    @EJB
    ProductDAO productDAO;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("list-all-products")
    public List<Product> list(){ return productDAO.findAll(); }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("add-product")
    public void addNewProduct(@NotNull Product product) throws IllegalArgumentException{
        if (product == null) {
            throw new IllegalArgumentException();
        }
        try {
            productDAO.addNewProduct(product);
        } catch (Exception e) {
        }
    }
}
