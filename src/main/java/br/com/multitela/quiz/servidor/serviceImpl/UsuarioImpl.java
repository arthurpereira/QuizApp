package br.com.multitela.quiz.servidor.serviceImpl;

import br.com.multitela.quiz.servidor.entity.Usuario;
import br.com.multitela.quiz.servidor.repository.RepositoryImpl;
import br.com.multitela.quiz.servidor.service.UsuarioService;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.DigestUtils;

import javax.ejb.Stateless;

/**
 * Created by arthurpereira on 12/6/16.
 */
@Stateless
public class UsuarioImpl extends RepositoryImpl<Usuario> implements UsuarioService {

    /**
     * Realiza a busca de um usuário único pelo seu nome de usuário.
     *
     * @param username
     * @return
     */
    @Override
    public Usuario buscarPorUsername(String username) {
        Session session = getEntityManager().unwrap(Session.class);
        Criteria criteria = session.createCriteria(Usuario.class);

        if (!username.isEmpty()) {
            criteria.add(Restrictions.eq("usuario", username));
        }

        return (Usuario) criteria.uniqueResult();
    }

    /**
     * Método que realiza a validação do usuário no banco de dados durante o
     * login.
     *
     * @param username
     * @param senha
     * @return
     */
    @Override
    public Usuario validarUsuarioESenha(String username, String senha) {
        Usuario usuario = this.buscarPorUsername(username);

        if (usuario == null || username == null || !usuario.getSenha().equals(DigestUtils.md5DigestAsHex(senha.getBytes())))
            return null;
        else
            return usuario;
    }
}
