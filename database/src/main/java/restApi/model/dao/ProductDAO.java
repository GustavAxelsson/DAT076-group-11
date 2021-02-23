package restApi.model.dao;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.Getter;
import restApi.model.entity.Category;
import restApi.model.entity.Product;
import restApi.model.entity.QProduct;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ProductDAO extends AbstractDAO<Product> {
    @Getter @PersistenceContext(unitName = "webshop")
    private EntityManager entityManager;

    public ProductDAO() {
        super(Product.class);
    }

    public Product getTestProduct() {
        return new Product("Nike");
    }

    public Product getProductByName(String name) {
        QProduct product = QProduct.product;
        JPAQuery<Product> query = new JPAQuery<>(entityManager);
        return query.select(product)
                .from(product)
                .where(product.name.eq(name))
                .fetchOne();
    }

    public int getStockByProductName(String name) {
        QProduct product = QProduct.product;
        JPAQuery<Product> query = new JPAQuery<>(entityManager);
        return query.select(product.stock)
                .from(product)
                .where(product.name.eq(name))
                .fetchOne();
    }

    public List<Product> getProductsByCategory(Category category) {
        QProduct product = QProduct.product;
        JPAQuery<Product> query = new JPAQuery<>(entityManager);
        return query.select(product)
                .from(product)
                .where(product.category.eq(category))
                .fetch();
    }

    public void updateProductCategory(Product product, Category category) {
        product.setCategory(category);
        entityManager.merge(product);
    }
}
