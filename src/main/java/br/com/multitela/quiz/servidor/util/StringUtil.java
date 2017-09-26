package br.com.multitela.quiz.servidor.util;

import org.apache.commons.lang3.text.WordUtils;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by arthurpereira on 2/10/17.
 */
public class StringUtil {

    public static String formatName(String text){
        text = removeUnnecessarySpaces(text);
        text = capitalize(text);

        return text;
    }

    private static String removeUnnecessarySpaces(String text) {
        return text.trim().replaceAll(" +", " ");
    }

    private static String capitalize(String text) {
        return WordUtils.capitalize(text.toLowerCase());
    }

    public static String generateToken(int length) {
        return new BigInteger(length * 5, new SecureRandom()).toString(32);
    }

}
