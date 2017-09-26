package br.com.multitela.quiz.servidor.serviceImpl;

import br.com.multitela.quiz.servidor.entity.Jogador;
import br.com.multitela.quiz.servidor.repository.RepositoryImpl;
import br.com.multitela.quiz.servidor.service.JogadorService;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * Created by arthurpereira on 12/2/16.
 */
@Stateless
public class JogadorImpl extends RepositoryImpl<Jogador> implements JogadorService {

    /**
     * Busca um jogador do banco de dados onde o id recebido como
     * parâmetro é igual ao id do Facebook da tabela jogador.
     *
     * @param id
     * @return
     */
    @Override
    public Jogador findByFacebookId(Long id) {
        try {
            TypedQuery<Jogador> query = getEntityManager().createQuery(
                    "SELECT jogador FROM " + Jogador.class.getName()
                            + " jogador WHERE jogador.facebook_id = :id", Jogador.class);
            query.setParameter("id", id);

            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public Jogador findByMatricula(Long matricula) {
        try {
            TypedQuery<Jogador> query = getEntityManager().createQuery(
            "SELECT jogador FROM " + Jogador.class.getName()
                    + " jogador WHERE jogador.matricula = :matricula", Jogador.class);
            query.setParameter("matricula", matricula);

            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

}
