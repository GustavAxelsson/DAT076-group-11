package restApi.model.dao;

import com.querydsl.jpa.impl.JPAQuery;
import lombok.Getter;
import restApi.model.entity.Category;
import restApi.model.entity.Product;
import restApi.model.entity.QCategory;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CategoryDAO extends AbstractDAO<Category>{
    @Getter
    @PersistenceContext(unitName = "webshop")
    private EntityManager entityManager;

    public CategoryDAO() {super(Category.class);}

    public void changeNameOfCategory(Category category, String name) {
        category.setName(name);
        entityManager.merge(category);
    }

    public List<Product> getAllProductsForCategory(String name) {
        QCategory category = QCategory.category;
        JPAQuery<Category> query = new JPAQuery<>(entityManager);
        Category cat = query.select(category).from(category).where(category.name.eq(name)).fetchOne();
        if (cat != null && cat.getProductList() != null) {
            return cat.getProductList();
        } else {
            return  null;
        }
    }
}
