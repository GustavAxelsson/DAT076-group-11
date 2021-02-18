package model.dao;

import com.querydsl.jpa.impl.JPAQuery;
import lombok.Getter;
import model.entity.Customer;
import model.entity.Product;
import model.entity.ProductOrder;
import model.entity.QProductOrder;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ProductOrderDAO extends AbstractDAO<ProductOrder> {
    @Getter
    @PersistenceContext(unitName = "webshop")
    private EntityManager entityManager;

    public ProductOrderDAO() {
        super(ProductOrder.class);
    }

    public Customer getCustomerByProductOrderId(long id) {
        QProductOrder productOrder = QProductOrder.productOrder;
        JPAQuery<Product> query = new JPAQuery<>(entityManager);
        return query.select(productOrder.customer)
                .from(productOrder)
                .where(productOrder.id.eq(id))
                .fetchOne();
    }

    public List<Product> getProductListByProductOrderId(long id) {
        QProductOrder productOrder = QProductOrder.productOrder;
        JPAQuery<Product> query = new JPAQuery<>(entityManager);
        return query.select(productOrder.productList)
                .from(productOrder)
                .where(productOrder.id.eq(id))
                .fetchOne();
    }
}
