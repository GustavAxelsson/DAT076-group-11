package model.dao;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import restApi.model.dao.ProductDAO;
import restApi.model.dao.SaleDAO;
import restApi.model.entity.Product;
import restApi.model.entity.Sale;
import restApi.model.entity.SaleProduct;

import javax.ejb.EJB;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(Arquillian.class)
public class SaleDAOTest extends AbstractDAOTest {

    @EJB
    private ProductDAO productDAO;

    @EJB
    private SaleDAO saleDAO;

    /*              Test products               */
    private static final Product p1 = new Product("Nike", "https://nike.com", 1200, "Very nice shirt");
    private static final Product p2 = new Product("Adidas", "https://adidas.com", 1300, "Very nice hat");

    /*              Test sale               */
    private static final Sale s1 = new Sale("Winter Sale", 0.2);
    private static final Sale s2 = new Sale("Summer Sale", 0.3);



    @Test
    public void getProductsBySaleTest() {
        p1.setSale(s1);
        List<Product> expectedList = new ArrayList<>();
        expectedList.add(p1);
        productDAO.create(p1);
        Assert.assertEquals(expectedList, saleDAO.getProductsFromSale(s1));
    }

    @Test
    public void addNewSaleTest() {
        saleDAO.addNewSale(s1);
        List<Sale> sales = saleDAO.findAll();
        Assert.assertEquals(1, sales.size());
    }

    @Test
    public void addProductToSaleTest() {
        productDAO.create(p1);
        saleDAO.addNewSale(s1);
        saleDAO.addProductToSale(p1, s1);
    }

    @Test
    public void createProductWithSale() {
        Sale sale = new Sale();
        sale.setName("Winter Sale");
        sale.setPercentage(0.2);
        p1.setSale(sale);
        p2.setSale(sale);
        productDAO.createAll(Arrays.asList(p1, p2));
        List<Sale> sales = saleDAO.findAll();
        Assert.assertEquals(1, sales.size());
        Assert.assertEquals("Winter Sale", sales.get(0).getName());
        Assert.assertEquals(0.2, sales.get(0).getPercentage(), 0.001);
        Assert.assertEquals(sale, p1.getSale());
    }

    @Test
    public void setCurrentSaleTest() {
        saleDAO.createAll(Arrays.asList(s1, s2));
        saleDAO.setCurrentSale(s1);
        saleDAO.setCurrentSale(s2);
        Assert.assertFalse(s1.isCurrentSale());
        Assert.assertTrue(s2.isCurrentSale());
    }

    @Test
    public void getCurrentSaleTest() {
        saleDAO.createAll(Arrays.asList(s1, s2));
        saleDAO.setCurrentSale(s1);
        saleDAO.setCurrentSale(s2);
        Assert.assertEquals(s2, saleDAO.getCurrentSale());
        Assert.assertNotEquals(s1, saleDAO.getCurrentSale());
    }
}


