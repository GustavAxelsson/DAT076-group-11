package restApi.resources;

import restApi.model.dao.CategoryDAO;
import restApi.model.dao.ProductDAO;
import restApi.model.entity.Category;
import restApi.model.entity.Product;

import javax.ejb.EJB;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.FileNotFoundException;
import java.util.List;



@Path("products")
public class ProductResource {
    @EJB
    ProductDAO productDAO;

    @EJB
    CategoryDAO categoryDAO;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("list-all-products")
    public List<Product> list(){ return productDAO.findAll(); }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("list-category-products")
    public List<Product> productsByCategory(@NotNull String name) throws FileNotFoundException {
        List<Product> products = categoryDAO.getAllProductsForCategory(name);
        if (products == null) {
            throw new FileNotFoundException();
        }
        return products;
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("product-id")
    public Product getProductById(@NotNull Long id) throws FileNotFoundException{
        Product product = productDAO.getProductById(id);
        if (product == null) {
            throw new FileNotFoundException();
        } else {
            return product;
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("list-all-categories")
    public List<Category> getAllCategories() throws FileNotFoundException {
        List<Category> categories = categoryDAO.findAll();
        if (categories == null) {
            throw new FileNotFoundException();
        }
        return categories;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("add-product")
    public void addNewProduct(String name) {
        productDAO.addNewProduct(name);
    }
}
