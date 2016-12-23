/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.multitela.quiz.servidor.controller;

import br.com.multitela.quiz.servidor.dao.UsuarioDAO;
import br.com.multitela.quiz.servidor.entity.Usuario;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author arthurpereira
 */
public class UsuarioController implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Cadastra um novo usuario no banco de dados.
     *
     * @param usuario
     */
    public void cadastrar(Usuario usuario) {
        usuarioDAO.startOperation();
        usuarioDAO.save(usuario);
        usuarioDAO.stopOperation(true);
    }

    /**
     * Atualiza/edita um usuario previamente cadastrado no banco de dados.
     *
     * @param usuario
     */
    public void atualizar(Usuario usuario) {
        usuarioDAO.startOperation();
        usuarioDAO.update(usuario);
        usuarioDAO.stopOperation(true);
    }

    /**
     * Método que realiza a validação do usuário no banco de dados durante o
     * login.
     *
     * @param nomeDeUsuario
     * @param senha
     * @return
     */
    public Usuario validarUsuario(String nomeDeUsuario, String senha) {
        usuarioDAO.startOperation();
        Usuario usuarioValido = usuarioDAO.buscarUsuario(nomeDeUsuario);
        usuarioDAO.stopOperation(false);
        if (usuarioValido == null || !usuarioValido.getSenha().equals(senha)) {
            return null;
        } else {
            return usuarioValido;
        }
    }

    /**
     * Realiza a comunicação com o método que faz a busca de um usuario pelo seu
     * id.
     *
     * @param id
     * @return
     */
    public Usuario find(Long id) {
        usuarioDAO.startOperation();
        Usuario resultado = usuarioDAO.find(Usuario.class, id);
        usuarioDAO.stopOperation(false);
        return resultado;
    }
    
    /**
     * Realiza a comunicação com o método que faz a busca de todos os usuarios.
     *
     * @return
     */
    public List<Usuario> findAll() {
        usuarioDAO.startOperation();
        List<Usuario> resultado = usuarioDAO.findAll(Usuario.class);
        usuarioDAO.stopOperation(false);
        return resultado;
    }
    
}
