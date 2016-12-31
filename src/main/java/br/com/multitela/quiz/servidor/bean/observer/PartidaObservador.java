/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.multitela.quiz.servidor.bean.observer;

import br.com.multitela.quiz.servidor.entity.JogadorPartidaAssociativa;
import br.com.multitela.quiz.servidor.entity.Pergunta;
import java.util.List;

/**
 *
 * @author arthurpereira
 */
public interface PartidaObservador {
    
    void atualizarPlacar(List<JogadorPartidaAssociativa> placar, List<Integer> pontuacoes);
    
    void atualizarPerguntas(List<Pergunta> perguntas);
    
}
