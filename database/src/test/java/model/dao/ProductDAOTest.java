package model.dao;

import model.entity.Category;
import model.entity.Customer;
import model.entity.Product;
import model.entity.ProductOrder;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(Arquillian.class)
public class ProductDAOTest {
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addClasses(ProductDAO.class, Product.class)
                .addClasses(ProductOrderDAO.class, ProductOrder.class)
                .addClasses(CustomerDAO.class, Customer.class)
                .addClasses(CategoryDAO.class, Category.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    @EJB
    private ProductOrderDAO productOrderDAO;

    @EJB
    private CustomerDAO customerDAO;

    @EJB
    private ProductDAO productDAO;

    @Before
    public void init() {
        Customer customer = new Customer("Anders", "Andersson", "anders.a@gmail.com");
        Customer customer2 = new Customer("Sofia", "Karlsson", "sofia.k@gmail.com");

        Product product = new Product("Nike", "https://nike.com", 1200, "Very nice shirt");
        Product product2 = new Product("Adidas", "https://adidas.com", 1800, "Running shoe");
        Product product3 = new Product("Gucci", "https://gucci.com", 10000, "Luxury bag");
        productDAO.createAll(Arrays.asList(product, product2, product3));

        ProductOrder order = new ProductOrder();
        order.setCustomer(customer);
        order.setProductList(Arrays.asList(product, product2));

        ProductOrder order2 = new ProductOrder();
        order.setCustomer(customer2);
        order.setProductList(Collections.singletonList(product3));
        productOrderDAO.createAll(Collections.singletonList(order2));

    }

    @Test
    public void findAllTest() { //TODO compare lists
        List<Product> products = productDAO.findAll();
        products.forEach(product -> {
            System.out.println("Product");
            System.out.println(product);
        });
        Assert.assertFalse(products.isEmpty());
    }

    @Test
    public void countTest() {
        long countBeforeInc = productDAO.count();
        Product shoe = new Product();
        productDAO.create(shoe);
        long countAfterInc = productDAO.count();
        Assert.assertEquals(countAfterInc, countBeforeInc+1);
    }

    @Test
    public void removeTest() {
        Product shoe = new Product();
        productDAO.create(shoe);
        long countBeforeRemove = productDAO.count();
        productDAO.remove(shoe);
        long countAfterRemove = productDAO.count();
        Assert.assertEquals(countBeforeRemove-1, countAfterRemove);
    }

    @Test()
    public void checkQuantityTest() {

        Customer customer = new Customer();
        customerDAO.create(customer);

        Product shoe = new Product();
        shoe.setPrice(500);
        shoe.setName("Nike");
        productDAO.create(shoe);

        ProductOrder order = new ProductOrder();
        order.setCustomer(customer);
        List<Product> productList =  new ArrayList<>();
        productList.add(shoe);
        order.setProductList(productList);
        productOrderDAO.create(order);
    }
}
