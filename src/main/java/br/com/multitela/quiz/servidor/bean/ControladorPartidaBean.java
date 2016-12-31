/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.multitela.quiz.servidor.bean;

import br.com.multitela.quiz.servidor.bean.observer.PartidaObservada;
import br.com.multitela.quiz.servidor.bean.observer.PartidaObservador;
import br.com.multitela.quiz.servidor.entity.JogadorPartidaAssociativa;
import br.com.multitela.quiz.servidor.entity.Partida;
import br.com.multitela.quiz.servidor.entity.Pergunta;
import br.com.multitela.quiz.servidor.enums.PartidaStatus;
import br.com.multitela.quiz.servidor.enums.PerguntaStatus;
import br.com.multitela.quiz.servidor.service.JogadorPartidaAssociativaService;
import br.com.multitela.quiz.servidor.service.PartidaService;
import br.com.multitela.quiz.servidor.service.PerguntaService;
import br.com.multitela.quiz.servidor.singleton.PartidaSingleton;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.persistence.NoResultException;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

/**
 *
 * @author arthurpereira
 */
@Named
@SessionScoped
public class ControladorPartidaBean extends AbstractBean implements PartidaObservada {

    @EJB
    private PerguntaService perguntaService;

    @EJB
    private JogadorPartidaAssociativaService partidaAssociativaService;

    @EJB
    private PartidaService partidaService;

    Partida partida;

    //Lista de perguntas;
    private List<Pergunta> listaDePerguntas;
    
    //Salva os jogadores da partida nessa lista
    private List<JogadorPartidaAssociativa> jogadoresPorPartida = new ArrayList<>();
    private List<PartidaObservador> obs = new ArrayList<>();
    
    //Variável que simula um ponteiro para indicar a posição da pergunta atual na lista de perguntas.
    private int posicaoPergunta;
    
    //Objeto auxiliar que será usado apenas para referenciar a pergunta atual na view do usuário.
    private Pergunta perguntaAtual;

    //Variável que indica o estado da partida
    private PartidaStatus partidaStatus;
    private PerguntaStatus perguntaStatus;
    
    public ControladorPartidaBean() {
        partidaStatus = PartidaStatus.NAO_INICIADA;
        perguntaStatus = PerguntaStatus.RESPOSTA_DESABILITADA;
    }
    
    public void iniciarPartida() {
        PartidaSingleton.setPlacarSingletonInstance(this);
        try {
            partida = new Partida();
            partida.setData(new Date());
            partidaService.save(partida);
            EventBus eventBus = EventBusFactory.getDefault().eventBus();
            eventBus.publish("/partida", new FacesMessage("iniciando partida"));
            partidaStatus = PartidaStatus.INICIADA;
            posicaoPergunta = 0;
            perguntaAtual = listaDePerguntas.get(posicaoPergunta);
            mensagemSucesso("A partida foi iniciada.");
            perguntaStatus = PerguntaStatus.RESPOSTA_DESABILITADA;
            try {
                Thread.sleep(500);
                atualizarPlacar();
            } catch (Exception e) {}
        } catch (Exception e) {
            keepDialogOpen();
            mensagemAtencao("Não foi possível iniciar a partida");
            e.printStackTrace();
        }
    }
    
    public void encerraPartida() {
        PartidaSingleton.removePlacarSingletonInstance();
        partidaStatus = PartidaStatus.ENCERRADA;
        mensagemSucesso("A partida foi encerrada.");
    }
    
    /**
     * Método utilizado para realizar a mudança de pergunta.
     */
    public void mudarPergunta() {
        if (posicaoPergunta < listaDePerguntas.size() - 1) {
            perguntaAtual = listaDePerguntas.get(++posicaoPergunta);
            perguntaStatus = PerguntaStatus.RESPOSTA_DESABILITADA;
            EventBus eventBus = EventBusFactory.getDefault().eventBus();
            eventBus.publish("/pergunta", new FacesMessage("mudando pergunta"));
        } else {
            EventBus eventBus = EventBusFactory.getDefault().eventBus();
            eventBus.publish("/pergunta", new FacesMessage("mudando pergunta"));
            encerraPartida();
        }
    }
    
    /**
     * Método utilizado para mostrar a resposta certa da pergunta atual.
     */
    public void habilitarAlternativas() {
        if (perguntaStatus == PerguntaStatus.RESPOSTA_DESABILITADA || perguntaStatus != PerguntaStatus.RESPONDIDA) {
            EventBus eventBus = EventBusFactory.getDefault().eventBus();
            eventBus.publish("/alternativa", new FacesMessage("habilitar alternativas"));
            perguntaStatus = PerguntaStatus.RESPOSTA_HABILITADA;
            mensagemSucesso("As alternativas foram habilitadas.");
        } else {
            mensagemAtencao("As alternativas já foram habilitadas.");
        }
    }
    
    /**
     * Método utilizado para mostrar a resposta certa da pergunta atual.
     */
    public void mostrarRespostaCerta() {
        if (perguntaStatus != PerguntaStatus.RESPOSTA_DESABILITADA) {
            if (perguntaStatus == PerguntaStatus.RESPOSTA_HABILITADA) {
                EventBus eventBus = EventBusFactory.getDefault().eventBus();
                eventBus.publish("/resposta", new FacesMessage("mostra resposta certa"));
                perguntaStatus = PerguntaStatus.RESPONDIDA;
                mensagemSucesso("A resposta certa foi mostrada.");
                atualizarPlacar();
            } else {
                mensagemAtencao("A pergunta atual já foi respondida.");
            }
        } else {
            mensagemAtencao("É preciso habilitar as alternativas.");
        }
    }
    
    @Override
    public void atualizarPlacar() {
        List<Integer> pontuacoes;
        try {
            pontuacoes = partidaAssociativaService.consultaPontuacoesPorPartida(partida.getId());
        } catch (NoResultException ex) {
            pontuacoes = new ArrayList<>();
        }
        try {
            jogadoresPorPartida = partidaAssociativaService.consultaJogadoresPorPartida(partida.getId());
        } catch (NoResultException ex) {
            jogadoresPorPartida = new ArrayList<>();
        }
        notificarPlacar(jogadoresPorPartida, pontuacoes);
    }

    @Override
    public void notificarPlacar(List<JogadorPartidaAssociativa> placar, List<Integer> pontuacoes) {
        for(PartidaObservador o : obs) {
            o.atualizarPlacar(jogadoresPorPartida, pontuacoes);
        }
    }

    @Override
    public void addObservador(PartidaObservador o) {
        obs.add(o);
    }

    @Override
    @PostConstruct
    public void listarPerguntas() {
        try {
            listaDePerguntas = perguntaService.listAll();
        } catch (NoResultException ex) {
            listaDePerguntas = new ArrayList<>();
        }
        notificarPerguntas(listaDePerguntas);
    }
    
    @Override
    public void notificarPerguntas(List<Pergunta> perguntas) {
        for(PartidaObservador o : obs) {
            o.atualizarPerguntas(perguntas);
        }
    }
    
    //GETTERS AND SETTERS

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    public List<Pergunta> getListaDePerguntas() {
        return listaDePerguntas;
    }

    public int getPosicaoPergunta() {
        return posicaoPergunta;
    }

    public Pergunta getPerguntaAtual() {
        return perguntaAtual;
    }

    public boolean isPartidaIniciada() {
        return partidaStatus == PartidaStatus.INICIADA;
    }

    public boolean isPartidaEncerrada() {
        return partidaStatus == PartidaStatus.ENCERRADA;
    }

    public boolean isLiberaResponderPergunta() {
        return perguntaStatus != PerguntaStatus.RESPONDIDA;
    }

    public boolean isAlternativasHabilitadas() {
        return perguntaStatus == PerguntaStatus.RESPOSTA_HABILITADA;
    }
    
}
