package model.dao;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import restApi.model.dao.CustomerDAO;
import restApi.model.dao.ProductDAO;
import restApi.model.dao.ProductOrderDAO;
import restApi.model.entity.Category;
import restApi.model.entity.Customer;
import restApi.model.entity.Product;
import restApi.model.entity.ProductOrder;

import javax.ejb.EJB;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(Arquillian.class)
public class ProductOrderDAOTest extends AbstractDAOTest{
    @EJB
    private ProductOrderDAO productOrderDAO;

    @EJB
    private ProductDAO productDAO;

    @EJB
    private CustomerDAO customerDAO;

    /*              Test Customers              */
    private static final Customer c1 = new Customer("anders.a@gmail.com", "Anders", "Andersson");
    private static final Customer c2 = new Customer("sofia.k@gmail.com", "Sofia", "Karlsson");

    /*              Test Products               */
    private static final Product p1 =  new Product("Nike", "https://nike.com", 1200, "Very nice shirt");
    private static final Product p2 =  new Product("Adidas", "https://adidas.com", 1800, "Running shoe");
    private static final Product p3 =  new Product("Gucci", "https://gucci.com", 10000, "Luxury bag");

    /*              Test Categories              */
    private static final Category cat1 = new Category("T-shirt");
    private static final Category cat2 = new Category("Shoe");
    private static final Category cat3 = new Category("Bag");

    @Test
    public void createProductOrder() {
        productDAO.createAll(Arrays.asList(p1, p2, p3));
        ProductOrder productOrder = new ProductOrder(c1);
        Set<Product> products = new HashSet<>();
        products.add(p1);
        products.add(p2);
        products.add(p3);
        productOrder.setProductList(products);
        productOrderDAO.create(productOrder);
        List<ProductOrder> productOrders = productOrderDAO.findAll();
        Assert.assertEquals(productOrders.size(), 1);
    }

    @Test
    public void countTest() {
        ProductOrder productOrder = new ProductOrder(c1);
        ProductOrder productOrder1 = new ProductOrder(c2);
        productOrderDAO.createAll(Arrays.asList(productOrder, productOrder1));
        Assert.assertEquals(productOrderDAO.count(), 2);
    }

    @Test
    public void removeTest() {
        productDAO.createAll(Arrays.asList(p1, p2, p3));
        ProductOrder productOrder = new ProductOrder(c1);
        ProductOrder productOrder1 = new ProductOrder(c2);

        HashSet<Product> products1 = new HashSet<>();
        products1.add(p1);
        productOrder.setProductList(products1);

        HashSet<Product> products2 = new HashSet<>();
        products1.add(p2);
        products2.add(p3);
        productOrder.setProductList(products2);

        productOrderDAO.createAll(Arrays.asList(productOrder, productOrder1));
        Assert.assertEquals(productOrderDAO.count(), 2);
        productOrderDAO.remove(productOrder);
        Assert.assertEquals(productOrderDAO.count(), 1);
    }

    @Test
    public void getCustomerByProductOrderIdTest() {
        productDAO.createAll(Arrays.asList(p1,p2));
        ProductOrder productOrder = new ProductOrder(c1);
        Set<Product> s = new HashSet<>();
        s.add(p1);
        s.add(p2);
        productOrder.setProductList(s);
        productOrderDAO.create(productOrder);
        List<ProductOrder> productOrderList = productOrderDAO.findAll();
        long id = productOrderList.get(0).getId();
        Assert.assertEquals(c1, productOrderDAO.getCustomerByProductOrderId(id));
    }

    @Test
    public void getProductListByProductOrderIdTest() {
        productDAO.createAll(Arrays.asList(p1,p2));
        ProductOrder productOrder = new ProductOrder(c1);
        Set<Product> products = new HashSet<>();
        products.add(p1);
        products.add(p2);
        productOrder.setProductList(products);
        productOrderDAO.create(productOrder);
        List<ProductOrder> productOrderList = productOrderDAO.findAll();
        long id = productOrderList.get(0).getId();
        Assert.assertTrue(listEqualsIgnoreOrder(productDAO.findAll(),
                productOrderDAO.getProductListByProductOrderId(id)));
    }

    @Test
    public void addItemsToProductOrder() {
        productDAO.createAll(Arrays.asList(p1,p2,p3));
        ProductOrder productOrder = new ProductOrder(c1);
        productOrderDAO.create(productOrder);
        ProductOrder po = productOrderDAO.findAll().get(0);
        List<Product> products = productOrderDAO.getProductListByProductOrderId(po.getId());
        Assert.assertEquals(0, products.size());
        productOrderDAO.addItemsToProductOrder(po,Arrays.asList(p1,p2));
        List<Product> newList = productOrderDAO.getProductListByProductOrderId(po.getId());
        Assert.assertEquals(2, newList.size());
    }
}
