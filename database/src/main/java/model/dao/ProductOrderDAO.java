package model.dao;

import lombok.Getter;
import model.entity.ProductOrder;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ProductOrderDAO extends AbstractDAO<ProductOrder>{
    @Getter @PersistenceContext(unitName = "webshop")
    private EntityManager entityManager;

    public ProductOrderDAO() {
        super(ProductOrder.class);
    }

    @Override
    public long count() {
        return super.count();
    }

    @Override
    public void create(ProductOrder entity) {
        super.create(entity);
    }

    @Override
    public List findAll() {
        return super.findAll();
    }

    @Override
    public void remove(ProductOrder entity) {
        super.remove(entity);
    }
}
