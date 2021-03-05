package restApi.resources;
import org.glassfish.jersey.media.multipart.FormDataParam;
import restApi.model.dao.CategoryDAO;
import restApi.model.dao.ProductDAO;
import restApi.model.dao.ProductImageDAO;
import restApi.model.entity.Product;
import restApi.model.entity.ProductImage;

import javax.ejb.EJB;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;



@Path("products")
public class ProductResource {
    @EJB
    ProductDAO productDAO;

    @EJB
    CategoryDAO categoryDAO;

    @EJB
    ProductImageDAO productImageDAO;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("list-all-products")
    public List<Product> list() throws FileNotFoundException {
        try {
            List<Product> products = productDAO.findAll();
            if (products == null) {
                throw new FileNotFoundException();
            }
            return products;

        } catch (Exception e) {
            throw new InternalServerErrorException();
        }

    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("list-category-products")
    public List<Product> allProductsByCategory(@NotNull String name) throws FileNotFoundException {
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
    public Product getProductById(@QueryParam("id")@NotNull Long id) throws FileNotFoundException{
        Product product = productDAO.getProductById(id);
        if (product == null) {
            throw new FileNotFoundException();
        } else {
            return product;
        }
    }

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
            throw new InternalServerErrorException();
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("purchase")
    public boolean purchase(@QueryParam("products")@NotNull List<Product> products) throws FileNotFoundException {
        return false;
    }


    @POST
    @Path("/upload-image")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void uploadFile(@FormDataParam("file") InputStream inputStream) {

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;

            while ((length = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, length);
            }

            ProductImage upload = new ProductImage("filename",
                    byteArrayOutputStream.toByteArray());
            productImageDAO.create(upload);
        } catch (Exception e) {
        }
    }
}
