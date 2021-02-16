package model.dao;

import com.querydsl.jpa.impl.JPAQuery;
import lombok.Getter;
import model.entity.*;

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
}
