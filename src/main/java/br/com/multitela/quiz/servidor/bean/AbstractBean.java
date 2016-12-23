/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.multitela.quiz.servidor.bean;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author arthurpereira
 */
public class AbstractBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String KEEP_DIALOG_OPENED = "KEEP_DIALOG_OPENED";

    public AbstractBean() {
        super();
    }

    /**
     * Método para exibição de mensagem de sucesso caso não tenham acontecidos
     * erros no cadastro/edição/busca.
     *
     * @param mensagem
     */
    public void mensagemSucesso(String mensagem) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage("sucesso", new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", mensagem));
    }

    /**
     * Método para exibição de mensagem de atenção caso tenha acontecido algum
     * erro no cadastro/edição/busca.
     *
     * @param mensagem
     */
    public void mensagemAtencao(String mensagem) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage("atencao", new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção!", mensagem));
    }

    /**
     * Método para exibição de mensagem indicando que a lista no formulário de
     * busca está vazia, ou seja, não houveram resultados.
     *
     * @param mensagem
     */
    public void mensagemVazia(String mensagem) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage("vazia", new FacesMessage(FacesMessage.SEVERITY_INFO, "Lista Vazia!", mensagem));
    }

    /**
     * Método para exibição de mensagem de erro caso tenha acontecido algum erro
     * no cadastro/edição/busca.
     *
     * @param mensagem
     */
    public void mensagemErro(String mensagem) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage("erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", mensagem));
    }

    protected void closeDialog() {
        getRequestContext().addCallbackParam(KEEP_DIALOG_OPENED, false);
    }

    protected void keepDialogOpen() {
        getRequestContext().addCallbackParam(KEEP_DIALOG_OPENED, true);
    }

    protected RequestContext getRequestContext() {
        return RequestContext.getCurrentInstance();
    }
}