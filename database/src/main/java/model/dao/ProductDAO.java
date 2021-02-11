package model.dao;

import lombok.Getter;
import model.entity.Product;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ProductDAO extends AbstractDAO<Product> {
    @Getter @PersistenceContext(unitName = "webshop")
    private EntityManager entityManager;

    public ProductDAO() {
        super(Product.class);
    }
}
