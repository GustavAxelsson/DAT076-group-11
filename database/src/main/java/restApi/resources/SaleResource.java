package restApi.resources;

import restApi.model.dao.ProductDAO;
import restApi.model.dao.SaleDAO;
import restApi.model.entity.Category;
import restApi.model.entity.Product;
import restApi.model.entity.Sale;
import restApi.model.entity.SaleProduct;

import javax.ejb.EJB;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.FileNotFoundException;
import java.util.List;

@Path("sale")
public class SaleResource {
    @EJB
    SaleDAO saleDAO;
    @EJB
    ProductDAO productDAO;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("add-sale")
    public void addNewSale(@NotNull Sale sale) throws IllegalArgumentException{
        if (sale == null) {
            throw new IllegalArgumentException();
        }
        try {
            saleDAO.addNewSale(sale);
        } catch (Exception e) {
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("list-all-sales")
    public List<Sale> getAllSales() throws FileNotFoundException {
        List<Sale> sales = saleDAO.findAll();
        if (sales == null) {
            throw new FileNotFoundException();
        }
        return sales;
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("list-sale-products")
    public List<Product> allProductsBySale(@NotNull Sale sale) throws FileNotFoundException {
        List<Product> products = saleDAO.getProductsFromSale(sale);
        if (products == null) {
            throw new FileNotFoundException();
        }
        return products;
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
    public void setCurrentSale(@NotNull Sale sale) throws IllegalArgumentException{
        if (sale == null) {
            throw new IllegalArgumentException();
        }
        try {
            saleDAO.setCurrentSale(sale);
        } catch (Exception e) {
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("get-current-sale")
    public Sale getCurrentSale() throws FileNotFoundException {
        Sale sale = saleDAO.getCurrentSale();
        if (sale == null) {
            throw new FileNotFoundException();
        }
        return sale;
    }
}
