package restApi.resources;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import restApi.model.dao.CategoryDAO;
import restApi.model.dao.ProductDAO;
import restApi.model.dao.ProductImageDAO;
import restApi.model.entity.Product;
import restApi.model.entity.ProductImage;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


@Path("products")
public class ProductService {
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
    public List<Product> list() {
        List<Product> products = productDAO.findAll();
        if (products == null) {
            return new ArrayList<>();
        }
        return products;
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
    @RolesAllowed("admin")
    public void addNewProduct(@NotNull Product product) {
            productDAO.addNewProduct(product);
    }


    @POST
    @Path("/upload-image")
    @RolesAllowed("admin")
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

            productImageDAO.updateProductImage(product, upload);

            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("download-image")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getProductImageById(@NotNull @QueryParam("id") long id) {
        try {
            ProductImage image = productImageDAO.getProductImageByProductId(id);
            if (image == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(image.getData(), MediaType.APPLICATION_OCTET_STREAM)
                    .header("Content-Disposition",
                            "attachment; filename=" + image.getFileName()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
