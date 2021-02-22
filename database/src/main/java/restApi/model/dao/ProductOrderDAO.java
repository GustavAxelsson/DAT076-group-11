package restApi.model.dao;

import com.querydsl.jpa.impl.JPAQuery;
import lombok.Getter;
import restApi.model.entity.*;

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
        QProduct product = QProduct.product;
        JPAQuery<Product> query = new JPAQuery<>(entityManager);
        return query.from(product)
                .innerJoin(product.productOrder, productOrder)
                .where(productOrder.id.eq(id))
                .fetch();
    }

    public List<ProductOrder> getProductOrdersByCustomerEmail(String email) {
        QProductOrder productOrder = QProductOrder.productOrder;
        QCustomer customer = QCustomer.customer;
        JPAQuery<Product> query = new JPAQuery<>(entityManager);
        return query.select(productOrder)
                .from(productOrder, customer)
                .where(customer.email.eq(email))
                .fetch();
    }
}
