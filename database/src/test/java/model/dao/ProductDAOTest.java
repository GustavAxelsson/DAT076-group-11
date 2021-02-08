package model.dao;

import model.entity.Product;
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
import java.util.Random;

@RunWith(Arquillian.class)
public class ProductDAOTest {
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addClasses(ProductDAO.class, Product.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    @EJB
    private ProductDAO productDAO;
    @Before
    public void init() {
        long generatedLong = new Random().nextLong();
        productDAO.create(new Product(generatedLong, "nike", "url", 2, "desc", "shoe"));
    }
    @Test
    public void newTestCase() {
        System.out.println("it works");
        Assert.assertTrue(true); /* Some better condition */
    }
}
