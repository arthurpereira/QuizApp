/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.multitela.quiz.servidor.controller;

import br.com.multitela.quiz.servidor.dao.JogadorPartidaAssociativaDAO;
import br.com.multitela.quiz.servidor.entity.JogadorPartidaAssociativa;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author arthurpereira
 */
public class JogadorPartidaAssociativaController implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private final JogadorPartidaAssociativaDAO jogadorPartidaAssociativaDAO = new JogadorPartidaAssociativaDAO();
    
    /**
     * Cadastra um novo objeto da classe JogadorPartidaAssociativa no
     * banco de dados.
     * 
     * @param jogadorPartidaAssociativa
     */
    public void cadastrar(JogadorPartidaAssociativa jogadorPartidaAssociativa) {
        jogadorPartidaAssociativaDAO.startOperation();
        jogadorPartidaAssociativaDAO.save(jogadorPartidaAssociativa);
        jogadorPartidaAssociativaDAO.stopOperation(true);
    }
    
    /**
     * Atualiza/edita um objeto da classe JogadorPartidaAssociativa previamente
     * cadastrado no banco de dados.
     *
     * @param jogadorPartidaAssociativa
     */
    public void atualizar(JogadorPartidaAssociativa jogadorPartidaAssociativa) {
        jogadorPartidaAssociativaDAO.startOperation();
        jogadorPartidaAssociativaDAO.update(jogadorPartidaAssociativa);
        jogadorPartidaAssociativaDAO.stopOperation(true);
    }
    
    /**
     * Realiza a comunicação com o método que faz a busca de uma lista de objetos
     * da classe JogadorPartidaAssociativa pelo id da partida.
     *
     * @param partida_id
     * @return
     */
    public List<JogadorPartidaAssociativa> consultaJogadoresPorPartida(Long partida_id) {
        jogadorPartidaAssociativaDAO.startOperation();
        List<JogadorPartidaAssociativa> resultado = jogadorPartidaAssociativaDAO.consultaJogadoresPorPartida(partida_id);
        jogadorPartidaAssociativaDAO.stopOperation(false);
        return resultado;
    }
}
