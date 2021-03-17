package model.dao;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import dao.CustomerDAO;
import dao.UserDAO;
import model.Customer;
import model.WebshopUser;

import javax.ejb.EJB;

@RunWith(Arquillian.class)
public class UserDAOTest extends AbstractDAOTest {

    @EJB
    UserDAO userDAO;

    @EJB
    CustomerDAO customerDAO;

    @Test
    public void registerUserTest() {
        userDAO.registerUser("username", "password");
        WebshopUser user = userDAO.findAll().get(0);
        Assert.assertEquals("username", user.getUsername());
        Assert.assertEquals("user", user.getRole());
    }

    @Test
    public void validateUserTest() {
        userDAO.registerUser("username", "password");
        WebshopUser user = userDAO.findAll().get(0);
        String role = userDAO.validate(user, "password");
        Assert.assertEquals("user", role);
    }

    @Test
    public void getUserFromUsernameTest() {
        userDAO.registerUser("username", "password");
        WebshopUser user = userDAO.findAll().get(0);
        Assert.assertEquals(user, userDAO.getUserFromUsername("username"));
    }

    @Test
    public void updateRoleOnUserTest() {
        userDAO.registerUser("username", "password");
        WebshopUser user = userDAO.findAll().get(0);
        Assert.assertEquals("user", user.getRole());
        userDAO.updateRoleOnUser(user, "admin");
        Assert.assertEquals("admin", user.getRole());
    }

    @Test
    public void updateCustomerTest() {
        Customer c1 = new Customer("Anna.email.com");
        customerDAO.create(c1);
        userDAO.registerUser("username", "password");
        WebshopUser user = userDAO.findAll().get(0);
        userDAO.updateCustomer(user, c1);
        user = userDAO.findAll().get(0);
        Assert.assertEquals(c1, user.getCustomer());
    }
}
