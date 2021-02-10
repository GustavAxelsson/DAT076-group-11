package model.dao;

import model.entity.Customer;
import model.entity.Product;
import model.entity.ProductOrder;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.util.ArrayList;
import java.util.List;

@RunWith(Arquillian.class)
public class ProductDAOTest {
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addClasses(ProductDAO.class, Product.class)
                .addClasses(ProductOrderDAO.class, ProductOrder.class)
                .addClasses(CustomerDAO.class, Customer.class)
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
        customer.setName("Gurra G");
        customerDAO.create(customer);

        Product woolCoat = new Product();
        woolCoat.setName("Wool");
        woolCoat.setCategory("Shirt");
        woolCoat.setDescription("Very cosy");
        woolCoat.setPrice(500);
        productDAO.create(woolCoat);

        ArrayList<Product> products = new ArrayList<Product>();
        products.add(woolCoat);

        ProductOrder productOrder = new ProductOrder();
        productOrder.setCustomer(customer);
        productOrder.setProductList(products);

        woolCoat.setProductOrder(productOrder);

    }

    @Test
    public void findAllTest() { //TODO compare lists
        List<Product> products = productDAO.findAll();
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
}
