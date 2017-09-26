package br.com.multitela.quiz.servidor.security;

import br.com.multitela.quiz.servidor.entity.Jogador;
import br.com.multitela.quiz.servidor.enums.LoginEnum;
import br.com.multitela.quiz.servidor.service.JogadorService;
import br.com.multitela.quiz.servidor.util.MessagesUtil;
import br.com.multitela.quiz.servidor.util.StringUtil;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by arthurpereira on 06/04/17.
 */
@Named
@RequestScoped
public class Autenticacao implements Serializable, AuthenticationProvider {

    private static final long serialVersionUID = 1L;

    private String nome;
    private String sobrenome;
    private Long matricula;

    @EJB
    private JogadorService jogadorService;

    @Named
    @Produces
    public Jogador getJogadorAutenticado() {
        try {
            return (Jogador) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException ex) {
            return null;
        }
    }

    public void login() {
        try {
            this.authenticate(new UsernamePasswordAuthenticationToken(matricula, null, null));
        } catch (AuthenticationException ex) {
            MessagesUtil.mostrarMensagemAtencao(ex.getMessage());
        }
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            Jogador jogador = jogadorService.findByMatricula(Long.parseLong(authentication.getName()));

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

            List<GrantedAuthority> AUTHORITIES = new ArrayList<>();
            AUTHORITIES.add(new SimpleGrantedAuthority("jogador"));

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    jogador, authentication.getCredentials(), AUTHORITIES);
            authToken.setDetails(jogador);

            SecurityContextHolder.createEmptyContext();
            SecurityContextHolder.getContext().setAuthentication(authToken);

            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(externalContext.getRequestContextPath() + "/jogo/");

            MessagesUtil.mostrarMensagemSucesso("Login realizado com sucesso.");

            return authToken;

        } catch (Exception ex) {
            throw new AuthenticationServiceException(ex.getMessage());
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
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
