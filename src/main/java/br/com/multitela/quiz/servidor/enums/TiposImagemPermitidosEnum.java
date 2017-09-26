package br.com.multitela.quiz.servidor.enums;

/**
 * Created by arthurpereira on 28/03/17.
 */
public enum TiposImagemPermitidosEnum {

    JPG("image/jpg"),
    JPEG("image/jpeg"),
    PNG("image/png"),
    GIF("image/gif");

    private String mimeType;

    private TiposImagemPermitidosEnum(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }

}
