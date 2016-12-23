/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.multitela.quiz.servidor.dao;

import br.com.multitela.quiz.servidor.entity.JogadorPartidaAssociativa;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author arthurpereira
 */
public class JogadorPartidaAssociativaDAO extends BaseDAO<JogadorPartidaAssociativa> {
    
    @SuppressWarnings("unchecked")
    public List<JogadorPartidaAssociativa> consultaJogadoresPorPartida(Long partida_id) {
        Query query = getEntityManager()
                .createQuery(
                        "SELECT jogador_partida FROM "
                        + JogadorPartidaAssociativa.class.getName()
                        + " AS jogador_partida WHERE jogador_partida.partida.id = :partida_id"
                        + " ORDER BY jogador_partida.pontuacao DESC");
        query.setParameter("partida_id", partida_id);
        query.setMaxResults(10);
        return query.getResultList();
    }
}
