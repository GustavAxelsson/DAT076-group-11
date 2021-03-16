package model.dao;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import restApi.model.dao.ProductDAO;
import restApi.model.dao.ProductImageDAO;
import restApi.model.entity.Product;
import restApi.model.entity.ProductImage;

import javax.ejb.EJB;
import java.util.List;

@RunWith(Arquillian.class)
public class ProductImageDAOTest extends AbstractDAOTest{
    @EJB
    private ProductDAO productDAO;

    @EJB
    private ProductImageDAO productImageDAO;

    @Test
    public void insertProductImageTest() {
        byte[] array = new byte[0];
        ProductImage productImage = new ProductImage("NEW file", array);
        productImageDAO.addProductImage(productImage);
        List<ProductImage> piList = productImageDAO.findAll();
        Assert.assertEquals(1, piList.size());
    }

    @Test
    public void insertProductAndThenAddProductImageTest() {
        final Product p1 =  new Product("Nike", "https://nike.com", 1200, "Very nice shirt");
        productDAO.create(p1);
        byte[] array = new byte[0];
        ProductImage productImage = new ProductImage("NEW file", array);
        long id = productDAO.findAll().get(0).getId();
        Product product = productDAO.getProductById(id);
        productDAO.updateProductImage(product, productImage);
        long piId = productImageDAO.findAll().get(0).getId();
        Assert.assertEquals(productImageDAO.getProductImageById(piId), product.getProductImage());
    }
}
