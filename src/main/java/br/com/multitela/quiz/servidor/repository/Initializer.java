package br.com.multitela.quiz.servidor.repository;

import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;

/**
 * Created by arthurpereira on 12/1/16.
 */
@Singleton
@Startup
public class Initializer {

    @Inject
    EntityManager em;

    @PostConstruct
    private void init() {

    }
}
