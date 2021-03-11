package model.dao;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import restApi.model.dao.CategoryDAO;
import restApi.model.dao.ProductDAO;
import restApi.model.dao.UserDao;
import restApi.model.entity.Category;
import restApi.model.entity.Product;

import javax.ejb.EJB;
import java.util.List;

@RunWith(Arquillian.class)
public class CategoryDAOTest extends AbstractDAOTest {

    @EJB
    CategoryDAO categoryDAO;

    @EJB
    ProductDAO productDAO;


    @Test
    public void createProductWithCategory() {
        Product product = new Product();
        product.setName("Nike");
        Category cat = new Category();
        cat.setName("shoes");
        product.setCategory(cat);
        productDAO.create(product);
        List<Category> categories = categoryDAO.findAll();
        Assert.assertEquals(1, categories.size());
        Assert.assertEquals("shoes", categories.get(0).getName());
    }

    @Test
    public void createProductAndChangeCategory() {
        Product product = new Product();
        product.setName("Nike");
        Category cat = new Category();
        cat.setName("shoes");
        product.setCategory(cat);
        productDAO.create(product);
        Assert.assertEquals("shoes", categoryDAO.findAll().get(0).getName());
        Product product1 = productDAO.getProductByName("Nike");
        Assert.assertNotNull(product1);

        Category category = new Category();
        category.setName("Hat");

        productDAO.updateProductCategory(product, category);
        Assert.assertEquals(2, categoryDAO.count());
    }

    @Test
    public void changeNameOfCategory() {
        Product product = new Product();
        product.setName("Nike");
        Category cat = new Category();
        cat.setName("shoes");
        product.setCategory(cat);
        productDAO.create(product);
        List<Category> categories = categoryDAO.findAll();
        Assert.assertEquals(1, categories.size());
        Assert.assertEquals("shoes", categories.get(0).getName());
        categoryDAO.changeNameOfCategory(categories.get(0), "Hat");
        List<Product> products = productDAO.findAll();
        Assert.assertEquals("Hat", products.get(0).getCategory().getName());
    }
}
