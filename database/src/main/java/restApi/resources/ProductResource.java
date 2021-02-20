package restApi.resources;

import restApi.model.dao.ProductDAO;
import restApi.model.entity.Product;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;


@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("products")
public class ProductResource {
    @EJB
    ProductDAO productDAO;

    @GET
    public Product list(){ return productDAO.getTestProduct(); }
}
