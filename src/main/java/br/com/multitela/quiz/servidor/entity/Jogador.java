/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.multitela.quiz.servidor.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author arthurpereira
 */
@Entity
@Table(name = "jogador")
public class Jogador implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "facebook_id")
    private long facebook_id;
    
    @Column(name = "nome")
    private String nome;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_criacao")
    private Date dataCriacao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_ultimo_login")
    private Date dataUltimoLogin;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "jogador", cascade=CascadeType.ALL)
    private List<JogadorPartidaAssociativa> partidas = new ArrayList<>();

    public long getFacebook_id() {
        return facebook_id;
    }

    public void setFacebook_id(long facebook_id) {
        this.facebook_id = facebook_id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataUltimoLogin() {
        return dataUltimoLogin;
    }

    public void setDataUltimoLogin(Date dataUltimoLogin) {
        this.dataUltimoLogin = dataUltimoLogin;
    }

    public List<JogadorPartidaAssociativa> getPartidas() {
        return partidas;
    }

    public void setPartidas(List<JogadorPartidaAssociativa> partidas) {
        this.partidas = partidas;
    }
}
