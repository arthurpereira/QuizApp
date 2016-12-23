/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.multitela.quiz.servidor.dao;

import br.com.multitela.quiz.servidor.entity.Pergunta;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author arthurpereira
 */
public class PerguntaDAO extends BaseDAO<Pergunta> {
    
    /**
     * Busca pergunta do banco de dados retornando uma lista para as tabelas de
     * busca.
     *
     * @param busca
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Pergunta> consulta(String busca) {
        Query query = getEntityManager().createQuery(
                "SELECT pergunta FROM "
                + Pergunta.class.getName()
                + " pergunta WHERE pergunta.texto LIKE :busca");
        query.setParameter("busca", "%" + busca + "%");
        
        return query.getResultList();
    }
}
