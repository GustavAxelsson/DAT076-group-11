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

@RunWith(Arquillian.class)
public class ProductImageDAOTest extends AbstractDAOTest{
    @EJB
    private ProductDAO productDAO;

    @EJB
    private ProductImageDAO productImageDAO;

    @Test
    public void insertProductImage() {
        byte[] array = new byte[0];
        ProductImage productImage = new ProductImage("NEW file", array);
        productImageDAO.addProductImage(productImage);
        System.out.println(productImageDAO.findAll());
    }

    @Test
    public void insertProductAndThenAddProductImage() {
        final Product p1 =  new Product("Nike", "https://nike.com", 1200, "Very nice shirt");
        productDAO.create(p1);
        byte[] array = new byte[0];
        ProductImage productImage = new ProductImage("NEW file", array);
        productImageDAO.updateProductImage(p1, productImage);
        Assert.assertEquals(1,productImageDAO.findAll().size());
    }

    @Test
    public void getProductImageFromProductId() {
        final Product p1 =  new Product("Nike", "https://nike.com", 1200, "Very nice shirt");
        productDAO.create(p1);
        byte[] array = new byte[0];
        ProductImage productImage = new ProductImage("NEW file", array);
        productImageDAO.updateProductImage(p1, productImage);

        Product product = productDAO.getProductByName("Nike");

        ProductImage productImage1 = productImageDAO.getProductImageByProductId(product.getId());

        Assert.assertNotNull(productImage1);
        Assert.assertEquals(product, productImage1.getProduct());
    }
}
