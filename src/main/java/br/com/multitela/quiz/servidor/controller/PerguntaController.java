/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.multitela.quiz.servidor.controller;

import br.com.multitela.quiz.servidor.dao.PerguntaDAO;
import br.com.multitela.quiz.servidor.entity.Pergunta;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author arthurpereira
 */
public class PerguntaController implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private final PerguntaDAO perguntaDAO = new PerguntaDAO();
    
    /**
     * Cadastra uma nova pergunta no banco de dados.
     *
     * @param pergunta
     */
    public void cadastrar(Pergunta pergunta) {
        perguntaDAO.startOperation();
        perguntaDAO.save(pergunta);
        perguntaDAO.stopOperation(true);
    }
    
    /**
     * Atualiza/edita uma pergunta previamente cadastrada no banco de dados.
     *
     * @param pergunta
     */
    public void atualizar(Pergunta pergunta) {
        perguntaDAO.startOperation();
        perguntaDAO.update(pergunta);
        perguntaDAO.stopOperation(true);
    }
    
    /**
     * Realiza a comunicação com o método que faz a busca de uma pergunta pelo 
     * seu nome.
     *
     * @param busca
     * @return
     */
    public List<Pergunta> busca(String busca) {
        perguntaDAO.startOperation();
        List<Pergunta> resultado = perguntaDAO
                .consulta(busca);
        perguntaDAO.stopOperation(false);
        return resultado;
    }
    
    public List<Pergunta> findAll() {
        perguntaDAO.startOperation();
        List<Pergunta> resultado = perguntaDAO.findAll(Pergunta.class);
        perguntaDAO.stopOperation(false);
        return resultado;
    }
    
}
