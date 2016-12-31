package br.com.multitela.quiz.servidor.service;

import br.com.multitela.quiz.servidor.entity.JogadorPartidaAssociativa;
import br.com.multitela.quiz.servidor.repository.Repository;

import java.util.List;

/**
 * Created by arthurpereira on 12/6/16.
 */
public interface JogadorPartidaAssociativaService extends Repository<JogadorPartidaAssociativa> {

    public List<JogadorPartidaAssociativa> consultaJogadoresPorPartida(Long partida_id);

    public List<Integer> consultaPontuacoesPorPartida(Long partida_id);

}
