package br.com.multitela.quiz.servidor.exception;

/**
 * Created by arthurpereira on 28/03/17.
 */
public class DiretorioNaoEncontradoException extends Exception {

    public static final long serialVersionUID = 1L;

    public DiretorioNaoEncontradoException() {
        super("O diretório de arquivos não foi encontrado ou não existe.");
    }
}
