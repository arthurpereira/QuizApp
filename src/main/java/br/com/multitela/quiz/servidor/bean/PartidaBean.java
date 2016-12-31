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
import org.primefaces.context.RequestContext;

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
    
    private List<Integer> listaColocacaoPlacar;
    
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
        listaAlternativasPorPergunta = new AlternativaDataModel(listaDePerguntas.get(posicaoPergunta).getAlternativa());
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
        ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
                .getRequest()).getSession().invalidate();
    }
    
    public void selecionaAlternativa(int indice) {
        posicaoAlternativaEscolhida = indice;
    }
    
    public void confirmaResposta() {
        if (perguntaStatus != PerguntaStatus.RESPONDIDA) {
            resposta = new Resposta();
            resposta.setPergunta(perguntaAtual);
            resposta.setAlternativaEscolhida(posicaoAlternativaEscolhida);
            perguntaStatus = PerguntaStatus.RESPONDIDA;
            listaStatusPorAlternativa.set(posicaoAlternativaEscolhida, AlternativaStatus.ESCOLHIDA);
            salvarResposta();
        }
    }
    
    public void mostrarRespostaCerta() {
        RequestContext.getCurrentInstance().execute("PF('dialog-confirma-resposta').hide();");
        if (perguntaStatus == PerguntaStatus.RESPONDIDA) {
            if (resposta.getAlternativaEscolhida() == perguntaAtual.getAlternativa_certa()) {
                listaStatusPorAlternativa.set(resposta.getAlternativaEscolhida(), AlternativaStatus.CERTA);
                FacesContext.getCurrentInstance().addMessage("default", new FacesMessage("Você Acertou!", "Essa é a resposta certa."));
            } else {
                listaStatusPorAlternativa.set(perguntaAtual.getAlternativa_certa(), AlternativaStatus.CERTA);
                listaStatusPorAlternativa.set(resposta.getAlternativaEscolhida(), AlternativaStatus.ERRADA);
                FacesContext.getCurrentInstance().addMessage("default", new FacesMessage("Você Errou", "Que pena..."));
            }
        } else {
            listaStatusPorAlternativa.set(perguntaAtual.getAlternativa_certa(), AlternativaStatus.CERTA);
            FacesContext.getCurrentInstance().addMessage("default", new FacesMessage("Você Errou", "Que pena..."));
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
            if (resposta.getAlternativaEscolhida() == perguntaAtual.getAlternativa_certa())
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
    }
    
    @Override
    public void atualizarPlacar(List<JogadorPartidaAssociativa> placar, List<Integer> pontuacoes) {
        jogadoresPorPartida = placar;
        listaColocacaoPlacar = new ArrayList<>();

        if (!jogadoresPorPartida.isEmpty()) {
            for (JogadorPartidaAssociativa jogadorPartida : jogadoresPorPartida) {
                colocacao = pontuacoes.indexOf(jogadorPartida.getAcertos()) + 1;
                listaColocacaoPlacar.add(colocacao);
            }
        }
        listaJogadoresPorPartida = new JogadorPartidaDataModel(jogadoresPorPartida);
        this.colocacao = pontuacoes.indexOf(acertos) + 1;
    }
    
    @Override
    public void atualizarPerguntas(List<Pergunta> perguntas) {
        listaDePerguntas = perguntas;
    }
    
    public int getColocacao(int posicao) {
        return listaColocacaoPlacar.get(posicao);
    }
    
    public int retornaNumeroDeJogadores() {
        return jogadoresPorPartida.size();
    }
    
    public String alternativaEscolhida() {
        if (perguntaAtual != null)
            return perguntaAtual.getAlternativa().get(posicaoAlternativaEscolhida).getTexto();
        else
            return "";
    }
    
    public String retornaLetraAlternativa(int indice) {
        return String.valueOf( (char) ( indice + 65 ) );
    }
    
    public void habilitarAlternativas() {
        perguntaStatus = PerguntaStatus.RESPOSTA_HABILITADA;
        limpaStatusPorAlternativa();
    }
    
    public void mudarPergunta() {
        if (posicaoPergunta < listaDePerguntas.size() - 1) {
            perguntaAtual = listaDePerguntas.get(++posicaoPergunta);
            listaAlternativasPorPergunta = new AlternativaDataModel(listaDePerguntas.get(posicaoPergunta).getAlternativa());
            RequestContext.getCurrentInstance().execute("jogo();");
        } else {
            RequestContext.getCurrentInstance().execute("fimdojogo();");
            encerrarPartida();
        }
        perguntaStatus = PerguntaStatus.RESPOSTA_DESABILITADA;
        limpaStatusPorAlternativa();
    }
    
    private void limpaStatusPorAlternativa() {
        listaStatusPorAlternativa = new ArrayList<>();
        for (int i = 0; i < perguntaAtual.getAlternativa().size(); i++) {
            if (perguntaStatus != PerguntaStatus.RESPOSTA_DESABILITADA)
                listaStatusPorAlternativa.add(i, AlternativaStatus.NAO_ESCOLHIDA);
            else
                listaStatusPorAlternativa.add(i, AlternativaStatus.DESABILITADA);
        }
    }
    
    public String retornaBackgroundColorAlternativa(int posicao) {
        return "background-color: " + listaStatusPorAlternativa.get(posicao).getCor() + ";";
    }
    
    public void logarComFacebook() {
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
                    jogadorService.save(jogador);
                }

                FacesContext.getCurrentInstance().addMessage("default",
                        new FacesMessage("Login", "O login foi realizado com sucesso."));
                jogadorEstaLogado = true;
                partidaAssociativa.setJogador(jogador);
            } catch (NumberFormatException ex) {
                RequestContext.getCurrentInstance().execute("registerWithServer();");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

    public void setPerguntaAtual(Pergunta perguntaAtual) {
        this.perguntaAtual = perguntaAtual;
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
