package br.com.multitela.quiz.servidor.enums;

/**
 * Created by arthurpereira on 9/25/17.
 */
public enum QuestionarioSatisfacaoEnum {

    PESSIMO("Péssimo"),
    RUIM("Ruim"),
    REGULAR("Regular"),
    BOM("Bom"),
    OTIMO("Ótimo");

    private String texto;

    private QuestionarioSatisfacaoEnum(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

}
