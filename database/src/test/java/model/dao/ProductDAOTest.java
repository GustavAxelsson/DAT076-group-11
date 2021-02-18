package model.dao;

import model.entity.Category;
import model.entity.Product;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.util.Arrays;
import java.util.List;

@RunWith(Arquillian.class)
public class ProductDAOTest  extends  AbstractDAOTest {

    @EJB
    private ProductDAO productDAO;

    @EJB
    private CategoryDAO categoryDAO;

    /*              Test products               */
    private static final Product p1 = new Product("Nike", "https://nike.com", 1200, "Very nice shirt");
    private static final Product p2 = new Product("Adidas", "https://adidas.com", 1800, "Running shoe");
    private static final Product p3 = new Product("Gucci", "https://gucci.com", 10000, "Luxury bag");

    /*              Test Categories             */
    private static final Category c1 = new Category("T-shirt");
    private static final Category c2 = new Category("Shoe");
    private static final Category c3 = new Category("Bag");


    @Test
    public void createTwoProducts() {
        productDAO.createAll(Arrays.asList(p1,p2));
        List<Product> productList = productDAO.findAll();
        Assert.assertEquals(productList.size(), 2);
    }

    @Test
    public void insertTwoProductsCheckCount() {
        productDAO.createAll(Arrays.asList(p1,p2));
        Assert.assertEquals(productDAO.count(), 2);
    }

    @Test
    public void insertTwoProductsWithCategories() {
        p1.setCategory(c1);
        p2.setCategory(c2);
        productDAO.createAll(Arrays.asList(p1,p2));
        Assert.assertEquals(categoryDAO.count(), 2);
        List<Category> categories = categoryDAO.findAll();
        Assert.assertEquals(categories.size(),2);
    }

}
