package frontend.resources;

import model.dao.ProductDAO;
import model.entity.Product;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

@Path("products")
public class ProductResource {
    @EJB
    ProductDAO productDAO;

    @GET
    public List<Product> list(){ return new ArrayList<>(productDAO.findAll());
    }
}
