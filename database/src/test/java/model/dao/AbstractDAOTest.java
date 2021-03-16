package model.dao;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import restApi.model.dao.*;
import restApi.model.entity.*;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.util.HashSet;
import java.util.List;

abstract class AbstractDAOTest {
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addClasses(ProductOrderDAO.class, ProductOrder.class)
                .addClasses(CustomerDAO.class, Customer.class)
                .addClasses(ProductDAO.class, Product.class)
                .addClasses(CategoryDAO.class, Category.class)
                .addClasses(ProductImageDAO.class, ProductImage.class)
                .addClasses(SaleDAO.class, Sale.class)
                .addClasses(UserDAO.class, WebshopUser.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @PersistenceContext
    EntityManager em;

    @Inject
    UserTransaction utx;

    @Before
    public void preparePersistenceTest() throws Exception {
        clearData();
        startTransaction();
    }

    private void clearData() throws Exception {
        utx.begin();
        em.joinTransaction();
        em.createQuery("delete from Customer ").executeUpdate();
        em.createQuery("delete from ProductOrder ").executeUpdate();
        em.createQuery("delete from Product ").executeUpdate();
        em.createQuery("delete from Category ").executeUpdate();
        em.createQuery("delete from Sale ").executeUpdate();
        em.createQuery("delete from ProductImage").executeUpdate();
        em.createQuery("delete from WebshopUser ").executeUpdate();
        utx.commit();
    }

    private void startTransaction() throws Exception {
        utx.begin();
        em.joinTransaction();
    }

    public static <T> boolean listEqualsIgnoreOrder(List<T> list1, List<T> list2) {
        return new HashSet<>(list1).equals(new HashSet<>(list2));
    }
}
