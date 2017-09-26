package br.com.multitela.quiz.servidor.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by arthurpereira on 1/13/17.
 */
@Entity
@Table(name = "questionario")
public class Questionario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne()
    @JoinColumn(name = "jogador_partida_id", nullable = false)
    private JogadorPartidaAssociativa jogadorPartida;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data")
    private Date data;

    @Column(name = "idade")
    private String idade;

    @Column(name = "sexo")
    private String sexo;

//    @Column(name = "estado_civil")
//    private String estadoCivil;

    @Column(name = "escolaridade")
    private String escolaridade;

    @Column(name = "mora_atualmente")
    private String moraAtualmente;

//    @Column(name = "renda_mensal")
//    private String rendaMensal;

//    @Column(name = "quantas_pessoas")
//    private String quantasPessoas;

    @Column(name = "situacao_descreve")
    private String situacaoDescreve;

//    @Column(name = "ingresso_graduacao")
//    private String ingressoGraduacao;

//    @Column(name = "escolaridade_pai")
//    private String escolaridadePai;
//
//    @Column(name = "escolaridade_mae")
//    private String escolaridadeMae;

//    @Column(name = "tipo_escola")
//    private String tipoEscola;

//    @Column(name = "frequencia_biblioteca")
//    private String frequenciaBiblioteca;

    @Column(name = "fonte_pesquisa")
    private String fontePesquisa;

    @Column(name = "utiliza_celular")
    private String utilizaCelular;

//    @Column(name = "atividade_academica")
//    private String atividadeAcademica;

    @Column(name = "quiz_satisfacao")
    private String quizSatisfacao;

    @Column(name = "quiz_experiencia")
    private String quizExperiencia;

    @Column(name = "quiz_interface")
    private String quizInterface;

//    @Column(name = "quiz_memorizacao")
//    private String quizMemorizacao;

//    @Column(name = "quiz_sem_usar")
//    private String quizSemUsar;

    @Column(name = "quiz_ajudou_ensino")
    private String quizAjudouEnsino;

//    @Column(name = "quiz_pode_melhorar")
//    private String quizPodeMelhorar;

    @Column(name = "sistema_operacional")
    private String sistemaOperacional;

    @Column(name = "sistema_operacional_versao")
    private String sistemaOperacionalVersao;

    @Column(name = "dispositivo")
    private String dispositivo;

    @Column(name = "navegador_nome")
    private String navegadorNome;

    @Column(name = "navegador_versao")
    private String navegadorVersao;

    @Column(name = "fabricante")
    private String fabricante;

    // GETTERS AND SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JogadorPartidaAssociativa getJogadorPartida() {
        return jogadorPartida;
    }

    public void setJogadorPartida(JogadorPartidaAssociativa jogadorPartida) {
        this.jogadorPartida = jogadorPartida;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

//    public String getEstadoCivil() {
//        return estadoCivil;
//    }
//
//    public void setEstadoCivil(String estadoCivil) {
//        this.estadoCivil = estadoCivil;
//    }

    public String getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(String escolaridade) {
        this.escolaridade = escolaridade;
    }

    public String getMoraAtualmente() {
        return moraAtualmente;
    }

    public void setMoraAtualmente(String moraAtualmente) {
        this.moraAtualmente = moraAtualmente;
    }

//    public String getRendaMensal() {
//        return rendaMensal;
//    }
//
//    public void setRendaMensal(String rendaMensal) {
//        this.rendaMensal = rendaMensal;
//    }
//
//    public String getQuantasPessoas() {
//        return quantasPessoas;
//    }
//
//    public void setQuantasPessoas(String quantasPessoas) {
//        this.quantasPessoas = quantasPessoas;
//    }

    public String getSituacaoDescreve() {
        return situacaoDescreve;
    }

    public void setSituacaoDescreve(String situacaoDescreve) {
        this.situacaoDescreve = situacaoDescreve;
    }

//    public String getIngressoGraduacao() {
//        return ingressoGraduacao;
//    }
//
//    public void setIngressoGraduacao(String ingressoGraduacao) {
//        this.ingressoGraduacao = ingressoGraduacao;
//    }
//
//    public String getEscolaridadePai() {
//        return escolaridadePai;
//    }
//
//    public void setEscolaridadePai(String escolaridadePai) {
//        this.escolaridadePai = escolaridadePai;
//    }
//
//    public String getEscolaridadeMae() {
//        return escolaridadeMae;
//    }
//
//    public void setEscolaridadeMae(String escolaridadeMae) {
//        this.escolaridadeMae = escolaridadeMae;
//    }
//
//    public String getTipoEscola() {
//        return tipoEscola;
//    }
//
//    public void setTipoEscola(String tipoEscola) {
//        this.tipoEscola = tipoEscola;
//    }
//
//    public String getFrequenciaBiblioteca() {
//        return frequenciaBiblioteca;
//    }
//
//    public void setFrequenciaBiblioteca(String frequenciaBiblioteca) {
//        this.frequenciaBiblioteca = frequenciaBiblioteca;
//    }

    public String getFontePesquisa() {
        return fontePesquisa;
    }

    public void setFontePesquisa(String fontePesquisa) {
        this.fontePesquisa = fontePesquisa;
    }

    public String getUtilizaCelular() {
        return utilizaCelular;
    }

    public void setUtilizaCelular(String utilizaCelular) {
        this.utilizaCelular = utilizaCelular;
    }

    //    public String getAtividadeAcademica() {
//        return atividadeAcademica;
//    }
//
//    public void setAtividadeAcademica(String atividadeAcademica) {
//        this.atividadeAcademica = atividadeAcademica;
//    }

    public String getQuizSatisfacao() {
        return quizSatisfacao;
    }

    public void setQuizSatisfacao(String quizSatisfacao) {
        this.quizSatisfacao = quizSatisfacao;
    }

    public String getQuizExperiencia() {
        return quizExperiencia;
    }

    public void setQuizExperiencia(String quizExperiencia) {
        this.quizExperiencia = quizExperiencia;
    }

    public String getQuizInterface() {
        return quizInterface;
    }

    public void setQuizInterface(String quizInterface) {
        this.quizInterface = quizInterface;
    }

//    public String getQuizMemorizacao() {
//        return quizMemorizacao;
//    }
//
//    public void setQuizMemorizacao(String quizMemorizacao) {
//        this.quizMemorizacao = quizMemorizacao;
//    }
//
//    public String getQuizSemUsar() {
//        return quizSemUsar;
//    }
//
//    public void setQuizSemUsar(String quizSemUsar) {
//        this.quizSemUsar = quizSemUsar;
//    }

    public String getQuizAjudouEnsino() {
        return quizAjudouEnsino;
    }

    public void setQuizAjudouEnsino(String quizAjudouEnsino) {
        this.quizAjudouEnsino = quizAjudouEnsino;
    }

//    public String getQuizPodeMelhorar() {
//        return quizPodeMelhorar;
//    }
//
//    public void setQuizPodeMelhorar(String quizPodeMelhorar) {
//        this.quizPodeMelhorar = quizPodeMelhorar;
//    }

    public String getSistemaOperacional() {
        return sistemaOperacional;
    }

    public void setSistemaOperacional(String sistemaOperacional) {
        this.sistemaOperacional = sistemaOperacional;
    }

    public String getSistemaOperacionalVersao() {
        return sistemaOperacionalVersao;
    }

    public void setSistemaOperacionalVersao(String sistemaOperacionalVersao) {
        this.sistemaOperacionalVersao = sistemaOperacionalVersao;
    }

    public String getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(String dispositivo) {
        this.dispositivo = dispositivo;
    }

    public String getNavegadorNome() {
        return navegadorNome;
    }

    public void setNavegadorNome(String navegadorNome) {
        this.navegadorNome = navegadorNome;
    }

    public String getNavegadorVersao() {
        return navegadorVersao;
    }

    public void setNavegadorVersao(String navegadorVersao) {
        this.navegadorVersao = navegadorVersao;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }
}
