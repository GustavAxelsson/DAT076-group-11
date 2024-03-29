package dao;

import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractDAO<T> {
    private final Class<T> entityType;
    protected abstract EntityManager getEntityManager();

    public long count() {
        final CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery cq = builder.createQuery();
        final Root<T> rt = cq.from(entityType);
        cq.select(builder.count(rt));
        final Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult());
    }
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void createAll(List<T> entities) {
        entities.forEach(entity -> {
            getEntityManager().persist(entity);
        });
        getEntityManager().flush();
    }

    public List<T> findAll() {
        final CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityType));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }
}
