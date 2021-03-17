package model.dao;


import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import dao.CustomerDAO;
import dao.ProductOrderDAO;
import model.Customer;
import model.ProductOrder;

import javax.ejb.EJB;
import java.util.Arrays;
import java.util.List;

@RunWith(Arquillian.class)
public class CustomerDAOTest extends AbstractDAOTest {

    @EJB
    private CustomerDAO customerDAO;

    @EJB
    private ProductOrderDAO productOrderDAO;

    private static final Customer c1 = new Customer("anders.a@gmail.com","Anders", "Andersson");
    private static final Customer c2 = new Customer( "sofia.k@gmail.com","Sofia", "Karlsson");

    private static final ProductOrder po1 = new ProductOrder(c1);
    private static final ProductOrder po2 = new ProductOrder(c1);

    @Test
    public void createTwoCustomersTest() {
        customerDAO.createAll(Arrays.asList(c1, c2));
        List<Customer> customers = customerDAO.findAll();
        Assert.assertEquals(2, customers.size());
    }

    @Test
    public void getCustomerNamedTest() {
        customerDAO.create(c1);
        Customer c = customerDAO.getCustomerNamed(c1.getFirstName());
        Assert.assertEquals(c.getLastName(), "Andersson");
        Assert.assertEquals(1, customerDAO.count());
    }

    @Test
    public void addAnOrderWithANewCustomerAndCheckThatCustomerIsPersistedTest() {
        ProductOrder o = new ProductOrder(c1);
        productOrderDAO.create(o);
        Customer persistedCustomer = customerDAO.getCustomerByEmail(c1.getEmail());
        Assert.assertEquals(c1.getEmail(), persistedCustomer.getEmail());
    }

    @Test
    public void createTwoCustomersRemoveOneByEmailTest() {
        customerDAO.createAll(Arrays.asList(c1, c2));
        customerDAO.removeCustomerByEmail("anders.a@gmail.com");
        Assert.assertEquals(1, customerDAO.count());
    }

    @Test
    public void getProductOrdersForCustomerByEmailTest() {
        productOrderDAO.createAll(Arrays.asList(po2, po1));
        Customer persistedCustomer = customerDAO.getCustomerByEmail(c1.getEmail());
        Assert.assertTrue(listEqualsIgnoreOrder(productOrderDAO.getProductOrdersByCustomerEmail(persistedCustomer.getEmail()),
                productOrderDAO.findAll()));
    }
}
