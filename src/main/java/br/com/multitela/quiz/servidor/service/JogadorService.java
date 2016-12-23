package br.com.multitela.quiz.servidor.service;

import br.com.multitela.quiz.servidor.entity.Jogador;
import br.com.multitela.quiz.servidor.repository.Repository;

/**
 * Created by arthurpereira on 12/2/16.
 */
public interface JogadorService extends Repository<Jogador> {

    public Jogador findByFacebookId(Long id);

}
