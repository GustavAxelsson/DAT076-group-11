package restApi.resources;

import restApi.model.dao.CategoryDAO;
import restApi.model.entity.Category;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("category")
public class CategoryResource {
    @EJB
    CategoryDAO categoryDAO;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("list-all-categories")
    public List<Category> list(){ return categoryDAO.findAll(); }
}
