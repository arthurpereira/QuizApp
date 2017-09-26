package br.com.multitela.quiz.servidor.entity;

import br.com.multitela.quiz.servidor.exception.DiretorioNaoEncontradoException;
import br.com.multitela.quiz.servidor.exception.FormatoDeArquivoInvalidoException;
import br.com.multitela.quiz.servidor.util.FileUtil;
import br.com.multitela.quiz.servidor.util.PropertiesUtil;
import br.com.multitela.quiz.servidor.util.StringUtil;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.imageio.ImageIO;
import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by arthurpereira on 25/03/17.
 */
@Entity
@Table(name = "imagem")
public class Imagem implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String TIPOS_DE_IMAGENS_PERMITIDAS = "(image+(\\/(?i)(jpg|png|jpeg|gif))$)";
    private static final String DIRETORIO_IMAGENS = PropertiesUtil.getResource().getString("quizapp.diretorio.imagens");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "width")
    private String width;

    @Column(name = "altura")
    private String height;

    @Column(name = "extensao")
    private String extensao;

    @Transient
    private Pattern pattern = Pattern.compile(TIPOS_DE_IMAGENS_PERMITIDAS);

    @Transient
    private Matcher matcher;

    public void persisteArquivo(InputStream arquivo, String tipo) throws DiretorioNaoEncontradoException, FormatoDeArquivoInvalidoException {
        if (arquivo != null) {
            validaDiretorio(DIRETORIO_IMAGENS);
            validaTipoArquivo(tipo);

            String nome = StringUtil.generateToken(20) + "." + FileUtil.getType(tipo);

            salvaArquivoNoDiretorio(arquivo, nome, DIRETORIO_IMAGENS);
            salvaDimensoes(DIRETORIO_IMAGENS);
        }
    }

    public void removeArquivo() {
        try {
            String arquivo = DIRETORIO_IMAGENS.concat(this.nome);
            File file = new File(arquivo);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean validaDiretorio(String diretorio) throws DiretorioNaoEncontradoException {
        boolean oCaminhoExiste = new File(diretorio).canRead();
        if (!oCaminhoExiste) {
            throw new DiretorioNaoEncontradoException();
        }
        return oCaminhoExiste;
    }

    public boolean validaTipoArquivo(String nome) throws FormatoDeArquivoInvalidoException {
        matcher = pattern.matcher(nome);
        boolean match = matcher.matches();

        if (!match)
            throw new FormatoDeArquivoInvalidoException();

        return match;
    }

    private String salvaArquivoNoDiretorio(InputStream inputStream, String nomeDoArquivo, String diretorio) {
        String arquivo = nomeDoArquivo;
        this.nome = nomeDoArquivo;
        this.extensao = FileUtil.getExtension(nomeDoArquivo);
        nomeDoArquivo = diretorio.concat(nomeDoArquivo);

        try {
            OutputStream out = new FileOutputStream(new File(nomeDoArquivo));
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            out.flush();
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return arquivo;
    }

    public void salvaDimensoes(String diretorio) {
        BufferedImage image;

        try {
            String arquivo = diretorio.concat(this.nome);
            File file = new File(arquivo);
            FileInputStream inputStream = new FileInputStream(file);

            image = ImageIO.read(inputStream);
            this.width = new Integer(image.getWidth()).toString();
            this.height = new Integer(image.getHeight()).toString();

            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public StreamedContent getBinary() throws IOException {
        return new DefaultStreamedContent(new FileInputStream(new File(DIRETORIO_IMAGENS, this.nome)));
    }

    // GETTERS AND SETTERS

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String caminho) {
        this.nome = caminho;
    }

    public String getWidth() {
        return width;
    }

    public String getHeight() {
        return height;
    }

}
