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

    public void addProductToSale(Product product, Sale sale) {
        product.setSale(sale);
        entityManager.merge(product);
    }

    public void setCurrentSale (Sale currentSale) {
        QSale sale = QSale.sale;
        JPAQuery<Product> query = new JPAQuery<>(entityManager);
        List <Sale> saleList = query.select(sale)
                .from(sale)
                .fetch();
        for(Sale s : saleList) {
            s.setCurrentSale(false);
            entityManager.merge(s);
        }
        currentSale.setCurrentSale(true);
        entityManager.merge(currentSale);
    }

    public Sale getCurrentSale () {
        QSale sale = QSale.sale;
        JPAQuery<Product> query = new JPAQuery<>(entityManager);
        List <Sale> saleList = query.select(sale)
                .from(sale)
                .fetch();
        Sale currSale = new Sale();
        for(Sale s : saleList) {
            if(s.isCurrentSale()) {
                currSale = s;
                break;
            }
        }
    return currSale;
    }

    public Sale getSaleById(long id) {
        QSale sale = QSale.sale;
        JPAQuery<Sale> query = new JPAQuery<>(entityManager);
        return query.select(sale)
                .from(sale)
                .where(sale.id.eq(id))
                .fetchOne();
    }
}
