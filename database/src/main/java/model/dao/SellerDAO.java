package model.dao;

import com.querydsl.jpa.impl.JPAInsertClause;
import lombok.Getter;
import model.entity.Customer;
import model.entity.Product;
import model.entity.Seller;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class SellerDAO extends AbstractDAO<Seller>{
    @Getter @PersistenceContext(unitName = "webshop")
    private EntityManager entityManager;

    public SellerDAO() { super(Seller.class); }

    //TODO Do not know why this is needed here but not in the other DAO's
    @Override
    protected EntityManager getEntityManager() {
        return null;
    }
}
