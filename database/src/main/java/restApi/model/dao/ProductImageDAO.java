package restApi.model.dao;

import com.querydsl.jpa.impl.JPAQuery;
import lombok.Getter;
import restApi.model.entity.Product;
import restApi.model.entity.ProductImage;
import restApi.model.entity.QProductImage;

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
}
