package br.com.multitela.quiz.servidor.service;

import br.com.multitela.quiz.servidor.entity.JogadorPartidaAssociativa;
import br.com.multitela.quiz.servidor.entity.Partida;
import br.com.multitela.quiz.servidor.repository.Repository;

import java.util.List;

/**
 * Created by arthurpereira on 12/6/16.
 */
public interface JogadorPartidaAssociativaService extends Repository<JogadorPartidaAssociativa> {

    public List<JogadorPartidaAssociativa> consultaTop10JogadoresPorPartida(Partida partida);

    public List<JogadorPartidaAssociativa> consultaTodosJogadoresPorPartida(Partida partida);

    public List<Integer> consultaPontuacoesPorPartida(Partida partida);

}
