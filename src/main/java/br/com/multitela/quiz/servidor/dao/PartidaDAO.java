/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.multitela.quiz.servidor.dao;

import br.com.multitela.quiz.servidor.entity.Partida;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author arthurpereira
 */
public class PartidaDAO extends BaseDAO<Partida> {
    
    /**
     * Busca partida do banco de dados retornando uma lista para as tabelas de
     * busca.
     *
     * @param data1
     * @param data2
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Partida> consultaPorData(Date data1, Date data2) {
        Session session = getEntityManager().unwrap(Session.class);
        Criteria criteria = session.createCriteria(Partida.class);

        if (data1 == null && data2 == null) {
            return new ArrayList<>();
        } else if (data1 != null && data2 != null) {
            if (data1.before(data2)) {
                criteria.add(Restrictions.ge("data", data1)); 
                criteria.add(Restrictions.le("data", data2));
            } else {
                criteria.add(Restrictions.ge("data", data2)); 
                criteria.add(Restrictions.le("data", data1));
            }
        } else {
            if (data1 != null)
                criteria.add(Restrictions.eq("data", data1));
            if (data2 != null)
                criteria.add(Restrictions.eq("data", data2));
        }
        return criteria.list();
    }
    
    @SuppressWarnings("unchecked")
    public Partida consultaUltimaPartida() {
        Query query = getEntityManager()
                .createQuery(
                        "SELECT partida FROM "
                        + Partida.class.getName()
                        + " AS partida ORDER BY partida.id DESC");
        query.setMaxResults(1);
        return (Partida) query.getSingleResult();
    }
}
