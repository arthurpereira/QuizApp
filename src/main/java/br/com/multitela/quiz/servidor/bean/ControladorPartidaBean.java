/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.multitela.quiz.servidor.bean;

import br.com.multitela.quiz.servidor.bean.model.AlternativaDataModel;
import br.com.multitela.quiz.servidor.bean.model.JogadorPartidaDataModel;
import br.com.multitela.quiz.servidor.bean.observer.PartidaObservada;
import br.com.multitela.quiz.servidor.bean.observer.PartidaObservador;
import br.com.multitela.quiz.servidor.dto.RespostasPorAlternativaDTO;
import br.com.multitela.quiz.servidor.dto.RespostasPorPerguntaDTO;
import br.com.multitela.quiz.servidor.entity.JogadorPartidaAssociativa;
import br.com.multitela.quiz.servidor.entity.Partida;
import br.com.multitela.quiz.servidor.entity.Pergunta;
import br.com.multitela.quiz.servidor.enums.AlternativaStatus;
import br.com.multitela.quiz.servidor.enums.PartidaStatus;
import br.com.multitela.quiz.servidor.enums.PerguntaStatus;
import br.com.multitela.quiz.servidor.service.JogadorPartidaAssociativaService;
import br.com.multitela.quiz.servidor.service.PartidaService;
import br.com.multitela.quiz.servidor.service.PerguntaService;
import br.com.multitela.quiz.servidor.service.RespostaService;
import br.com.multitela.quiz.servidor.singleton.PartidaSingleton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.NoResultException;

import br.com.multitela.quiz.servidor.util.AlternativaUtil;
import org.primefaces.context.RequestContext;
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

    @EJB
    private RespostaService respostaService;

    Partida partida;

    //Lista de perguntas;
    private List<Pergunta> listaDePerguntas;
    private AlternativaDataModel listaAlternativasPorPergunta;
    private List<AlternativaStatus> listaStatusPorAlternativa;
    
    //Salva os jogadores da partida nessa lista
    private List<JogadorPartidaAssociativa> jogadoresPorPartida;
    private JogadorPartidaDataModel listaJogadoresPorPartida;
    private List<PartidaObservador> obs = new ArrayList<>();

    //Salva as pontuações dos jogadores em ordem decrescente
    List<Integer> listaPontuacoesPlacar;
    //Salva as quantidades de respostas por alternativas de uma pergunta
    List<RespostasPorAlternativaDTO> listaRespostasPorAlternativa;
    //Salva as respostas dos jogadores para a pergunta atual
    List<RespostasPorPerguntaDTO> listaRespostasPorPergunta;
    //Salva se os jogadores já responderam a pergunta atual
    List<Boolean> listaJogadoresJaResponderam;
    
    //Variável que simula um ponteiro para indicar a posição da pergunta atual na lista de perguntas.
    private int posicaoPergunta;
    //Variável que armazena a quantidade total de jogadores na partida.
    private int quantJogadoresTotal;
    //Variável que armazena a quantidade de jogadores que responderam a pergunta atual.
    private int quantJogadoresJaResponderam;
    
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
            limpaStatusPorAlternativa();
            perguntaStatus = PerguntaStatus.RESPOSTA_DESABILITADA;
            try {
                Thread.sleep(500);
                atualizarPlacar();
            } catch (Exception e) {}
            quantJogadoresTotal = partidaAssociativaService.countTodosJogadoresPorPartida(partida);
        } catch (Exception e) {
            keepDialogOpen();
            mensagemAtencao("Não foi possível iniciar a partida");
            e.printStackTrace();
        }
    }
    
    public void encerraPartida() {
        ExternalContext externalContext = FacesContext.getCurrentInstance()
                .getExternalContext();
        try {
            partidaStatus = PartidaStatus.NAO_INICIADA;
            perguntaStatus = PerguntaStatus.RESPOSTA_DESABILITADA;
            externalContext.redirect(externalContext.getRequestContextPath()
                    + "/admin/");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            PartidaSingleton.removePlacarSingletonInstance();
        }
    }
    
    /**
     * Método utilizado para realizar a mudança de pergunta.
     */
    public void mudarPergunta() {
        if (posicaoPergunta < listaDePerguntas.size() - 1) {
            perguntaAtual = listaDePerguntas.get(++posicaoPergunta);
            notificarPerguntaAtual(posicaoPergunta - 1);
            listaAlternativasPorPergunta = new AlternativaDataModel(listaDePerguntas.get(posicaoPergunta).getAlternativas());
            perguntaStatus = PerguntaStatus.RESPOSTA_DESABILITADA;
            EventBus eventBus = EventBusFactory.getDefault().eventBus();
            eventBus.publish("/pergunta", new FacesMessage("mudando pergunta"));
            RequestContext.getCurrentInstance().execute("jogo();");
            RequestContext.getCurrentInstance().execute("mudarPerguntaJS();");
        } else {
            EventBus eventBus = EventBusFactory.getDefault().eventBus();
            eventBus.publish("/pergunta", new FacesMessage("mudando pergunta"));
            RequestContext.getCurrentInstance().execute("fimdojogo();");
            partidaStatus = PartidaStatus.ENCERRADA;
        }
        limpaStatusPorAlternativa();
        quantJogadoresJaResponderam = 0;
    }
    
    /**
     * Método utilizado para mostrar a resposta certa da pergunta atual.
     */
    public void habilitarAlternativas() {
        if (perguntaStatus == PerguntaStatus.RESPOSTA_DESABILITADA || perguntaStatus != PerguntaStatus.RESPONDIDA) {
            EventBus eventBus = EventBusFactory.getDefault().eventBus();
            eventBus.publish("/alternativa", new FacesMessage("habilitar alternativas"));
            perguntaStatus = PerguntaStatus.RESPOSTA_HABILITADA;
            limpaStatusPorAlternativa();
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
                listaStatusPorAlternativa.set(perguntaAtual.getAlternativa_certa(), AlternativaStatus.CERTA);
                atualizarPlacar();
                mostrarQuantidadeRespostasPorAlternativa();
                listaJogadoresJaResponderam = new ArrayList<>();
            } else {
                mensagemAtencao("A pergunta atual já foi respondida.");
            }
        } else {
            mensagemAtencao("É preciso habilitar as alternativas.");
        }
    }

    public void mostrarQuantidadeRespostasPorAlternativa() {
        try {
            listaRespostasPorAlternativa = respostaService.countRespostasPorAlternativasDaPerguntaNaPartida(perguntaAtual, partida);
        } catch (NoResultException ex) {
            listaRespostasPorAlternativa = new ArrayList<>();
        }
    }

    public void atualizarListaJogadoresJaResponderam() {
        try {
            listaJogadoresJaResponderam = respostaService.consultaTop10ExistemRespostasPorPerguntaNaPartida(perguntaAtual, partida);
        } catch (NoResultException ex) {
            listaJogadoresJaResponderam = new ArrayList<>();
        }
        quantJogadoresJaResponderam = respostaService.countExistemRespostasPorPerguntaNaPartida(perguntaAtual, partida);
    }
    
    @Override
    public void atualizarPlacar() {
        try {
            listaRespostasPorPergunta = respostaService.consultaTop10RespostasPorPerguntaNaPartida(perguntaAtual, partida);
        } catch (NoResultException ex) {
            listaRespostasPorPergunta = new ArrayList<>();
        }
        try {
            listaPontuacoesPlacar = partidaAssociativaService.consultaPontuacoesPorPartida(partida);
        } catch (NoResultException ex) {
            listaPontuacoesPlacar = new ArrayList<>();
        }
        try {
            jogadoresPorPartida = partidaAssociativaService.consultaTop10JogadoresPorPartida(partida);
        } catch (NoResultException ex) {
            jogadoresPorPartida = new ArrayList<>();
        }
        listaJogadoresPorPartida = new JogadorPartidaDataModel(jogadoresPorPartida);
        notificarPlacar(jogadoresPorPartida, listaPontuacoesPlacar);
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

    @PostConstruct
    public void listarPerguntas() {
        jogadoresPorPartida = new ArrayList<>();
        listaJogadoresPorPartida = new JogadorPartidaDataModel(jogadoresPorPartida);
        try {
            listaDePerguntas = perguntaService.listAll();
            listaAlternativasPorPergunta = new AlternativaDataModel(listaDePerguntas.get(posicaoPergunta).getAlternativas());
        } catch (NoResultException ex) {
            listaDePerguntas = new ArrayList<>();
        }
        notificarPerguntas(listaDePerguntas);
    }
    
    @Override
    public void notificarPerguntas(List<Pergunta> perguntas) {
        for (PartidaObservador o : obs) {
            o.atualizarPerguntas(perguntas);
        }
    }

    @Override
    public void notificarPerguntaAtual(int indice) {
        for (PartidaObservador o : obs) {
            o.atualizarPerguntaIndice(indice);
        }
    }

    private void limpaStatusPorAlternativa() {
        listaStatusPorAlternativa = new ArrayList<>();
        for (int i = 0; i < perguntaAtual.getAlternativas().size(); i++) {
            if (perguntaStatus != PerguntaStatus.RESPOSTA_DESABILITADA)
                listaStatusPorAlternativa.add(i, AlternativaStatus.NAO_ESCOLHIDA);
            else
                listaStatusPorAlternativa.add(i, AlternativaStatus.DESABILITADA);
        }
    }

    public String retornaAlternativaCSSClass(int posicao) {
        return listaStatusPorAlternativa.get(posicao).getCssClass();
    }

    public String retornaLetraAlternativa(int indice) {
        return AlternativaUtil.retornaLetraAlternativa(indice);
    }

    public int retornaColocacao(int pontuacao) {
        return listaPontuacoesPlacar.indexOf(pontuacao) + 1;
    }

    public int retornaQuantidadeJogadoresNaoResponderam() {
        return quantJogadoresTotal - quantJogadoresJaResponderam;
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

    public AlternativaDataModel getListaAlternativasPorPergunta() {
        return listaAlternativasPorPergunta;
    }

    public boolean isPartidaNaoIniciada() {
        return partidaStatus == PartidaStatus.NAO_INICIADA;
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

    public boolean isAlternativasDesabilitadas() {
        return perguntaStatus == PerguntaStatus.RESPOSTA_DESABILITADA;
    }

    public boolean isPerguntaRespondida() {
        return perguntaStatus == PerguntaStatus.RESPONDIDA;
    }

    public JogadorPartidaDataModel getListaJogadoresPorPartida() {
        return listaJogadoresPorPartida;
    }

    public List<RespostasPorAlternativaDTO> getListaRespostasPorAlternativa() {
        return listaRespostasPorAlternativa;
    }

    public List<RespostasPorPerguntaDTO> getListaRespostasPorPergunta() {
        return listaRespostasPorPergunta;
    }

    public List<Boolean> getListaJogadoresJaResponderam() {
        return listaJogadoresJaResponderam;
    }
}
