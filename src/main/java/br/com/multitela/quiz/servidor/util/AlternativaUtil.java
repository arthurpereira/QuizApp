package br.com.multitela.quiz.servidor.util;

/**
 * Created by arthurpereira on 03/10/17.
 */
public class AlternativaUtil {

    public static String retornaLetraAlternativa(int indice) {
        return String.valueOf( (char) ( indice + 65 ) );
    }

    public static int retornaIndiceAlternativa(char letra) {
        return  ((int) letra) - 65;
    }

}
