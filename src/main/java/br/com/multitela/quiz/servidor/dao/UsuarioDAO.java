/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.multitela.quiz.servidor.dao;

import br.com.multitela.quiz.servidor.entity.Usuario;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author arthurpereira
 */
public class UsuarioDAO extends BaseDAO<Usuario> {

    /**
     * Realiza a busca de um usuário único pelo seu nome de usuário.
     *
     * @param nomeDeUsuario
     * @return
     */
    public Usuario buscarUsuario(String nomeDeUsuario) {
        Session session = getEntityManager().unwrap(Session.class);
        Criteria criteria = session.createCriteria(Usuario.class);

        if (StringUtils.isNotBlank(nomeDeUsuario)) {
            criteria.add(Restrictions.eq("usuario", nomeDeUsuario));
        }

        return (Usuario) criteria.uniqueResult();
    }

}
