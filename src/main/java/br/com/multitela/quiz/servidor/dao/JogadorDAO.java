/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.multitela.quiz.servidor.dao;

import br.com.multitela.quiz.servidor.entity.Jogador;
import javax.persistence.Query;

/**
 *
 * @author arthurpereira
 */
public class JogadorDAO extends BaseDAO<Jogador> {
    
    /**
     * Busca um jogador do banco de dados onde o id recebido como
     * parâmetro é igual ao id do Facebook da tabela jogador.
     *
     * @param id
     * @return
     */
    public Jogador find(Long id) {
        Query query = getEntityManager().createQuery(
                "SELECT jogador FROM "
                + Jogador.class.getName()
                + " jogador WHERE jogador.facebook_id = :id");
        query.setParameter("id", id);

        return (Jogador) query.getSingleResult();
    }
    
}
