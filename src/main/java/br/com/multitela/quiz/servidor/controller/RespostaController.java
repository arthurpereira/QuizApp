/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.multitela.quiz.servidor.controller;

import br.com.multitela.quiz.servidor.dao.RespostaDAO;
import br.com.multitela.quiz.servidor.entity.Resposta;
import java.io.Serializable;

/**
 *
 * @author arthurpereira
 */
public class RespostaController implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private final RespostaDAO respostaDAO = new RespostaDAO();
    
    /**
     * Cadastra uma nova resposta no banco de dados.
     *
     * @param resposta
     */
    public void cadastrar(Resposta resposta) {
        respostaDAO.startOperation();
        respostaDAO.save(resposta);
        respostaDAO.stopOperation(true);
    }
}
