package restApi.model.dao;

import com.querydsl.jpa.impl.JPAQuery;
import lombok.Getter;
import restApi.model.entity.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class SaleDAO extends AbstractDAO<Sale> {
    @Getter
    @PersistenceContext(unitName = "webshop")
    private EntityManager entityManager;

    public SaleDAO() { super(Sale.class); }

    public List<Product> getProductsFromSale(Sale sale) {
        QProduct product = QProduct.product;
        JPAQuery<Product> query = new JPAQuery<>(entityManager);
        return query.select(product)
                .from(product)
                .where(product.sale.eq(sale))
                .fetch();
    }

    public void addNewSale(Sale sale) {
        entityManager.persist(sale);
    }
}
