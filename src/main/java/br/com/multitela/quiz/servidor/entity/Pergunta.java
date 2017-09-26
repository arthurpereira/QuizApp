/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.multitela.quiz.servidor.entity;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.multitela.quiz.servidor.exception.DiretorioNaoEncontradoException;
import br.com.multitela.quiz.servidor.exception.FormatoDeArquivoInvalidoException;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author arthurpereira
 */
@Entity
@Table(name = "pergunta")
public class Pergunta implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    @Column(name = "texto", length = 1023)
    private String texto;

    @Column(name = "texto_html", length = 1023)
    private String textoHTML;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Imagem imagem;

    @Column(name = "descricao_imagem", length = 511)
    private String descricaoImagem;

    @Column(name = "descricao_imagem_html", length = 511)
    private String descricaoImagemHTML;
    
    @OneToMany(mappedBy = "pergunta", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<Alternativa> alternativas;
    
    @Column(name = "alternativa_certa")
    private int alternativa_certa;
    
    @OneToMany(cascade = {CascadeType.DETACH}, mappedBy = "pergunta")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Resposta> respostas;

    public void salvarImagem(InputStream arquivo, String tipo) throws DiretorioNaoEncontradoException, FormatoDeArquivoInvalidoException {
        if (imagem != null) {
            imagem.removeArquivo();
        }
        imagem = new Imagem();
        imagem.persisteArquivo(arquivo, tipo);
    }

    public void removeImagem() {
        if (imagem != null) {
            imagem.removeArquivo();
            imagem = null;
        }
    }

    // GETTERS AND SETTERS

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        textoHTML = texto.replaceAll("(\r\n|\n)", "<br />");
        this.texto = texto;
    }

    public String getTextoHTML() {
        return textoHTML;
    }

    public Imagem getImagem() {
        return imagem;
    }

    public String getDescricaoImagem() {
        return descricaoImagem;
    }

    public void setDescricaoImagem(String descricaoImagem) {
        descricaoImagemHTML = descricaoImagem.replaceAll("(\r\n|\n)", "<br />");
        this.descricaoImagem = descricaoImagem;
    }

    public String getDescricaoImagemHTML() {
        return descricaoImagemHTML;
    }

    public List<Alternativa> getAlternativas() {
        return alternativas;
    }

    public void setAlternativas(List<Alternativa> alternativa) {
        this.alternativas = alternativa;
    }

    public int getAlternativa_certa() {
        return alternativa_certa;
    }

    public void setAlternativa_certa(int alternativa_certa) {
        this.alternativa_certa = alternativa_certa;
    }

    public List<Resposta> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<Resposta> respostas) {
        this.respostas = respostas;
    }
    
}
