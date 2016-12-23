/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.multitela.quiz.servidor.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author arthurpereira
 *
 * @param <T>
 */
public class BaseDAO<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    static EntityManagerFactory emf;

    private ThreadLocal<EntityManager> managerThreadLocal;

    private ThreadLocal<EntityTransaction> transactionThreadLocal;

    /**
     * Construtor padrão. Seta a variavel estatica emf (EntityManagerFactory)
     * caso esteja nula. E caso estejam nulos, seta também o managerThreadLocal
     * e transactionThreadLocal.
     */
    public BaseDAO() {
        if (emf == null) {
            emf = getEntityManagerFactory();
        }

        if (managerThreadLocal == null) {
            managerThreadLocal = new ThreadLocal<>();
        }

        if (transactionThreadLocal == null) {
            transactionThreadLocal = new ThreadLocal<>();
        }
    }

    /**
     * Retorna um novo EntityManagerFactory.
     *
     * @return Persistence.createEntityManagerFactory("almoxarifado");
     */
    private EntityManagerFactory getEntityManagerFactory() {
        if (emf != null) {
            return emf;
        } else {
            emf = Persistence.createEntityManagerFactory("multitela_quiz");
            return emf;
        }
    }

    /**
     * Retorna um EntityManager, caso a ThreadLocal esteja nula, ele cria um
     * novo.
     *
     * @return EntityManager
     */
    public EntityManager getEntityManager() {
        if (managerThreadLocal.get() == null) {
            EntityManager em = emf.createEntityManager();
            managerThreadLocal.set(em);
        }
        return managerThreadLocal.get();
    }

    /**
     * Retorna um EntityTransaction, caso a ThreadLocal esteja nula, ele cria um
     * novo.
     *
     * @return EntityManager
     */
    public EntityTransaction getEntityTransaction() {
        if (transactionThreadLocal.get() == null) {
            EntityTransaction transaction = getEntityManager().getTransaction();
            transactionThreadLocal.set(transaction);
        }
        return transactionThreadLocal.get();
    }

    /**
     * Inicia a operação com o banco. Caso não tenha um entityTransaction ativo,
     * ele tenta iniciar um novo, podendo disparar uma exceção de persistencia.
     * A excecao é por falha na conexão. Então, ele tenta se reconectar buscando
     * um novo EntityManagerFactory, novo EntityManager e novo
     * EntityTransaction.
     */
    public void startOperation() {

        if (!(getEntityTransaction().isActive())) {
            try {
                getEntityTransaction().begin();
                getEntityManager().clear();
            } catch (PersistenceException p) {

                emf = getEntityManagerFactory();
                EntityManager em = emf.createEntityManager();
                managerThreadLocal.set(em);

                EntityTransaction et = managerThreadLocal.get().getTransaction();
                transactionThreadLocal.set(et);

                getEntityTransaction().begin();
                getEntityManager().clear();
            }
        }

    }

    /**
     * Finaliza a operação com o banco. Deve receber como parametro true, caso
     * deva ser efetuado o commit, e false caso deva ser efetuado o rollback.
     *
     * @param commitChanges
     */
    public void stopOperation(boolean commitChanges) {

        if (getEntityTransaction().isActive()) {
            if (commitChanges) {
                //getEntityManager().flush();
                getEntityTransaction().commit();

            } else {
                getEntityTransaction().rollback();
            }

            getEntityManager().close();
            transactionThreadLocal.remove();
            managerThreadLocal.remove();
        } else {
            startOperation();
            if (commitChanges) {
                //getEntityManager().flush();
                getEntityTransaction().commit();
            } else {
                getEntityTransaction().rollback();
            }
            getEntityManager().close();
            transactionThreadLocal.remove();
            managerThreadLocal.remove();
        }

    }

    //
    public void commit() {
        getEntityManager().getTransaction().commit();
    }

    public void rollback() {
        getEntityManager().getTransaction().rollback();
    }

    public void closeTransaction() {
        getEntityManager().close();
    }

    public void commitAndCloseTransaction() {
        commit();
        closeTransaction();
    }

    public void flush() {
        getEntityManager().flush();
    }

    public void save(T entity) {
        getEntityManager().persist(entity);
    }

    public void delete(T entity) {
        T entityToBeRemoved = getEntityManager().merge(entity);

        getEntityManager().remove(entityToBeRemoved);
    }

    public T update(T entity) {
        return getEntityManager().merge(entity);
    }

    public T find(Class<T> classType, Object id) {
        return getEntityManager().find(classType, id);
    }

    public T findReferenceOnly(Class<T> classType, Object id) {
        return getEntityManager().getReference(classType, id);
    }

    // Usando unchecked pois o JPA não tem um
    // em.getCriteriaBuilder().createQuery()<T> method
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<T> findAll(Class<T> classType) {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(classType));
        return getEntityManager().createQuery(cq).getResultList();
    }

}
