package br.com.multitela.quiz.servidor.dto;

import java.io.Serializable;

/**
 * Created by arthurpereira on 04/10/17.
 */
public class PerguntasPorPartidaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String pergunta;
    private Integer indice;

    public PerguntasPorPartidaDTO(String pergunta, Integer indice) {
        this.pergunta = pergunta;
        this.indice = indice;
    }

    // GETTERS AND SETTERS

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public Integer getIndice() {
        return indice;
    }

    public void setIndice(Integer indice) {
        this.indice = indice;
    }

}
