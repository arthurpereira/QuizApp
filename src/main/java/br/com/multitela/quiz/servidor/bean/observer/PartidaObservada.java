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
public interface PartidaObservada {

    void addObservador(PartidaObservador o);
    
    void atualizarPlacar();
    
    void notificarPlacar(List<JogadorPartidaAssociativa> placar, List<Integer> pontuacoes);

    void notificarPerguntas(List<Pergunta> perguntas);

    void notificarPerguntaAtual(int indice);
    
}
