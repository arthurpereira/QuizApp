/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.multitela.quiz.servidor.entity;

import br.com.multitela.quiz.servidor.enums.LoginEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "facebook_id", unique = true)
    private Long facebook_id;

    @Column(name = "matricula", unique = true)
    private Long matricula;
    
    @Column(name = "nome")
    private String nome;

    @Column(name = "sobrenome")
    private String sobrenome;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_criacao")
    private Date dataCriacao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_ultimo_login")
    private Date dataUltimoLogin;

    @Enumerated(EnumType.STRING)
    @Column(name = "login_tipo")
    private LoginEnum loginTipo;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "jogador", cascade=CascadeType.ALL)
    private List<JogadorPartidaAssociativa> partidas = new ArrayList<>();

    // GETTERS AND SETTERS

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFacebook_id() {
        return facebook_id;
    }

    public void setFacebook_id(long facebook_id) {
        this.facebook_id = facebook_id;
    }

    public long getMatricula() {
        return matricula;
    }

    public void setMatricula(long matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getNomeCompleto() {
        return getNome() + " " + getSobrenome();
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

    public LoginEnum getLoginTipo() {
        return loginTipo;
    }

    public void setLoginTipo(LoginEnum loginTipo) {
        this.loginTipo = loginTipo;
    }

    public List<JogadorPartidaAssociativa> getPartidas() {
        return partidas;
    }

    public void setPartidas(List<JogadorPartidaAssociativa> partidas) {
        this.partidas = partidas;
    }

    public boolean isLoginTipoFacebook() {
        return loginTipo == LoginEnum.FACEBOOK;
    }

    public boolean isLoginTipoSemFacebook() {
        return loginTipo == LoginEnum.SEM_FACEBOOK;
    }
}
