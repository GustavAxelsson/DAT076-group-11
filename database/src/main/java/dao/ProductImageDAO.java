package dao;

import com.querydsl.jpa.impl.JPAQuery;
import lombok.Getter;
import model.Product;
import model.ProductImage;
import model.QProductImage;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ProductImageDAO extends AbstractDAO<ProductImage> {
    @Getter
    @PersistenceContext(unitName = "webshop")
    private EntityManager entityManager;

    public ProductImageDAO() { super(ProductImage.class);}

    public ProductImage getProductImageById(long id) {
        QProductImage productImage = QProductImage.productImage;
        JPAQuery<ProductImage> query = new JPAQuery<>(entityManager);
        return query.select(productImage).from(productImage)
                .from(productImage)
                .where(productImage.id.eq(id))
                .fetchOne();
    }

    public void addProductImage(ProductImage productImage) {
        entityManager.merge(productImage);
    }

    public void updateProductImage(Product product, ProductImage productImage) {
        productImage.setProduct(product);
        entityManager.merge(productImage);
    }

    public ProductImage getProductImageByProductId(long productId) {
        QProductImage productImage = QProductImage.productImage;
        JPAQuery<ProductImage> query = new JPAQuery<>(entityManager);
        return query.select(productImage).from(productImage)
                .from(productImage)
                .where(productImage.product.id.eq(productId)).fetchOne();
    }
}
