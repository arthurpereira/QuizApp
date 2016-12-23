/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.multitela.quiz.servidor.controller;

import br.com.multitela.quiz.servidor.dao.JogadorDAO;
import br.com.multitela.quiz.servidor.entity.Jogador;
import java.io.Serializable;

/**
 *
 * @author arthurpereira
 */
public class JogadorController implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private final JogadorDAO jogadorDAO = new JogadorDAO();
    
    /**
     * Cadastra um novo jogador no banco de dados.
     *
     * @param jogador
     */
    public void cadastrar(Jogador jogador) {
        jogadorDAO.startOperation();
        jogadorDAO.save(jogador);
        jogadorDAO.stopOperation(true);
    }
    
    /**
     * Realiza a comunicação com o método que faz a busca de jogador pelo
     * id do Facebook.
     *
     * @param id
     * @return
     */
    public Jogador find(Long id) {
        jogadorDAO.startOperation();
        Jogador resultado = jogadorDAO
                .find(id);
        jogadorDAO.stopOperation(false);
        return resultado;
    }
    
}
