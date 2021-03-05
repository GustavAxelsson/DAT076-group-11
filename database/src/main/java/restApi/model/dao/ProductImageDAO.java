package restApi.model.dao;

import lombok.Getter;
import restApi.model.entity.ProductImage;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ProductImageDAO extends AbstractDAO<ProductImage> {
    @Getter
    @PersistenceContext(unitName = "webshop")
    private EntityManager entityManager;

    public  ProductImageDAO() { super(ProductImage.class);}
}
