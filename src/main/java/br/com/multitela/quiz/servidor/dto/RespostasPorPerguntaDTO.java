package br.com.multitela.quiz.servidor.dto;

import br.com.multitela.quiz.servidor.enums.AlternativaStatus;

import java.io.Serializable;

/**
 * Created by arthurpereira on 23/10/17.
 */
public class RespostasPorPerguntaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Character alternativa;
    private AlternativaStatus status;

    // GETTERS AND SETTERS

    public Character getAlternativa() {
        return alternativa;
    }

    public void setAlternativa(Character alternativa) {
        this.alternativa = alternativa;
    }

    public AlternativaStatus getStatus() {
        return status;
    }

    public void setStatus(AlternativaStatus status) {
        this.status = status;
    }
}
