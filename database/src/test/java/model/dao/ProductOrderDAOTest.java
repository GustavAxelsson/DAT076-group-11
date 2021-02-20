package model.dao;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import restApi.model.dao.ProductDAO;
import restApi.model.dao.ProductOrderDAO;
import restApi.model.entity.Category;
import restApi.model.entity.Customer;
import restApi.model.entity.Product;
import restApi.model.entity.ProductOrder;

import javax.ejb.EJB;
import java.util.Arrays;
import java.util.List;

@RunWith(Arquillian.class)
public class ProductOrderDAOTest extends AbstractDAOTest{
    @EJB
    private ProductOrderDAO productOrderDAO;

    @EJB
    private ProductDAO productDAO;

    /*              Test Customers              */
    private static final Customer c1 = new Customer("Anders", "Andersson", "anders.a@gmail.com");
    private static final Customer c2 = new Customer("Sofia", "Karlsson", "sofia.k@gmail.com");

    /*              Test Products               */
    private static final Product p1 =  new Product("Nike", "https://nike.com", 1200, "Very nice shirt");
    private static final Product p2 =  new Product("Adidas", "https://adidas.com", 1800, "Running shoe");
    private static final Product p3 =  new Product("Gucci", "https://gucci.com", 10000, "Luxury bag");

    /*              Test Categories              */
    private static final Category cat1 = new Category("T-shirt");
    private static final Category cat2 = new Category("Shoe");
    private static final Category cat3 = new Category("Bag");

    @Test
    public void createTwoProductOrders() {
        productDAO.createAll(Arrays.asList(p1, p2, p3));
        ProductOrder productOrder = new ProductOrder(c1, Arrays.asList(p1, p2));
        ProductOrder productOrder1 = new ProductOrder(c2, Arrays.asList(p2,p3));
        productOrderDAO.createAll(Arrays.asList(productOrder, productOrder1));

        List<ProductOrder> productOrders = productOrderDAO.findAll();
        Assert.assertEquals(productOrders.size(), 2);
    }

    @Test
    public void countTest() {
        productDAO.createAll(Arrays.asList(p1, p2, p3));
        ProductOrder productOrder = new ProductOrder(c1, Arrays.asList(p1, p2));
        ProductOrder productOrder1 = new ProductOrder(c2, Arrays.asList(p2,p3));
        productOrderDAO.createAll(Arrays.asList(productOrder, productOrder1));
        Assert.assertEquals(productOrderDAO.count(), 2);
    }

    @Test
    public void removeTest() {
        productDAO.createAll(Arrays.asList(p1, p2, p3));
        ProductOrder productOrder = new ProductOrder(c1, Arrays.asList(p1, p2));
        ProductOrder productOrder1 = new ProductOrder(c2, Arrays.asList(p2,p3));
        productOrderDAO.createAll(Arrays.asList(productOrder, productOrder1));
        Assert.assertEquals(productOrderDAO.count(), 2);
        productOrderDAO.remove(productOrder);
        Assert.assertEquals(productOrderDAO.count(), 1);
    }
}
