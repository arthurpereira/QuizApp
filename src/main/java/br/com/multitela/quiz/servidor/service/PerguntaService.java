package br.com.multitela.quiz.servidor.service;

import br.com.multitela.quiz.servidor.dto.PerguntasPorPartidaDTO;
import br.com.multitela.quiz.servidor.entity.Partida;
import br.com.multitela.quiz.servidor.entity.Pergunta;
import br.com.multitela.quiz.servidor.repository.Repository;

import java.util.List;

/**
 * Created by arthurpereira on 12/2/16.
 */
public interface PerguntaService extends Repository<Pergunta> {

    public List<Pergunta> buscar(String busca);

    public List<PerguntasPorPartidaDTO> consultaPerguntasPorPartida(Partida partida);

}
