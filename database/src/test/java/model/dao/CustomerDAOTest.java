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
public class CustomerDAOTest {
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addClasses(ProductOrderDAO.class, ProductOrder.class)
                .addClasses(CustomerDAO.class, Customer.class)
                .addClasses(ProductDAO.class, Product.class)
                .addClasses(CategoryDAO.class, Category.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

    }
    @EJB
    private ProductOrderDAO productOrderDAO;

    @EJB
    private ProductDAO productDAO;

    @EJB
    private CustomerDAO customerDAO;

    @Before
    public void init() {
        Customer customer = new Customer("Anders", "Andersson", "anders.a@gmail.com");
        Customer customer2 = new Customer("Sofia", "Karlsson", "sofia.k@gmail.com");

        Product product = new Product("Nike", "https://nike.com", 1200, "Very nice shirt");
        Product product2 = new Product("Adidas", "https://adidas.com", 1800, "Running shoe");
        Product product3 = new Product("Gucci", "https://gucci.com", 10000, "Luxury bag");
        product.setCategory(new Category("T-shirt"));
        product2.setCategory(new Category("Shoe"));
        product3.setCategory(new Category("Bag"));
        productDAO.createAll(Arrays.asList(product, product2, product3));

        ProductOrder order = new ProductOrder();
        order.setCustomer(customer);
        order.setProductList(Arrays.asList(product, product2));

        ProductOrder order2 = new ProductOrder();
        order2.setCustomer(customer2);
        order2.setProductList(Collections.singletonList(product3));

        productOrderDAO.createAll(Arrays.asList(order, order2));

    }

    @Test
    public void findAllTest() { //TODO compare lists
        List<Customer> customers = customerDAO.findAll();
        customers.forEach(customer -> {
            System.out.println("Customer");
            System.out.println(customer);
        });
        Assert.assertFalse(customers.isEmpty());
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
}
