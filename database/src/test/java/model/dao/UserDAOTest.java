package model.dao;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import restApi.model.dao.UserDao;

import javax.ejb.EJB;

@RunWith(Arquillian.class)
public class UserDAOTest extends AbstractDAOTest {

    @EJB
    UserDao userDao;

    @Test
    public void createUser() {
    }
}
