package restApi.resources;

import restApi.model.dao.SaleDAO;
import restApi.model.entity.Sale;

import javax.ejb.EJB;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("sale")
public class SaleResource {
    @EJB
    SaleDAO saleDAO;

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
}
