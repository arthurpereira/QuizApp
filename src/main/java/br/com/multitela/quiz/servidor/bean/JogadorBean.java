package br.com.multitela.quiz.servidor.bean;

import br.com.multitela.quiz.servidor.entity.Jogador;
import br.com.multitela.quiz.servidor.enums.LoginEnum;
import br.com.multitela.quiz.servidor.service.JogadorService;
import br.com.multitela.quiz.servidor.util.StringUtil;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by arthurpereira on 1/13/17.
 */
@Named
@ViewScoped
public class JogadorBean extends AbstractBean {

    @EJB
    private JogadorService jogadorService;

    private Jogador jogador;

    private String nome;
    private String sobrenome;
    private Long matricula;

    public JogadorBean() {
        jogador = new Jogador();
    }

    public String logar() {
        jogador = jogadorService.findByMatricula(matricula);
        try {
            if (jogador == null) {
                jogador = new Jogador();
                jogador.setNome(StringUtil.formatName(nome));
                jogador.setSobrenome(StringUtil.formatName(sobrenome));
                jogador.setMatricula(matricula);
                jogador.setLoginTipo(LoginEnum.SEM_FACEBOOK);
                jogador.setDataCriacao(new Date());
                jogadorService.save(jogador);
            }
            jogador.setDataUltimoLogin(new Date());
            jogadorService.update(jogador);
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            request.getSession().setAttribute("jogador", jogador);
            context.getExternalContext().getFlash().setKeepMessages(true);
            mensagemInfo("Sucesso!", "Login realizado com sucesso.");

            return "/jogo/index.xhtml?faces-redirect=true";
        } catch (Exception ex) {
            ex.printStackTrace();
            mensagemErro("Não foi possível realizar o login.");
            return null;
        }
    }

    public void validaMatricula(FacesContext context, UIComponent component, String matricula) {
        List<FacesMessage> msgs = new ArrayList<>();
        if (matricula.length() < 1) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro.",
                    "Número de matrícula inválido");
            msgs.add(message);
        }
        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }
    }

    public Jogador retornaJogadorLogado() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest httpServletRequest = (HttpServletRequest) context.getExternalContext().getRequest();
        return (Jogador) httpServletRequest.getSession(true).getAttribute("jogador");
    }

    // GETTERS AND SETTERS

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

    public Long getMatricula() {
        return matricula;
    }

    public void setMatricula(Long matricula) {
        this.matricula = matricula;
    }
}
