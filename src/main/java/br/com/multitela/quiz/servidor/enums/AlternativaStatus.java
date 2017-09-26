/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.multitela.quiz.servidor.enums;

/**
 *
 * @author arthurpereira
 */
public enum AlternativaStatus {

    NAO_ESCOLHIDA("nao-escolhida"),
    ESCOLHIDA("escolhida"),
    CERTA("certa"),
    ERRADA("errada"),
    DESABILITADA("desabilitada");

    private String cssClass;

    private AlternativaStatus(String cssClass) {
        this.cssClass = cssClass;
    }

    public String getCssClass() {
        return cssClass;
    }

}
