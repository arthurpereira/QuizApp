package br.com.multitela.quiz.servidor.serviceImpl;

import br.com.multitela.quiz.servidor.entity.Resposta;
import br.com.multitela.quiz.servidor.repository.RepositoryImpl;
import br.com.multitela.quiz.servidor.service.RespostaService;

import javax.ejb.Stateless;

/**
 * Created by arthurpereira on 12/6/16.
 */
@Stateless
public class RespostaImpl extends RepositoryImpl<Resposta> implements RespostaService {
}
