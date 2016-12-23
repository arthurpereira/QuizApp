/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.multitela.quiz.servidor.bean;

import br.com.multitela.quiz.servidor.controller.UsuarioController;
import br.com.multitela.quiz.servidor.entity.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import br.com.multitela.quiz.servidor.service.UsuarioService;
import org.primefaces.component.inputtext.InputText;

/**
 *
 * @author arthurpereira
 */
@Named
@ViewScoped
public class UsuarioBean extends AbstractBean {

    @EJB
    private UsuarioService usuarioService;
    private UsuarioController usuarioController;

    private Usuario usuarioLogado, usuario;
    private String nomeDeUsuario, senha, novaSenha, confirmaSenha;
    private List<Usuario> usuariosCadastrados;
    
    private InputText campoUsuario;

    public UsuarioBean() {
        usuario = new Usuario();
    }

    /**
     * Realiza a autenticação do usuário no sistema.
     *
     * @return String
     */
    public String logar() {
        FacesContext context = FacesContext.getCurrentInstance();
        Usuario usuarioValido = usuarioService.validarUsuarioESenha(nomeDeUsuario, senha);
        closeDialog();

        if (usuarioValido != null) {
            usuarioLogado = usuarioValido;
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            request.getSession().setAttribute("usuario-logado", usuarioLogado);
            context.getExternalContext().getFlash().setKeepMessages(true);
            context.addMessage("sucesso", new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Sucesso!", "Usuário " + usuarioLogado.getNome() + " logado com sucesso."));
            return "/admin/index.xhtml?faces-redirect=true";
        }

        mensagemErro("Usuário ou Senha inválidos.");

        return null;
    }

    /**
     * Faz a validação da variável usuário no sistema.
     *
     * @param context
     * @param component
     * @param usuario
     */
    public void validaUsuario(FacesContext context, UIComponent component, String usuario) {
        List<FacesMessage> msgs = new ArrayList<>();
        if (usuario.length() < 5) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "O nome de usuário não pode ter menos de 5 caracteres.",
                    "O nome de usuário não pode ter menos de 5 caracteres.");
            msgs.add(message);
        }
        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }
    }

    /**
     * Faz a validação da variável senha no sistema.
     *
     * @param context
     * @param component
     * @param senha
     */
    public void validaSenha(FacesContext context, UIComponent component, String senha) {
        List<FacesMessage> msgs = new ArrayList<>();
        if (senha.length() < 6) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "A senha não pode ter menos de 6 caracteres.",
                    "A senha não pode ter menos de 6 caracteres.");
            msgs.add(message);
        }
        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }
    }

    /**
     * Realiza a desconexão do usuário do sistema.
     *
     * @return String
     */
    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage("sucesso", new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!",
                "Você foi desconectado com sucesso."));
        ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
                .getRequest()).getSession().invalidate();
        closeDialog();
        return "/admin/login.xhtml?faces-redirect=true";
    }

    /**
     * Cadastra um novo usuário no sistema.
     *
     * @return String
     */
    public String cadastrar() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Usuario usuarioSeguro = usuarioService.find(retornaUsuarioLogado().getId());

            if (senha.equals(usuarioSeguro.getSenha())) {
                usuariosCadastrados = usuarioController.findAll();
                
                boolean eUsuarioUnico = true;
                
                for (Usuario usuarioCadastrado : usuariosCadastrados) {
                    if (usuario.getUsuario().equalsIgnoreCase(usuarioCadastrado.getUsuario())) {
                        eUsuarioUnico = false;
                        break;
                    }
                }
                
                if (eUsuarioUnico) {
                    usuarioService.save(usuario);
                    closeDialog();
                    context.getExternalContext().getFlash().setKeepMessages(true);
                    mensagemSucesso("Usuário \"" + usuario.getNome() + "\" foi cadastrado.");
                    limpaUsuario();
                    return "/admin/index.xhtml?faces-redirect=true";
                } else {
                    mensagemAtencao("Já existe um usuário cadastrado com este nome de usuário.");
                    campoUsuario.setValid(false);
                    return null;
                }
            } else {
                context.getExternalContext().getFlash().setKeepMessages(true);
                mensagemAtencao("A senha de Administrador não confere.");
                limpaUsuario();
                return "/admin/cadastro_usuario.xhtml?faces-redirect=true";
            }
        } catch (Exception e) {
            keepDialogOpen();
            mensagemAtencao("Não foi possível cadastrar o usuário. Tente novamente mais tarde.");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Atualiza/edita um usuário previamente cadastrado no sistema.
     *
     * @return String
     */
    public String atualizar() {
        FacesContext context = FacesContext.getCurrentInstance();
        Usuario usuarioLogadoSeguro = new Usuario(); 
        try {
            Usuario usuarioSeguro = usuarioService.find(retornaUsuarioLogado().getId());
            usuarioLogadoSeguro = org.apache.commons.lang3.SerializationUtils.clone( usuarioSeguro );

            if (senha.equals(usuarioSeguro.getSenha())) {
                
                usuariosCadastrados = usuarioController.findAll();
                
                boolean eUsuarioUnico = true;
                
                for (Usuario usuarioCadastrado : usuariosCadastrados) {
                    if (usuarioLogado.getUsuario().equalsIgnoreCase(usuarioCadastrado.getUsuario())
                            && usuarioLogado.getId() != usuarioCadastrado.getId()) {
                        eUsuarioUnico = false;
                        break;
                    }
                }
                
                if (eUsuarioUnico) {
                    usuarioSeguro.setNome(usuarioLogado.getNome());
                    usuarioSeguro.setUsuario(usuarioLogado.getUsuario());
                    usuarioSeguro.setSenha(usuarioLogado.getSenha());
                    usuarioService.update(usuarioSeguro);
                    closeDialog();
                    context.getExternalContext().getFlash().setKeepMessages(true);
                    mensagemSucesso("Usuário \"" + usuarioSeguro.getNome() + "\" foi editado.");
                    HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
                    request.getSession().setAttribute("usuario-logado", usuarioSeguro);
                    limpaUsuario();
                    return "/admin/index.xhtml?faces-redirect=true";
                } else {
                    mensagemAtencao("Já existe um usuário cadastrado com este nome de usuário.");
                    campoUsuario.setValid(false);
                    HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
                    request.getSession().setAttribute("usuario-logado", usuarioLogadoSeguro);
                    return null;
                }
            } else {
                context.getExternalContext().getFlash().setKeepMessages(true);
                mensagemAtencao("A senha atual não confere. Tente novamente.");
                limpaUsuario();
                return "/admin/minha_conta.xhtml?faces-redirect=true";
            }
        } catch (Exception e) {
            mensagemAtencao("Usuário \"" + usuarioLogado.getNome() + "\" não foi editado. Tente novamente mais tarde.");
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            request.getSession().setAttribute("usuario-logado", usuarioLogadoSeguro);
            e.printStackTrace();
            closeDialog();
            return null;
        }        
    }

    /**
     * Instancia novos objetos e anula as principais variáveis de navegação.
     */
    public void limpaUsuario() {
        usuario = new Usuario();
        usuarioController = new UsuarioController();
        nomeDeUsuario = senha = novaSenha = confirmaSenha = null;
    }

    /**
     * Método que retorna o usuário logado no sistema.
     *
     * @return
     */
    public Usuario retornaUsuarioLogado() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest httpServletRequest = (HttpServletRequest) context.getExternalContext().getRequest();
        usuarioLogado = (Usuario) httpServletRequest.getSession(true).getAttribute("usuario-logado");
        return usuarioLogado;
    }

    //GETTERS AND SETTERS
    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNomeDeUsuario() {
        return nomeDeUsuario;
    }

    public void setNomeDeUsuario(String nomeDeUsuario) {
        this.nomeDeUsuario = nomeDeUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getConfirmaSenha() {
        return confirmaSenha;
    }

    public void setConfirmaSenha(String confirmaSenha) {
        this.confirmaSenha = confirmaSenha;
    }

    public InputText getCampoUsuario() {
        return campoUsuario;
    }

    public void setCampoUsuario(InputText campoUsuario) {
        this.campoUsuario = campoUsuario;
    }
    
}