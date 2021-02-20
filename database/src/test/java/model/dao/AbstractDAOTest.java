package model.dao;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import restApi.model.dao.CategoryDAO;
import restApi.model.dao.CustomerDAO;
import restApi.model.dao.ProductDAO;
import restApi.model.dao.ProductOrderDAO;
import restApi.model.entity.Category;
import restApi.model.entity.Customer;
import restApi.model.entity.Product;
import restApi.model.entity.ProductOrder;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

abstract class AbstractDAOTest {
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addClasses(ProductOrderDAO.class, ProductOrder.class)
                .addClasses(CustomerDAO.class, Customer.class)
                .addClasses(ProductDAO.class, Product.class)
                .addClasses(CategoryDAO.class, Category.class)
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
        utx.commit();
    }

    private void startTransaction() throws Exception {
        utx.begin();
        em.joinTransaction();
    }
}
