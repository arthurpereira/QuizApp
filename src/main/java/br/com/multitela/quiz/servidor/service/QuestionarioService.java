package br.com.multitela.quiz.servidor.service;

import br.com.multitela.quiz.servidor.entity.Questionario;
import br.com.multitela.quiz.servidor.repository.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by arthurpereira on 1/13/17.
 */
public interface QuestionarioService extends Repository<Questionario> {

    List<Questionario> consultaPorData(Date data1, Date data2);

}
