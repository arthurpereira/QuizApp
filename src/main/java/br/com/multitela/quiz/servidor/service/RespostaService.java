package br.com.multitela.quiz.servidor.service;

import br.com.multitela.quiz.servidor.dto.RespostasPorAlternativaDTO;
import br.com.multitela.quiz.servidor.dto.RespostasPorJogadorDTO;
import br.com.multitela.quiz.servidor.entity.JogadorPartidaAssociativa;
import br.com.multitela.quiz.servidor.entity.Partida;
import br.com.multitela.quiz.servidor.entity.Pergunta;
import br.com.multitela.quiz.servidor.entity.Resposta;
import br.com.multitela.quiz.servidor.repository.Repository;

import java.util.List;

/**
 * Created by arthurpereira on 12/6/16.
 */
public interface RespostaService extends Repository<Resposta> {

    List<RespostasPorAlternativaDTO> countRespostasPorPergunta(Partida partida, Pergunta pergunta);

    List<String> consultaRespostasPorJogadorPartida(JogadorPartidaAssociativa jogadorPartida, Partida partida);

    List<RespostasPorJogadorDTO> consultaRespostasPorJogadorPorPartida(Partida partida);

}
