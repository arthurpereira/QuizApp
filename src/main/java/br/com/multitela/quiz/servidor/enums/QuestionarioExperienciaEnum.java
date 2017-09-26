package br.com.multitela.quiz.servidor.enums;

/**
 * Created by arthurpereira on 9/25/17.
 */
public enum QuestionarioExperienciaEnum {

    MUITO_DIFICIL("Muito difícil"),
    DIFICIL("Difícil"),
    REGULAR("Regular"),
    FACIL("Fácil"),
    MUITO_FACIL("Muito fácil");

    private String texto;

    private QuestionarioExperienciaEnum(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }
}
