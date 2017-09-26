package br.com.multitela.quiz.servidor.enums;

/**
 * Created by arthurpereira on 9/25/17.
 */
public enum QuestionarioLikertEnum {

    DISCORDO_TOTALMENTE("Discordo totalmente"),
    DISCORDO("Discordo"),
    NAO_CONCORDO_NEM_DISCORDO("Não concordo nem discordo"),
    CONCORDO("Concordo"),
    CONCORDO_TOTALMENTE("Concordo totalmente");

    private String texto;

    private QuestionarioLikertEnum(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }
}
