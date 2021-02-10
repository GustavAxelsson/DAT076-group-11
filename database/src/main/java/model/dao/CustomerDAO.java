package model.dao;

import lombok.Getter;
import model.entity.Customer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CustomerDAO extends AbstractDAO<Customer>{
    @Getter
    @PersistenceContext(unitName = "webshop")
    private EntityManager entityManager;

    public CustomerDAO() {
        super(Customer.class);
    }
}
