package br.com.multitela.quiz.servidor.exception;

/**
 * Created by arthurpereira on 28/03/17.
 */
public class FormatoDeArquivoInvalidoException extends Exception {

    public static final long serialVersionUID = 1L;

    public FormatoDeArquivoInvalidoException() {
        super("O formato de arquivo enviado é inválido ou não é permitido.");
    }
}
