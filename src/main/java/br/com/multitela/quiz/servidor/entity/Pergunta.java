/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.multitela.quiz.servidor.entity;

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
import javax.persistence.Table;
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
    
    @Column(name = "texto")
    private String texto;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @Column(name = "alternativa_id")
    private List<Alternativa> alternativa;
    
    @Column(name = "alternativa_certa")
    private int alternativa_certa;
    
    @OneToMany(cascade = {CascadeType.DETACH}, mappedBy = "pergunta")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Resposta> respostas;

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
        this.texto = texto;
    }

    public List<Alternativa> getAlternativa() {
        return alternativa;
    }

    public void setAlternativa(List<Alternativa> alternativa) {
        this.alternativa = alternativa;
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
