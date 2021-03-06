/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.multitela.quiz.servidor.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author arthurpereira
 */
@Entity
@Table(name = "resposta")
public class Resposta implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    @Column(name = "alternativa_indice")
    private int alternativaIndice;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "alternativa_id")
    private Alternativa alternativa;
    
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "pergunta_id")
    private Pergunta pergunta;
    
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "jogador_partida_id")
    private JogadorPartidaAssociativa jogadorPartida;

    // GETTERS AND SETTERS

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAlternativaIndice() {
        return alternativaIndice;
    }

    public void setAlternativaIndice(int alternativaEscolhida) {
        this.alternativaIndice = alternativaEscolhida;
    }

    public Alternativa getAlternativa() {
        return alternativa;
    }

    public void setAlternativa(Alternativa alternativa) {
        this.alternativa = alternativa;
    }

    public Pergunta getPergunta() {
        return pergunta;
    }

    public void setPergunta(Pergunta pergunta) {
        this.pergunta = pergunta;
    }

    public JogadorPartidaAssociativa getJogadorPartida() {
        return jogadorPartida;
    }

    public void setJogadorPartida(JogadorPartidaAssociativa jogadorPartida) {
        this.jogadorPartida = jogadorPartida;
    }
    
}
