package restApi.resources;

import restApi.model.dao.CategoryDAO;
import restApi.model.entity.Category;

import javax.ejb.EJB;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.FileNotFoundException;
import java.util.List;

@Path("category")
public class CategoryResource {
    @EJB
    CategoryDAO categoryDAO;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("list-all-categories")
    public List<Category> list() throws FileNotFoundException{
        try {
            List<Category> categories = categoryDAO.findAll();
            if (categories == null) {
                throw new FileNotFoundException();
            }

            return categories;
        } catch (Exception e) {

            throw new FileNotFoundException();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("add-category")
    public void addCategory(@NotNull String categoryName) throws InternalServerErrorException {
        try {
            Category cat = new Category(categoryName);
            categoryDAO.create(cat);
        } catch (Exception e) {
            throw new InternalServerErrorException();
        }
    }
}
