/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.multitela.quiz.servidor.persistence;

import br.com.multitela.quiz.servidor.controller.JogadorPartidaAssociativaController;
import br.com.multitela.quiz.servidor.entity.JogadorPartidaAssociativa;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;

/**
 *
 * @author arthurpereira
 */
public class TesteJogadorPartidaDAO {
    
    public static void main(String args[]) {
        
        List<JogadorPartidaAssociativa> jogadoresPorPartida = new ArrayList<>();
        JogadorPartidaAssociativaController jogadorPartidaController = new JogadorPartidaAssociativaController();
        
        try {
            jogadoresPorPartida = jogadorPartidaController.consultaJogadoresPorPartida(Long.valueOf(2));
            System.out.println(jogadoresPorPartida.get(0).getPartida().getData());
            System.out.println("A consulta foi realizada com sucesso.");
        } catch (NoResultException ex) {
            System.out.println("A consulta foi realizada mas não encontrou resultados.");
        }
    }
}
