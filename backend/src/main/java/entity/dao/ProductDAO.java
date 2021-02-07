package entity.dao;

import entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Getter;
import javax.ejb.Stateless;

import java.util.List;

@Stateless
public class ProductDAO extends AbstractDAO<Product> {
    @Getter @PersistenceContext(unitName = "default")
    private EntityManager entityManager;

    public ProductDAO() {
        super(Product.class);
    }

    public List<Product> findProductsName() {
        throw new UnsupportedOperationException("Not imp yet");
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
