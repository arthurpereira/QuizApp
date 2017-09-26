package br.com.multitela.quiz.servidor.bean;

import br.com.multitela.quiz.servidor.entity.Jogador;
import br.com.multitela.quiz.servidor.entity.JogadorPartidaAssociativa;
import br.com.multitela.quiz.servidor.entity.Questionario;
import br.com.multitela.quiz.servidor.enums.QuestionarioAvaliacaoEnum;
import br.com.multitela.quiz.servidor.enums.QuestionarioExperienciaEnum;
import br.com.multitela.quiz.servidor.enums.QuestionarioLikertEnum;
import br.com.multitela.quiz.servidor.enums.QuestionarioSatisfacaoEnum;
import br.com.multitela.quiz.servidor.service.QuestionarioService;
import eu.bitwalker.useragentutils.UserAgent;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RateEvent;
import ua_parser.Client;
import ua_parser.Parser;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by arthurpereira on 1/13/17.
 */
@Named
@ViewScoped
public class QuestionarioBean extends AbstractBean {

    @EJB
    private QuestionarioService questionarioService;

    private Questionario questionario;
    private Date data1, data2;
    private List<Questionario> questionariosBuscados;

    private Integer avaliacao1, avaliacao2, avaliacao3, avaliacao4, avaliacao5;

    public QuestionarioBean() {
        questionario = new Questionario();
    }

    public void cadastrar() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("user-agent"));

            Parser parser = new Parser();
            Client client = parser.parse(request.getHeader("user-agent"));

            questionario.setNavegadorNome(userAgent.getBrowser().getName());
            questionario.setNavegadorVersao(userAgent.getBrowserVersion().toString());
            questionario.setSistemaOperacional(userAgent.getOperatingSystem().getName());
            questionario.setSistemaOperacionalVersao(client.os.major + "." + client.os.minor);
            questionario.setDispositivo(userAgent.getOperatingSystem().getDeviceType().getName());
            questionario.setFabricante(userAgent.getOperatingSystem().getManufacturer().getName());
            questionario.setJogadorPartida(retornaJogadorPartida());
            questionario.setData(new Date());
            questionarioService.save(questionario);
            closeDialog();
            RequestContext.getCurrentInstance().execute("agradecimento();");
            context.getExternalContext().getFlash().setKeepMessages(true);
        } catch (Exception ex) {
            keepDialogOpen();
            mensagemAtencao("Atenção", "O questionário não foi respondido.");
            ex.printStackTrace();
        } finally {
            HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            httpSession.invalidate();
        }
    }

    /**
     * Realiza a busca de questionarios respondidos no sistema.
     */
    public void buscar() {
        try {
            questionariosBuscados = questionarioService.consultaPorData(data1, data2);

            if (questionariosBuscados.isEmpty())
                mensagemInfo("Nenhum Questionário foi encontrado.");
        } catch (Exception ex) {
            keepDialogOpen();
            mensagemAtencao("Não foi possível realizar a busca. Tente novamente mais tarde.");
            ex.printStackTrace();
        }
    }

    private JogadorPartidaAssociativa retornaJogadorPartida() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest httpServletRequest = (HttpServletRequest) context.getExternalContext().getRequest();
        return (JogadorPartidaAssociativa) httpServletRequest.getSession(true).getAttribute("jogador-partida");
    }

    public void onRateAvaliacao1(RateEvent rateEvent) {
        questionario.setQuizSatisfacao(
                new ArrayList<>(Arrays.asList(QuestionarioSatisfacaoEnum.values()))
                        .get(((Integer) rateEvent.getRating()) - 1).getTexto());
    }

    public void onRateAvaliacao2(RateEvent rateEvent) {
        questionario.setQuizExperiencia(
                new ArrayList<>(Arrays.asList(QuestionarioExperienciaEnum.values()))
                        .get(((Integer) rateEvent.getRating()) - 1).getTexto());
    }

    public void onRateAvaliacao3(RateEvent rateEvent) {
        questionario.setQuizInterface(
                new ArrayList<>(Arrays.asList(QuestionarioAvaliacaoEnum.values()))
                        .get(((Integer) rateEvent.getRating()) - 1).getTexto());
    }

    public void onRateAvaliacao4(RateEvent rateEvent) {
        questionario.setQuizAjudouEnsino(
                new ArrayList<>(Arrays.asList(QuestionarioLikertEnum.values()))
                        .get(((Integer) rateEvent.getRating()) - 1).getTexto());
    }

    // GETTERS AND SETTES

    public Questionario getQuestionario() {
        return questionario;
    }

    public void setQuestionario(Questionario questionario) {
        this.questionario = questionario;
    }

    public Date getData1() {
        return data1;
    }

    public void setData1(Date data1) {
        this.data1 = data1;
    }

    public Date getData2() {
        return data2;
    }

    public void setData2(Date data2) {
        this.data2 = data2;
    }

    public List<Questionario> getQuestionariosBuscados() {
        return questionariosBuscados;
    }

    public Integer getAvaliacao1() {
        return avaliacao1;
    }

    public void setAvaliacao1(Integer avaliacao1) {
        this.avaliacao1 = avaliacao1;
    }

    public Integer getAvaliacao2() {
        return avaliacao2;
    }

    public void setAvaliacao2(Integer avaliacao2) {
        this.avaliacao2 = avaliacao2;
    }

    public Integer getAvaliacao3() {
        return avaliacao3;
    }

    public void setAvaliacao3(Integer avaliacao3) {
        this.avaliacao3 = avaliacao3;
    }

    public Integer getAvaliacao4() {
        return avaliacao4;
    }

    public void setAvaliacao4(Integer avaliacao4) {
        this.avaliacao4 = avaliacao4;
    }

    public Integer getAvaliacao5() {
        return avaliacao5;
    }

    public void setAvaliacao5(Integer avaliacao5) {
        this.avaliacao5 = avaliacao5;
    }
}
