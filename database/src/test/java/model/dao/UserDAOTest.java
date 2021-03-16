package model.dao;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import restApi.model.dao.CustomerDAO;
import restApi.model.dao.UserDao;
import restApi.model.entity.Customer;
import restApi.model.entity.WebshopUser;

import javax.ejb.EJB;

@RunWith(Arquillian.class)
public class UserDAOTest extends AbstractDAOTest {

    @EJB
    UserDao userDao;

    @EJB
    CustomerDAO customerDAO;

    @Test
    public void registerUserTest() {
        userDao.registerUser("username", "password");
        WebshopUser user = userDao.findAll().get(0);
        Assert.assertEquals("username", user.getUsername());
        Assert.assertEquals("user", user.getRole());
    }

    @Test
    public void validateUserTest() {
        userDao.registerUser("username", "password");
        WebshopUser user = userDao.findAll().get(0);
        String role = userDao.validate(user, "password");
        Assert.assertEquals("user", role);
    }

    @Test
    public void getUserFromUsernameTest() {
        userDao.registerUser("username", "password");
        WebshopUser user = userDao.findAll().get(0);
        Assert.assertEquals(user, userDao.getUserFromUsername("username"));
    }

    @Test
    public void updateRoleOnUserTest() {
        userDao.registerUser("username", "password");
        WebshopUser user = userDao.findAll().get(0);
        Assert.assertEquals("user", user.getRole());
        userDao.updateRoleOnUser(user, "admin");
        Assert.assertEquals("admin", user.getRole());
    }

    @Test
    public void updateCustomerTest() {
        Customer c1 = new Customer("Anna.email.com");
        customerDAO.create(c1);
        userDao.registerUser("username", "password");
        WebshopUser user = userDao.findAll().get(0);
        userDao.updateCustomer(user, c1);
        user = userDao.findAll().get(0);
        Assert.assertEquals(c1, user.getCustomer());
    }
}
