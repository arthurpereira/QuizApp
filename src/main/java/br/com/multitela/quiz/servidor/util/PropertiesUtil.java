package br.com.multitela.quiz.servidor.util;

import java.util.ResourceBundle;

/**
 * Created by arthurpereira on 25/03/17.
 */
public class PropertiesUtil {

    private static ResourceBundle resource;

    public static ResourceBundle getResource() {
        if (resource == null) {
            resource = ResourceBundle.getBundle("br.com.multitela.quiz.servidor.properties.quizapp");
        }
        return resource;
    }
}
