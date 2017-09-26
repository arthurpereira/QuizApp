package br.com.multitela.quiz.servidor.enums;

/**
 * Created by arthurpereira on 1/13/17.
 */
public enum LoginEnum {

    FACEBOOK("Facebook"),
    SEM_FACEBOOK("Sem Facebook");

    private String descricao;

    private LoginEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
