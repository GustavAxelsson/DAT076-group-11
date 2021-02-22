package model.dao;


import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import restApi.model.dao.CustomerDAO;
import restApi.model.dao.ProductOrderDAO;
import restApi.model.entity.Customer;
import restApi.model.entity.Product;
import restApi.model.entity.ProductOrder;

import javax.ejb.EJB;
import java.util.Arrays;
import java.util.List;

@RunWith(Arquillian.class)
public class CustomerDAOTest extends AbstractDAOTest {

    @EJB
    private CustomerDAO customerDAO;

    @EJB
    private ProductOrderDAO productOrderDAO;

    private static final Product p1 =  new Product("Nike", "https://nike.com", 1200, "Very nice shirt");
    private static final Product p2 =  new Product("Adidas", "https://adidas.com", 1800, "Running shoe");
    private static final Product p3 =  new Product("Gucci", "https://gucci.com", 10000, "Luxury bag");

    private static final Customer c1 = new Customer("anders.a@gmail.com","Anders", "Andersson");
    private static final Customer c2 = new Customer( "sofia.k@gmail.com","Sofia", "Karlsson");

    private static final ProductOrder po1 = new ProductOrder(c1);
    private static final ProductOrder po2 = new ProductOrder(c1);

    @Test
    public void createTwoCustomers() {
        customerDAO.createAll(Arrays.asList(c1, c2));
        List<Customer> customers = customerDAO.findAll();
        Assert.assertEquals(2, customers.size());
    }

    @Test
    public void getCustomerNamed() {
        customerDAO.create(c1);
        Customer c = customerDAO.getCustomerNamed(c1.getFirstName());
        Assert.assertEquals(c.getLastName(), "Andersson");
        Assert.assertEquals(customerDAO.count(), 1);
    }

    @Test
    public void addAnOrderWithANewCustomerAndCheckThatCustomerIsPersisted() {
        ProductOrder o = new ProductOrder(c1);
        productOrderDAO.create(o);
        Customer persistedCustomer = customerDAO.getCustomerByEmail(c1.getEmail());
        System.out.println(persistedCustomer);
        Assert.assertEquals(persistedCustomer.getEmail(), c1.getEmail());
    }

    @Test
    public void createTwoCustomersRemoveOneByEmail() {
        customerDAO.createAll(Arrays.asList(c1, c2));
        customerDAO.removeCustomerByEmail("anders.a@gmail.com");
        Assert.assertEquals(customerDAO.count(), 1);
    }

    @Test
    public void getProductOrdersForCustomerByEmailTest() {
        productOrderDAO.createAll(Arrays.asList(po2, po1));
        Customer persistedCustomer = customerDAO.getCustomerByEmail(c1.getEmail());
        Assert.assertTrue(listEqualsIgnoreOrder(productOrderDAO.getProductOrdersByCustomerEmail(persistedCustomer.getEmail()),
                productOrderDAO.findAll()));
    }
}
