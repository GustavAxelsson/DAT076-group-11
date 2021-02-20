package model.dao;


import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import restApi.model.dao.CustomerDAO;
import restApi.model.dao.ProductOrderDAO;
import restApi.model.entity.Customer;
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

    private static final Customer c1 = new Customer("anders.a@gmail.com","Anders", "Andersson");
    private static final Customer c2 = new Customer( "sofia.k@gmail.com","Sofia", "Karlsson");

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
        Assert.assertEquals(persistedCustomer.getEmail(), c1.getEmail());
    }

    @Test
    public void createTwoCustomersRemoveOneByEmail() {
        customerDAO.createAll(Arrays.asList(c1, c2));
        customerDAO.removeCustomerByEmail("anders.a@gmail.com");
        Assert.assertEquals(customerDAO.count(), 1);
    }
}
