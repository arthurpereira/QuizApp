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

    NAO_ESCOLHIDA("#3360c7"),
    ESCOLHIDA("#16aceb"),
    CERTA("#2eb22e"),
    ERRADA("#d50f0f"),
    DESABILITADA("#acacac");

    private String cor;

    private AlternativaStatus(String cor) {
        this.cor = cor;
    }

    public String getCor() {
        return cor;
    }

}
