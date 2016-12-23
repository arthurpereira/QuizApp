/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.multitela.quiz.servidor.controller;

import br.com.multitela.quiz.servidor.dao.PartidaDAO;
import br.com.multitela.quiz.servidor.entity.Partida;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author arthurpereira
 */
public class PartidaController implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private final PartidaDAO partidaDAO = new PartidaDAO();
    
    /**
     * Cadastra uma nova partida no banco de dados.
     *
     * @param partida
     */
    public void cadastrar(Partida partida) {
        partidaDAO.startOperation();
        partidaDAO.save(partida);
        partidaDAO.stopOperation(true);
    }
    
    /**
     * Realiza a comunicação com o método que faz a busca de partida em um
     * intervalo de datas.
     *
     * @param data1
     * @param data2
     * @return
     */
    public List<Partida> buscaPorData(Date data1, Date data2) {
        partidaDAO.startOperation();
        List<Partida> resultado = partidaDAO
                .consultaPorData(data1, data2);
        partidaDAO.stopOperation(false);
        return resultado;
    }
    
    public Partida consultaUltimaPartida() {
        partidaDAO.startOperation();
        Partida resultado = partidaDAO
                .consultaUltimaPartida();
        partidaDAO.stopOperation(false);
        return resultado;
    }
    
}
