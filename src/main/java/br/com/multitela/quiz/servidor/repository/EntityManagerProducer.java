package br.com.multitela.quiz.servidor.repository;

import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by arthurpereira on 12/1/16.
 */
@Stateless
public class EntityManagerProducer {

    @PersistenceContext(unitName = "multitela_quiz")
    private EntityManager entityManager;

    @Produces
    @RequestScoped
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
