package br.com.multitela.quiz.servidor.dto;

/**
 * Created by arthurpereira on 23/06/17.
 */
public class RespostasPorAlternativaDTO {

    private Long quantRespostas;

    public RespostasPorAlternativaDTO(Long quantRespostas) {
        this.quantRespostas = quantRespostas;
    }

    // GETTERS AND SETTERS

    public Long getQuantRespostas() {
        return quantRespostas;
    }

    public void setQuantRespostas(Long quantRespostas) {
        this.quantRespostas = quantRespostas;
    }

}
