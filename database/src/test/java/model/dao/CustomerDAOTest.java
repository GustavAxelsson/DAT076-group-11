package model.dao;

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
import java.util.List;

@RunWith(Arquillian.class)
public class CustomerDAOTest {
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addClasses(ProductOrderDAO.class, ProductOrder.class)
                .addClasses(CustomerDAO.class, Customer.class)
                .addClasses(ProductDAO.class, Product.class)
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
        Customer customer = new Customer();
//        customer.setName("Gurra G");
        customerDAO.create(customer);

        Product nikeShoe = new Product();
        nikeShoe.setName("Air");
//        nikeShoe.setCategory("Shoe");
        nikeShoe.setDescription("New nike air");
        nikeShoe.setPrice(800);
        productDAO.create(nikeShoe);

        ArrayList<Product> products = new ArrayList<Product>();
        products.add(nikeShoe);

        ProductOrder productOrder = new ProductOrder();
        productOrder.setCustomer(customer);
        productOrder.setProductList(products);
        productOrderDAO.create(productOrder);

        ArrayList<ProductOrder> productOrders = new ArrayList<>();
        productOrders.add(productOrder);

        customer.setProductOrderList(productOrders);

    }

    @Test
    public void findAllTest() { //TODO compare lists
        List<Product> products = customerDAO.findAll();
        Assert.assertFalse(products.isEmpty());
    }

    @Test
    public void countTest() {
        long countBeforeInc = customerDAO.count();
        Customer customer = new Customer();
        customerDAO.create(customer);
        long countAfterInc = customerDAO.count();
        Assert.assertEquals(countAfterInc, countBeforeInc+1);
    }

    @Test
    public void removeTest() {
        Customer customer = new Customer();
        customerDAO.create(customer);
        long countBeforeRemove = customerDAO.count();
        customerDAO.remove(customer);
        long countAfterRemove = customerDAO.count();
        Assert.assertEquals(countBeforeRemove-1, countAfterRemove);
    }
}
