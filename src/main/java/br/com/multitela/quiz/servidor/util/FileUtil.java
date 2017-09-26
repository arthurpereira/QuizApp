package br.com.multitela.quiz.servidor.util;

import br.com.multitela.quiz.servidor.enums.TiposImagemPermitidosEnum;

import javax.servlet.http.Part;

/**
 * Created by arthurpereira on 28/03/17.
 */
public class FileUtil {

    public static final int MAX_FILE_SIZE = 2048000;

    public static String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1);
            }
        }
        return null;
    }

    public static String getExtension(String filename) {
        String extension = "";

        int i = filename.lastIndexOf('.');
        int p = Math.max(filename.lastIndexOf('/'), filename.lastIndexOf('\\'));

        if (i > p) {
            extension = filename.substring(i+1);
        }

        return extension;
    }

    public static String getType(String mimeType) {
        String type = mimeType.substring(mimeType.lastIndexOf('/') + 1, mimeType.length());

        return type;
    }

    public static boolean validateMimeType(String mimeType) {
        boolean isValid = false;

        for (TiposImagemPermitidosEnum validMimeType : TiposImagemPermitidosEnum.values()) {
            if (mimeType == validMimeType.getMimeType()) {
                isValid = true;
            }
        }

        return isValid;
    }

}
