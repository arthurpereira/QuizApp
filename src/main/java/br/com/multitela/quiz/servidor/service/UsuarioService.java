package br.com.multitela.quiz.servidor.service;

import br.com.multitela.quiz.servidor.entity.Usuario;
import br.com.multitela.quiz.servidor.repository.Repository;

/**
 * Created by arthurpereira on 12/6/16.
 */
public interface UsuarioService extends Repository<Usuario> {

    public Usuario buscarPorUsername(String username);

    public Usuario validarUsuarioESenha(String username, String senha);

}
