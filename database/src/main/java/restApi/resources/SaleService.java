package restApi.resources;

import restApi.model.dao.ProductDAO;
import restApi.model.dao.SaleDAO;
import restApi.model.entity.Category;
import restApi.model.entity.Product;
import restApi.model.entity.Sale;
import restApi.model.entity.SaleProduct;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Path("sale")
public class SaleService {
    @EJB
    SaleDAO saleDAO;
    @EJB
    ProductDAO productDAO;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("add-sale")
    @RolesAllowed("admin")
    public void addNewSale(@NotNull Sale sale) {
        saleDAO.addNewSale(sale);
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("list-all-sales")
    public Response getAllSales() throws FileNotFoundException {
        List<Sale> sales = saleDAO.findAll();
        if (sales == null) {
            return Response.ok(new ArrayList<>(), MediaType.APPLICATION_JSON).build();
        }
        return Response.ok(sales, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("list-sale-products")
    public Response getAllProductsForSale(@QueryParam("id") long id) {
        Sale sale = saleDAO.getSaleById(id);
        List<Product> products = saleDAO.getProductsFromSale(sale);
        if (products == null) {
            return Response.ok(new ArrayList<>(), MediaType.APPLICATION_JSON).build();
        }
        return Response.ok(products, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("add-product-sale")
    public void addProductToSale(SaleProduct saleProduct) throws IllegalArgumentException{
        Product product = productDAO.getProductById(saleProduct.getProductId());
        Sale sale = saleDAO.getSaleById(saleProduct.getSaleId());
        saleDAO.addProductToSale(product, sale);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("set-current-sale")
    public void setCurrentSale(@NotNull Sale sale) {
        saleDAO.setCurrentSale(sale);
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("get-current-sale")
    public Response getCurrentSale() {
        Sale sale = saleDAO.getCurrentSale();
        if (sale == null) {
           return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(sale, MediaType.APPLICATION_JSON).build();
    }
}
