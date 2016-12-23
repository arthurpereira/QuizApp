package br.com.multitela.quiz.servidor.service;

import br.com.multitela.quiz.servidor.entity.Partida;
import br.com.multitela.quiz.servidor.repository.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by arthurpereira on 12/6/16.
 */
public interface PartidaService extends Repository<Partida> {

    public List<Partida> consultaPorData(Date data1, Date data2);

    public Partida consultaUltimaPartida();

}
