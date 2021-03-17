package dao;

import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.Getter;
import model.Customer;
import model.QCustomer;

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


    public Customer getCustomerNamed(String name) {
        QCustomer customer = QCustomer.customer;
        JPAQuery<Customer> query = new JPAQuery<>(entityManager);
        return query.select(customer)
                .from(customer)
                .where(customer.firstName.eq(name))
                .fetchOne();
    }

    public Customer getCustomerByEmail(String email) {
        QCustomer customer = QCustomer.customer;
        JPAQuery<Customer> query = new JPAQuery<>(entityManager);
        return query.select(customer)
                .from(customer)
                .where(customer.email.eq(email))
                .fetchOne();
    }

    public void removeCustomerByEmail(String email) {
        QCustomer customer = QCustomer.customer;
        new JPADeleteClause(entityManager, customer)
                .where(customer.email.eq(email)).execute();
    }
}
