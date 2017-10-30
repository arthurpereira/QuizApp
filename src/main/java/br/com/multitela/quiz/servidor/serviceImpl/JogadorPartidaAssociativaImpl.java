package br.com.multitela.quiz.servidor.serviceImpl;

import br.com.multitela.quiz.servidor.entity.JogadorPartidaAssociativa;
import br.com.multitela.quiz.servidor.entity.Partida;
import br.com.multitela.quiz.servidor.repository.RepositoryImpl;
import br.com.multitela.quiz.servidor.service.JogadorPartidaAssociativaService;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by arthurpereira on 12/6/16.
 */
@Stateless
public class JogadorPartidaAssociativaImpl extends RepositoryImpl<JogadorPartidaAssociativa> implements JogadorPartidaAssociativaService {

    @Override
    public List<JogadorPartidaAssociativa> consultaTop10JogadoresPorPartida(Partida partida) {
        TypedQuery query = getEntityManager().createQuery(
                "SELECT jogador_partida FROM " + JogadorPartidaAssociativa.class.getName()
                + " AS jogador_partida WHERE jogador_partida.partida.id = :partida_id"
                + " ORDER BY jogador_partida.acertos DESC, jogador_partida.id", JogadorPartidaAssociativa.class);
        query.setParameter("partida_id", partida.getId());
        query.setMaxResults(10);

        return query.getResultList();
    }

    @Override
    public List<JogadorPartidaAssociativa> consultaTodosJogadoresPorPartida(Partida partida) {
        TypedQuery query = getEntityManager().createQuery(
                "SELECT jogador_partida FROM " + JogadorPartidaAssociativa.class.getName()
                + " AS jogador_partida WHERE jogador_partida.partida.id = :partida_id"
                + " ORDER BY jogador_partida.acertos DESC", JogadorPartidaAssociativa.class);
        query.setParameter("partida_id", partida.getId());

        return query.getResultList();
    }

    @Override
    public List<Integer> consultaPontuacoesPorPartida(Partida partida) {
        Query query = getEntityManager().createQuery(
                "SELECT DISTINCT jogador_partida.acertos FROM " + JogadorPartidaAssociativa.class.getName()
                + " AS jogador_partida WHERE jogador_partida.partida.id = :partida_id"
                + " ORDER BY jogador_partida.acertos DESC");
        query.setParameter("partida_id", partida.getId());

        return query.getResultList();
    }
}
