/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.multitela.quiz.servidor.bean;

import br.com.multitela.quiz.servidor.entity.Alternativa;
import br.com.multitela.quiz.servidor.entity.Imagem;
import br.com.multitela.quiz.servidor.entity.Pergunta;
import br.com.multitela.quiz.servidor.exception.DiretorioNaoEncontradoException;
import br.com.multitela.quiz.servidor.exception.FormatoDeArquivoInvalidoException;
import br.com.multitela.quiz.servidor.service.PerguntaService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.Part;

/**
 *
 * @author arthurpereira
 */
@Named
@ViewScoped
public class PerguntaBean extends AbstractBean {

    @EJB
    private PerguntaService perguntaService;

    private Pergunta pergunta;
    private Alternativa alt1, alt2, alt3, alt4, alt5;
    private String busca;
    private List<Pergunta> perguntasBuscadas;

    private Imagem imagem;
    private Part arquivo;
    
    public PerguntaBean() {
        pergunta = new Pergunta();
        alt1 = new Alternativa(pergunta);
        alt2 = new Alternativa(pergunta);
        alt3 = new Alternativa(pergunta);
        alt4 = new Alternativa(pergunta);
        alt5 = new Alternativa(pergunta);
    }
    
    /**
     * Cadastra uma nova pergunta no sistema.
     *
     * @return String
     */
    public String cadastrar() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            pergunta.setAlternativas(new ArrayList<>());
            pergunta.getAlternativas().add(alt1);
            pergunta.getAlternativas().add(alt2);
            pergunta.getAlternativas().add(alt3);
            pergunta.getAlternativas().add(alt4);
            pergunta.getAlternativas().add(alt5);

            if (arquivo != null) {
                enviarArquivo();
            }

            perguntaService.save(pergunta);
            closeDialog();
            context.getExternalContext().getFlash().setKeepMessages(true);
            mensagemSucesso("A pergunta foi cadastrada.");
            limpaPergunta();
            return "/admin/index.xhtml?faces-redirect=true";
        } catch (IOException ex) {
            keepDialogOpen();
            mensagemAtencao("A pergunta não foi cadastrada. Tente novamente mais tarde.");
            ex.printStackTrace();
            return null;
        } catch (DiretorioNaoEncontradoException ex) {
            keepDialogOpen();
            mensagemAtencao("A pergunta não foi cadastrada. Tente novamente mais tarde.");
            ex.printStackTrace();
            return null;
        } catch (FormatoDeArquivoInvalidoException e) {
            keepDialogOpen();
            mensagemAtencao("O fomato de imagem enviado é inválido ou não permitido.");
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            keepDialogOpen();
            mensagemAtencao("A pergunta não foi cadastrada. Tente novamente mais tarde.");
            e.printStackTrace();
            return null;
        }
    }

    private void enviarArquivo() throws IOException, DiretorioNaoEncontradoException, FormatoDeArquivoInvalidoException {
        InputStream inputStream = arquivo.getInputStream();
        String tipo = arquivo.getContentType();

        pergunta.salvarImagem(inputStream, tipo);

        inputStream.close();
    }
    
    /**
     * Atualiza/edita uma pergunta previamente cadastrada no sistema.
     *
     * @return String
     */
    public String atualizar() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (arquivo != null) {
                if (pergunta.getImagem() != null) {
                    pergunta.removeImagem();
                }
                enviarArquivo();
            }
            perguntaService.update(pergunta);
            closeDialog();
            context.getExternalContext().getFlash().setKeepMessages(true);
            mensagemSucesso("A Pergunta foi editada.");
            limpaPergunta();
            return "/admin/index.xhtml?faces-redirect=true";
        } catch (Exception e) {
            keepDialogOpen();
            mensagemAtencao("A Pergunta não foi editada. Tente novamente mais tarde.");
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Busca uma pergunta previamente cadastrada no sistema.
     */
    public void buscar() {
        try {
            perguntasBuscadas = perguntaService.buscar(busca);
            if (perguntasBuscadas.isEmpty()) {
                mensagemInfo("Nenhuma Pergunta foi encontrada.");
            }
        } catch (Exception e) {
            keepDialogOpen();
            mensagemAtencao("Não foi possível realizar a busca. Tente novamente mais tarde.");
            e.printStackTrace();
        }
    }
    
    /**
     * Instancia novos objetos e anula as principais variáveis de navegação.
     */
    public void limpaPergunta() {
        pergunta = new Pergunta();
        alt1 = new Alternativa(pergunta);
        alt2 = new Alternativa(pergunta);
        alt3 = new Alternativa(pergunta);
        alt4 = new Alternativa(pergunta);
        alt5 = new Alternativa(pergunta);
        imagem = null;
        arquivo = null;
    }
    
    //GETTERS AND SETTERS

    public Pergunta getPergunta() {
        return pergunta;
    }

    public void setPergunta(Pergunta pergunta) {
        this.pergunta = pergunta;
    }

    public Alternativa getAlt1() {
        return alt1;
    }

    public void setAlt1(Alternativa alt1) {
        this.alt1 = alt1;
    }

    public Alternativa getAlt2() {
        return alt2;
    }

    public void setAlt2(Alternativa alt2) {
        this.alt2 = alt2;
    }

    public Alternativa getAlt3() {
        return alt3;
    }

    public void setAlt3(Alternativa alt3) {
        this.alt3 = alt3;
    }

    public Alternativa getAlt4() {
        return alt4;
    }

    public void setAlt4(Alternativa alt4) {
        this.alt4 = alt4;
    }

    public Alternativa getAlt5() {
        return alt5;
    }

    public void setAlt5(Alternativa alt5) {
        this.alt5 = alt5;
    }

    public String getBusca() {
        return busca;
    }

    public void setBusca(String busca) {
        this.busca = busca;
    }

    public List<Pergunta> getPerguntasBuscadas() {
        return perguntasBuscadas;
    }

    public Imagem getImagem() {
        return imagem;
    }

    public void setImagem(Imagem imagem) {
        this.imagem = imagem;
    }

    public Part getArquivo() {
        return arquivo;
    }

    public void setArquivo(Part arquivo) {
        this.arquivo = arquivo;
    }
}
