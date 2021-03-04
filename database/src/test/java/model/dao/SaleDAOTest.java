package model.dao;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import restApi.model.dao.ProductDAO;
import restApi.model.dao.SaleDAO;
import restApi.model.entity.Product;
import restApi.model.entity.Sale;

import javax.ejb.EJB;
import java.util.ArrayList;
import java.util.List;

@RunWith(Arquillian.class)
public class SaleDAOTest extends AbstractDAOTest {

    @EJB
    private ProductDAO productDAO;

    @EJB
    private SaleDAO saleDAO;

    /*              Test products               */
    private static final Product p1 = new Product("Nike", "https://nike.com", 1200, "Very nice shirt");

    /*              Test sale               */
    private static final Sale s1 = new Sale("Winter Sale", 0.2);



    @Test
    public void getProductsBySaleTest() {
        p1.setSale(s1);
        List<Product> expectedList = new ArrayList<>();
        expectedList.add(p1);
        productDAO.create(p1);
        Assert.assertEquals(expectedList, saleDAO.getProductsFromSale(s1));
    }

    @Test
    public void createProductWithSale() {
        Product product = new Product();
        product.setName("Nike");
        Sale sale = new Sale();
        sale.setName("Winter Sale");
        sale.setPercentage(0.2);
        product.setSale(sale);
        productDAO.create(product);
        List<Sale> sales = saleDAO.findAll();
        Assert.assertEquals(1, sales.size());
        Assert.assertEquals("Winter Sale", sales.get(0).getName());
        Assert.assertEquals(0.2, sales.get(0).getPercentage(), 0.001);
    }
}


