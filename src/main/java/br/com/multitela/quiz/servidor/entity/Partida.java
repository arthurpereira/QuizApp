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
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "partida")
public class Partida implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "partida_id")
    private long id;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_partida")
    private Date data;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "partida")
    private List<JogadorPartidaAssociativa> jogadores = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public List<JogadorPartidaAssociativa> getJogadores() {
        return jogadores;
    }

    public void setJogadores(List<JogadorPartidaAssociativa> jogadores) {
        this.jogadores = jogadores;
    }

}
