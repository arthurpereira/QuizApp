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
import br.com.multitela.quiz.servidor.entity.Jogador;
import br.com.multitela.quiz.servidor.entity.JogadorPartidaAssociativa;
import br.com.multitela.quiz.servidor.entity.Partida;
import br.com.multitela.quiz.servidor.entity.Pergunta;
import br.com.multitela.quiz.servidor.entity.Resposta;
import br.com.multitela.quiz.servidor.enums.AlternativaStatus;
import br.com.multitela.quiz.servidor.enums.LoginEnum;
import br.com.multitela.quiz.servidor.enums.PartidaStatus;
import br.com.multitela.quiz.servidor.enums.PerguntaStatus;
import br.com.multitela.quiz.servidor.service.JogadorPartidaAssociativaService;
import br.com.multitela.quiz.servidor.service.JogadorService;
import br.com.multitela.quiz.servidor.service.PartidaService;
import br.com.multitela.quiz.servidor.service.PerguntaService;
import br.com.multitela.quiz.servidor.service.RespostaService;
import br.com.multitela.quiz.servidor.singleton.PartidaSingleton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

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
public class PartidaBean extends AbstractBean implements PartidaObservador {

    @EJB
    private JogadorService jogadorService;

    @EJB
    private PerguntaService perguntaService;

    @EJB
    private PartidaService partidaService;

    @EJB
    private RespostaService respostaService;

    @EJB
    private JogadorPartidaAssociativaService partidaAssociativaService;
    
    private Resposta resposta;
    
    //Lista de perguntas;
    private List<Pergunta> listaDePerguntas;
    private AlternativaDataModel listaAlternativasPorPergunta;
    private List<AlternativaStatus> listaStatusPorAlternativa;
    
    //Variável que simula um ponteiro para indicar a posição da pergunta atual na lista de perguntas.
    private int posicaoPergunta;
    private int posicaoAlternativaEscolhida;
    private int acertos;
    private int erros;
    private int colocacao;
    
    //Objeto auxiliar que será usado apenas para referenciar a pergunta atual na view do usuário.
    private Pergunta perguntaAtual;

    //Variável que indica o estado da partida
    private PartidaStatus partidaStatus;
    private PerguntaStatus perguntaStatus;

    private boolean jogadorEstaLogado;
    
    private Jogador jogador;
    private Partida partida;
    
    private JogadorPartidaAssociativa partidaAssociativa;
    private List<JogadorPartidaAssociativa> jogadoresPorPartida;
    private JogadorPartidaDataModel listaJogadoresPorPartida;
    
    private List<Integer> listaPontuacoesPlacar;
    
    private PartidaObservada obs;

    public PartidaBean() {

    }

    @PostConstruct
    private void limparPartida() {
        resposta = new Resposta();
        posicaoPergunta = 0;
        acertos = 0;
        erros = 0;
        listaDePerguntas = perguntaService.listAll();
        listaAlternativasPorPergunta = new AlternativaDataModel(listaDePerguntas.get(posicaoPergunta).getAlternativas());
        perguntaAtual = listaDePerguntas.get(posicaoPergunta);
        partidaStatus = PartidaStatus.NAO_INICIADA;
        perguntaStatus = PerguntaStatus.RESPOSTA_DESABILITADA;
        jogadorEstaLogado = false;
        jogador = new Jogador();
        partidaAssociativa = new JogadorPartidaAssociativa();
        jogadoresPorPartida = new ArrayList<>();
        listaJogadoresPorPartida = new JogadorPartidaDataModel(jogadoresPorPartida);
    }
    
    public void iniciarPartida() {
        obs = PartidaSingleton.getPlacarSingletonInstance();
        obs.addObservador(this);

        partida = partidaService.consultaUltimaPartida();
        cadastrarJogadorPartida();
        partidaStatus = PartidaStatus.INICIADA;
        perguntaStatus = PerguntaStatus.RESPOSTA_DESABILITADA;
        limpaStatusPorAlternativa();
    }

    public void encerrarPartida() {
//        limparPartida();
        partidaStatus = PartidaStatus.ENCERRADA;
    }
    
    public void selecionaAlternativa(int indice) {
        posicaoAlternativaEscolhida = indice;
    }
    
    public void confirmaResposta() {
        if (perguntaStatus != PerguntaStatus.RESPONDIDA) {
            resposta = new Resposta();
            resposta.setPergunta(perguntaAtual);
            resposta.setAlternativa(perguntaAtual.getAlternativas().get(posicaoAlternativaEscolhida));
            resposta.setAlternativaIndice(posicaoAlternativaEscolhida);
            perguntaStatus = PerguntaStatus.RESPONDIDA;
            listaStatusPorAlternativa.set(posicaoAlternativaEscolhida, AlternativaStatus.ESCOLHIDA);
            salvarResposta();
            EventBus eventBus = EventBusFactory.getDefault().eventBus();
            eventBus.publish("/placar", new FacesMessage("atualizar placar"));
        }
    }
    
    public void mostrarRespostaCerta() {
        RequestContext.getCurrentInstance().execute("PF('dialog-confirma-resposta').hide();");
        if (perguntaStatus == PerguntaStatus.RESPONDIDA) {
            if (resposta.getAlternativaIndice() == perguntaAtual.getAlternativa_certa()) {
                listaStatusPorAlternativa.set(resposta.getAlternativaIndice(), AlternativaStatus.CERTA);
                mensagemInfo("Você Acertou!", "A alternativa "
                        + retornaLetraAlternativa(perguntaAtual.getAlternativa_certa()) + " é a resposta certa.");
            } else {
                listaStatusPorAlternativa.set(perguntaAtual.getAlternativa_certa(), AlternativaStatus.CERTA);
                listaStatusPorAlternativa.set(resposta.getAlternativaIndice(), AlternativaStatus.ERRADA);
                mensagemErro("Você Errou", "A resposta certa é a alternativa "
                        + retornaLetraAlternativa(perguntaAtual.getAlternativa_certa()) + ".");
            }
        } else {
            listaStatusPorAlternativa.set(perguntaAtual.getAlternativa_certa(), AlternativaStatus.CERTA);
            mensagemInfo("Você Errou", "A resposta certa é a alternativa "
                    + retornaLetraAlternativa(perguntaAtual.getAlternativa_certa()) + ".");
            perguntaStatus = PerguntaStatus.RESPONDIDA;
            erros++;
            try {
                partidaAssociativa.setErros(erros);
                partidaAssociativaService.update(partidaAssociativa);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private void salvarResposta() {
        try {
//            partidaAssociativa.getRespostas().add(resposta);
            if (resposta.getAlternativaIndice() == perguntaAtual.getAlternativa_certa())
                acertos++;
            else
                erros++;
            partidaAssociativa.setAcertos(acertos);
            partidaAssociativa.setErros(erros);
            resposta.setJogadorPartida(partidaAssociativa);
            respostaService.save(resposta);
//            if (partidaAssociativa.getId() == null) {
//                partidaAssociativaController.cadastrar(partidaAssociativa);
//            } else {
                partidaAssociativaService.update(partidaAssociativa);
//            }
            //FacesContext.getCurrentInstance().addMessage("default", new FacesMessage("Sucesso", "A resposta foi salva."));
        } catch (Exception e) {
            keepDialogOpen();
            //FacesContext.getCurrentInstance().addMessage("default", new FacesMessage("Erro", "Não foi possível salvar a resposta no banco de dados."));
            e.printStackTrace();
        }
    }
    
    private void cadastrarJogadorPartida() {
        partidaAssociativa.setJogador(jogador);
        partidaAssociativa.setPartida(partida);
        partidaAssociativa.setAcertos(0);
        partidaAssociativa.setErros(0);
        partidaAssociativaService.save(partidaAssociativa);
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        request.getSession().setAttribute("jogador-partida", partidaAssociativa);
    }
    
    @Override
    public void atualizarPlacar(List<JogadorPartidaAssociativa> placar, List<Integer> pontuacoes) {
        jogadoresPorPartida = placar;
        listaPontuacoesPlacar = pontuacoes;

        listaJogadoresPorPartida = new JogadorPartidaDataModel(jogadoresPorPartida);
        this.colocacao = pontuacoes.indexOf(acertos) + 1;
        partidaAssociativa.setColocacao(colocacao);
        partidaAssociativaService.update(partidaAssociativa);
    }
    
    @Override
    public void atualizarPerguntas(List<Pergunta> perguntas) {
        listaDePerguntas = perguntas;
    }

    @Override
    public void atualizarPerguntaIndice(int indice) {
        posicaoPergunta = indice;
    }
    
    public int retornaColocacao(int pontuacao) {
        return listaPontuacoesPlacar.indexOf(pontuacao) + 1;
    }
    
    public int retornaNumeroDeJogadores() {
        return jogadoresPorPartida.size();
    }
    
    public String alternativaEscolhida() {
        if (perguntaAtual != null)
            return perguntaAtual.getAlternativas().get(posicaoAlternativaEscolhida).getTexto();
        else
            return "";
    }

    public String retornaLetraAlternativa(int indice) {
        return AlternativaUtil.retornaLetraAlternativa(indice);
    }
    
    public void habilitarAlternativas() {
        perguntaStatus = PerguntaStatus.RESPOSTA_HABILITADA;
        limpaStatusPorAlternativa();
    }
    
    public void mudarPergunta() {
        if (posicaoPergunta < listaDePerguntas.size() - 1) {
            perguntaAtual = listaDePerguntas.get(++posicaoPergunta);
            listaAlternativasPorPergunta = new AlternativaDataModel(listaDePerguntas.get(posicaoPergunta).getAlternativas());
            RequestContext.getCurrentInstance().execute("jogo();");
        } else {
            RequestContext.getCurrentInstance().execute("fimdojogo();");
            encerrarPartida();
        }
        perguntaStatus = PerguntaStatus.RESPOSTA_DESABILITADA;
        limpaStatusPorAlternativa();
    }

    public void visualizouPlacar() {
        partidaAssociativa.setQuantidadeVisualizacoesPlacar(partidaAssociativa.getQuantidadeVisualizacoesPlacar() + 1);
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
    
    public void registrarUsuario() {
        if (!jogadorEstaLogado) {
            try {
                jogador.setNome(FacesContext.getCurrentInstance().getExternalContext()
                        .getRequestParameterMap().get("nome"));
                jogador.setFacebook_id(Long.parseLong(FacesContext.getCurrentInstance()
                        .getExternalContext().getRequestParameterMap().get("id")));
                System.out.println("\nUSUÁRIO LOGADO: " + jogador.getNome() +
                        "\nID: " + jogador.getFacebook_id());

                if (jogadorService.findByFacebookId(jogador.getFacebook_id()) != null) {
                    System.out.println("JOGADOR FOI RECUPERADO DO BANCO DE DADOS");
                    jogador.setDataUltimoLogin(new Date());
                    jogadorService.update(jogador);
                } else {
                    jogador.setDataCriacao(new Date());
                    jogador.setLoginTipo(LoginEnum.FACEBOOK);
                    jogadorService.save(jogador);
                }

                mensagemInfo("Login", "O login foi realizado com sucesso.");
                jogadorEstaLogado = true;
                partidaAssociativa.setJogador(jogador);
            } catch (Exception ex) {
                jogador = retornaJogadorLogado();
                if (jogador != null) {
                    jogadorEstaLogado = true;
                    partidaAssociativa.setJogador(jogador);
                }
            }
        }
    }

    public Jogador retornaJogadorLogado() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest httpServletRequest = (HttpServletRequest) context.getExternalContext().getRequest();
        return (Jogador) httpServletRequest.getSession(true).getAttribute("jogador");
    }
    
    //GETTERS AND SETTERS

    public Resposta getResposta() {
        return resposta;
    }

    public void setResposta(Resposta resposta) {
        this.resposta = resposta;
    }

    public List<Pergunta> getListaDePerguntas() {
        return listaDePerguntas;
    }

    public void setListaDePerguntas(List<Pergunta> listaDePerguntas) {
        this.listaDePerguntas = listaDePerguntas;
    }

    public AlternativaDataModel getListaAlternativasPorPergunta() {
        return listaAlternativasPorPergunta;
    }

    public void setListaAlternativasPorPergunta(AlternativaDataModel listaAlternativasPorPergunta) {
        this.listaAlternativasPorPergunta = listaAlternativasPorPergunta;
    }

    public int getPosicaoPergunta() {
        return posicaoPergunta;
    }

    public int getAcertos() {
        return acertos;
    }

    public int getErros() {
        return erros;
    }

    public int getColocacao() {
        return colocacao;
    }

    public Pergunta getPerguntaAtual() {
        return perguntaAtual;
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

    public boolean isJogadorEstaLogado() {
        return jogadorEstaLogado;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    public JogadorPartidaDataModel getListaJogadoresPorPartida() {
        return listaJogadoresPorPartida;
    }

    public void setListaJogadoresPorPartida(JogadorPartidaDataModel listaJogadoresPorPartida) {
        this.listaJogadoresPorPartida = listaJogadoresPorPartida;
    }
    
}
