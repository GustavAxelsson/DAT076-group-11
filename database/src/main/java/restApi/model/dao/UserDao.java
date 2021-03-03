package restApi.model.dao;

import lombok.Getter;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UserDao {

    @Getter
    @PersistenceContext(unitName = "webshop")
    private EntityManager entityManager;


}
