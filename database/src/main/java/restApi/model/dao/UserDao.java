package restApi.model.dao;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.Getter;
import org.mindrot.jbcrypt.BCrypt;
import restApi.model.entity.QWebshopUser;
import restApi.model.entity.WebshopUser;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UserDao extends AbstractDAO<WebshopUser>{

    @Getter
    @PersistenceContext(unitName = "webshop")
    private EntityManager entityManager;

    public UserDao() {super(WebshopUser.class);}

    public void registerUser(String username, String password) {
        String salt = BCrypt.gensalt(10);
        String hashedPw = BCrypt.hashpw(password, salt);
        WebshopUser user = new WebshopUser();
        user.setUsername(username);
        user.setPassword(hashedPw);
        user.setRole("user");
        entityManager.persist(user);
    }

    public boolean userAlreadyExist(String username) {
        QWebshopUser user = QWebshopUser.webshopUser;
        JPAQuery<WebshopUser> query = new JPAQuery<>(entityManager);
        return query.select(user).from(user).where(user.username.eq(username)) != null;
    }

    public String validate(WebshopUser user, String password) {
        if (user != null) {
            if(BCrypt.checkpw(password, user.getPassword())) {
               return user.getRole();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public WebshopUser getUserFromUsername(String username) {
        QWebshopUser user = QWebshopUser.webshopUser;
        JPAQuery<WebshopUser> query = new JPAQuery<>(entityManager);
        return query.select(user).from(user).where(user.username.eq(username))
                .fetchOne();
    }

    public void updateRoleOnUser(WebshopUser user, String role) {
        user.setRole(role);
        entityManager.merge(user);
    }


}
