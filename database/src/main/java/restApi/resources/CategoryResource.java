package restApi.resources;

import restApi.model.dao.CategoryDAO;
import restApi.model.entity.Category;

import javax.ejb.EJB;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileNotFoundException;
import java.util.List;

@Path("category")
public class CategoryResource {
    @EJB
    CategoryDAO categoryDAO;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("list-all-categories")
    public Response list() {
            List<Category> categories = categoryDAO.findAll();
            if (categories == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(categories).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("add-category")
    public void addCategory(@NotNull String categoryName) throws InternalServerErrorException {
            Category cat = new Category(categoryName);
            categoryDAO.create(cat);
    }
}
