package model.dao;

import lombok.Getter;
import model.entity.Product;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
}
