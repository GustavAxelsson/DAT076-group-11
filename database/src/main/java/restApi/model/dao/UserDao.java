package restApi.model.dao;

import lombok.Getter;
import restApi.model.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UserDao extends AbstractDAO<User>{

    @Getter
    @PersistenceContext(unitName = "webshop")
    private EntityManager entityManager;

    public UserDao() {super(User.class);}


}
