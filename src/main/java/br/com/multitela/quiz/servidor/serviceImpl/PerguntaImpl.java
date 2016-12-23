package br.com.multitela.quiz.servidor.serviceImpl;

import br.com.multitela.quiz.servidor.entity.Pergunta;
import br.com.multitela.quiz.servidor.repository.RepositoryImpl;
import br.com.multitela.quiz.servidor.service.PerguntaService;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by arthurpereira on 12/2/16.
 */
@Stateless
public class PerguntaImpl extends RepositoryImpl<Pergunta> implements PerguntaService {

    /**
     * Busca pergunta do banco de dados retornando uma lista para as tabelas de
     * busca.
     *
     * @param busca
     * @return
     */
    @Override
    public List<Pergunta> buscar(String busca) {
        TypedQuery<Pergunta> query = getEntityManager().createQuery(
                "SELECT pergunta FROM " + Pergunta.class.getName()
                + " pergunta WHERE pergunta.texto LIKE :busca", Pergunta.class);
        query.setParameter("busca", "%" + busca + "%");

        return query.getResultList();
    }

}
