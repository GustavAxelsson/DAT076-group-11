package model.dao;

import lombok.Getter;
import model.entity.Category;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CategoryDAO extends AbstractDAO<Category>{
    @Getter
    @PersistenceContext(unitName = "webshop")
    private EntityManager entityManager;

    public CategoryDAO() {super(Category.class);}
}
