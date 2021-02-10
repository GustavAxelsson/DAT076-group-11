package model.dao;

import lombok.Getter;
import model.entity.ProductOrder;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ProductOrderDAO extends AbstractDAO<ProductOrder>{
    @Getter @PersistenceContext(unitName = "webshop")
    private EntityManager entityManager;

    public ProductOrderDAO() {
        super(ProductOrder.class);
    }
}
