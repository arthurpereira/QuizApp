package br.com.multitela.quiz.servidor.dto;

import br.com.multitela.quiz.servidor.entity.Jogador;
import br.com.multitela.quiz.servidor.entity.Resposta;

import java.util.List;

/**
 * Created by arthurpereira on 03/10/17.
 */
public class RespostasPorJogadorDTO {

    private Jogador jogador;
    private List<String> listaRespostas;

    // GETTERS AND SETTERS

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    public List<String> getListaRespostas() {
        return listaRespostas;
    }

    public void setListaRespostas(List<String> listaRespostas) {
        this.listaRespostas = listaRespostas;
    }
}
