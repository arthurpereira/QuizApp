package br.com.multitela.quiz.servidor.enums;

/**
 * Created by arthurpereira on 9/25/17.
 */
public enum QuestionarioAvaliacaoEnum {

    PESSIMA("Péssima"),
    RUIM("Ruim"),
    REGULAR("Regular"),
    BOA("Boa"),
    OTIMA("Ótima");

    private String texto;

    private QuestionarioAvaliacaoEnum(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }
}
