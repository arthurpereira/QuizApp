package br.com.multitela.quiz.servidor.repository;

import org.hibernate.Session;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by arthurpereira on 12/1/16.
 */
public class RepositoryImpl<T> implements Repository<T> {

    @Inject
    EntityManager entityManager;

    @Override
    public void save(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public T update(T entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void delete(T entity) {
        entityManager.remove(this.update(entity));
    }

    @Override
    public T find(Object id) throws EntityNotFoundException {
        if (id == null)
            throw new EntityNotFoundException("ID field is required.");

        return entityManager.find(this.getGenericClass(), id);
    }

    @SuppressWarnings("unchecked")
    protected Class<T> getGenericClass() {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;

            return (Class<T>) parameterizedType.getActualTypeArguments()[0];
        } else {
            return null;
        }
    }

    @Override
    public List<T> list(Class<T> tipo, String campo) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(tipo);
        Root<T> root = query.from(tipo);
        query.select(root);
        query.orderBy(criteriaBuilder.asc(root.get(campo)));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<T> list(String sql) {
        return this.createQuery(sql).getResultList();
    }

    @Override
    public List<T> list(String sql, Object... valores) {
        return this.createQuery(sql, valores).getResultList();
    }

    @Override
    public List<T> listAll() {
        TypedQuery<T> query = entityManager.createQuery("SELECT t FROM " + getGenericClass().getSimpleName() + " t",
                getGenericClass());
        return query.getResultList();
    }

    @Override
    public TypedQuery<T> createQuery(String sql) {
        return entityManager.createNamedQuery(sql, getGenericClass());
    }

    @Override
    public TypedQuery<T> createQuery(String sql, Object... valores) {
        TypedQuery<T> query = this.createQuery(sql);
        for (int i = 1; i <= valores.length; i++) {
            query.setParameter(i, valores[i - 1]);
        }
        return query;
    }

    @Override
    public TypedQuery<T> createQuery(String sql, Map<String, Object> params) {
        TypedQuery<T> query = this.createQuery(sql);
        for (Map.Entry<String, Object> param : params.entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }
        return query;
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public Session getHibernateSession() {
        return (Session) entityManager.getDelegate();
    }
}
