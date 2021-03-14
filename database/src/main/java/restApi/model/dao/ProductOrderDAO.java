package restApi.model.dao;

import com.querydsl.jpa.impl.JPAQuery;
import lombok.Getter;
import restApi.model.entity.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    public Set<Product> getProductListByProductOrderId(long id) {
        QProductOrder productOrder = QProductOrder.productOrder;
        JPAQuery<Product> query = new JPAQuery<>(entityManager);
        ProductOrder po = query.select(productOrder)
                .from(productOrder)
                .where(productOrder.id.eq(id))
                .fetchOne();
        return po.getProductList();
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

    public void addItemsToProductOrder(ProductOrder productOrder, List<Product> products) {
        products.forEach(product -> {
            productOrder.getProductList().add(product);
        });
        entityManager.merge(productOrder);
    }

    public void updateProductOrder(ProductOrder productOrder) {
        entityManager.merge(productOrder);
    }

}
