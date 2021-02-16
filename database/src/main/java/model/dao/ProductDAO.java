package model.dao;

import com.querydsl.jpa.impl.JPAQuery;
import lombok.Getter;
import model.entity.Category;
import model.entity.Product;
import model.entity.QProduct;

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
}
