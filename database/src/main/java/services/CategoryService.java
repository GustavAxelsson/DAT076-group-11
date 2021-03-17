package services;

import dao.CategoryDAO;
import model.Category;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("category")
public class CategoryService {
    @EJB
    CategoryDAO categoryDAO;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("list-all-categories")
    public Response list() {
        List<Category> categories = categoryDAO.findAll();
        if (categories == null) {
            return Response.ok(new ArrayList<>()).build();
        }
        return Response.ok(categories).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("add-category")
    @RolesAllowed("admin")
    public void addCategory(@NotNull String categoryName) throws InternalServerErrorException {
            Category cat = new Category(categoryName);
            categoryDAO.create(cat);
    }
}
