/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.multitela.quiz.servidor.persistence;

import br.com.multitela.quiz.servidor.dao.UsuarioDAO;
import br.com.multitela.quiz.servidor.entity.Usuario;

/**
 *
 * @author arthurpereira
 */
public class CadastraUsuario {

    public static void main(String args[]) {

        UsuarioDAO dao = new UsuarioDAO();
        
        Usuario user = new Usuario();
        
        user.setNome("Administrador");
        user.setUsuario("admin");
        user.setSenha("123456");

        dao.startOperation();
        dao.save(user);
        dao.stopOperation(true);
    }
}
