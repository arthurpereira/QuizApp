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
    
    public void atualizarPlacar();
    
    public void notificarPlacar(List<JogadorPartidaAssociativa> placar);
    
    public void listarPerguntas();
    
    public void notificarPerguntas(List<Pergunta> perguntas);
    
    public void addObservador(PartidaObservador o);
    
}
