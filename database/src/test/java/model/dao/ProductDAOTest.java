package model.dao;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import dao.CategoryDAO;
import dao.ProductDAO;
import model.Category;
import model.Product;

import javax.ejb.EJB;
import java.util.ArrayList;
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
    private static final Product p3 = new Product("Gucci", "https://gucci.com", 10000, "Luxury bag", 2);

    /*              Test Categories             */
    private static final Category c1 = new Category("T-shirt");
    private static final Category c2 = new Category("Shoe");


    @Test
    public void addSameProductTest() {
        productDAO.addNewProduct(p1);
        productDAO.addNewProduct(p1);
        List<Product> productList = productDAO.findAll();
        Assert.assertEquals(1, productList.size());
    }

    @Test
    public void createTwoProductsTest() {
        productDAO.createAll(Arrays.asList(p1,p2));
        List<Product> productList = productDAO.findAll();
        Assert.assertEquals(2, productList.size());
    }

    @Test
    public void insertTwoProductsCheckCountTest() {
        productDAO.createAll(Arrays.asList(p1,p2));
        Assert.assertEquals(2, productDAO.count());
    }

    @Test
    public void insertTwoProductsWithCategoriesTest() {
        p1.setCategory(c1);
        p2.setCategory(c2);
        productDAO.createAll(Arrays.asList(p1,p2));
        Assert.assertEquals(categoryDAO.count(), 2);
        List<Category> categories = categoryDAO.findAll();
        Assert.assertEquals(2, categories.size());
    }

    @Test
    public void getProductByNameTest() {
        productDAO.create(p1);
        Assert.assertEquals(p1 ,productDAO.getProductByName("Nike"));
    }

    @Test
    public void getProductsByCategoryTest() {
        p1.setCategory(c1);
        List<Product> expectedList = new ArrayList<>();
        expectedList.add(p1);
        productDAO.create(p1);
        Assert.assertEquals(expectedList, productDAO.getProductsByCategory(c1));
    }

    @Test public void getProductByIdTest() {
        productDAO.addNewProduct(p1);
        List<Product> productList = productDAO.findAll();
        Product p = productList.get(0);
        Assert.assertEquals(p, productDAO.getProductById(p.getId()));
    }
}
