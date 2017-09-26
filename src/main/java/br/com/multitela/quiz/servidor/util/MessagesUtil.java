package br.com.multitela.quiz.servidor.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Created by arthurpereira on 06/04/17.
 */
public class MessagesUtil {

    private static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    /**
     * Método para exibição de mensagem de sucesso ao realizar cadastro/edição/busca.
     *
     * @param mensagem
     */
    public static void mostrarMensagemSucesso(String mensagem) {
        setMessageInfo(mensagem);
    }

    /**
     * Método para exibição de mensagem de sucesso ao realizar cadastro/edição/busca.
     *
     * @param titulo
     * @param mensagem
     */
    public static void mostrarMensagemSucesso(String titulo, String mensagem) {
        setMessageInfo(titulo, mensagem);
    }

    /**
     * Método para exibição de mensagem de atenção ao realizar cadastro/edição/busca.
     *
     * @param mensagem
     */
    public static void mostrarMensagemAtencao(String mensagem) {
        setMessageWarn(mensagem);
    }

    /**
     * Método para exibição de mensagem de atenção ao realizar cadastro/edição/busca.
     *
     * @param titulo
     * @param mensagem
     */
    public static void mostrarMensagemAtencao(String titulo, String mensagem) {
        setMessageWarn(titulo, mensagem);
    }

    /**
     * Método para exibição de mensagem de erro ao realizar cadastro/edição/busca.
     *
     * @param mensagem
     */
    public static void mostrarMensagemErro(String mensagem) {
        setMessageError(mensagem);
    }

    /**
     * Método para exibição de mensagem de erro ao realizar cadastro/edição/busca.
     *
     * @param titulo
     * @param mensagem
     */
    public static void mostrarMensagemErro(String titulo, String mensagem) {
        setMessageError(titulo, mensagem);
    }

    /**
     * Método para exibição de mensagem indicando que a lista no formulário de
     * busca está vazia, ou seja, não houveram resultados.
     *
     * @param mensagem
     */
    public static void mostrarMensagemVazia(String mensagem) {
        setMessageInfo("Lista Vazia!", mensagem);
    }

    private static void setMessageInfo(String mensagem) {
        showFacesMessage("sucesso", FacesMessage.SEVERITY_INFO, "Sucesso!", mensagem);
    }

    private static void setMessageInfo(String titulo, String mensagem) {
        showFacesMessage("sucesso", FacesMessage.SEVERITY_INFO, titulo, mensagem);
    }

    private static void setMessageWarn(String mensagem) {
        showFacesMessage("atencao", FacesMessage.SEVERITY_WARN, "Atenção!", mensagem);
    }

    private static void setMessageWarn(String titulo, String mensagem) {
        showFacesMessage("atencao", FacesMessage.SEVERITY_WARN, titulo, mensagem);
    }

    private static void setMessageError(String mensagem) {
        showFacesMessage("erro", FacesMessage.SEVERITY_ERROR, "Erro!", mensagem);
    }

    private static void setMessageError(String titulo, String mensagem) {
        showFacesMessage("erro", FacesMessage.SEVERITY_ERROR, titulo, mensagem);
    }

    private static void showFacesMessage(String name, FacesMessage.Severity severity, String title, String message) {
        getFacesContext().getExternalContext().getFlash().setKeepMessages(true);
        getFacesContext().addMessage(name, new FacesMessage(severity, title, message));
    }

}
