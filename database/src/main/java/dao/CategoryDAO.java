package dao;

import lombok.Getter;
import model.Category;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
}
