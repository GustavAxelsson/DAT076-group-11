package model.dao;

import model.entity.Customer;
import model.entity.ProductOrder;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.util.Arrays;
import java.util.List;

@RunWith(Arquillian.class)
public class CustomerDAOTest extends AbstractDAOTest {

    @EJB
    private CustomerDAO customerDAO;

    @EJB
    private ProductOrderDAO productOrderDAO;

    void insertData() throws Exception {
        utx.begin();
        em.joinTransaction();
        Customer c1 = new Customer("anders.a@gmail.com","Anders", "Andersson");
        Customer c2 = new Customer( "sofia.k@gmail.com","Sofia", "Karlsson");
        customerDAO.createAll(Arrays.asList(c1, c2));
        utx.commit();
        em.clear();
    }


    @Test
    public void findAllTest() {
        List<Customer> customers = customerDAO.findAll();
        Assert.assertEquals(2, customers.size());
    }

    @Test
    public void getCustomerNamed() {
        Customer c = customerDAO.getCustomerNamed("Anders");
        Customer c2 = customerDAO.getCustomerNamed("Jacob");
        Assert.assertEquals(c.getLastName(), "Andersson");
        Assert.assertNull(c2);
    }

    @Test
    public void addAnOrderWithANewCustomerAndCheckThatCustomerIsPersisted() {
        String customerEmail = "abc@gmail.com";
        String customerPersonalNumber = "85-11-24-1111";
        ProductOrder o = new ProductOrder(new Customer(customerEmail, customerPersonalNumber));
        productOrderDAO.create(o);

        Customer persistedCustomer = customerDAO.getCustomerByEmail(customerEmail);
        Assert.assertEquals(persistedCustomer.getEmail(), customerEmail);
        Assert.assertEquals(persistedCustomer.getPersonalNumber(), customerPersonalNumber);
        Assert.assertEquals(customerDAO.count(), 3);
    }

    @Test
    public void removeCustomerByEmail() {
        customerDAO.removeCustomerByEmail("anders.a@gmail.com");
        Assert.assertEquals(customerDAO.count(), 1);
    }
}
