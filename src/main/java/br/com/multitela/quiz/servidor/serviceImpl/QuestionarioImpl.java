package br.com.multitela.quiz.servidor.serviceImpl;

import br.com.multitela.quiz.servidor.entity.Questionario;
import br.com.multitela.quiz.servidor.repository.RepositoryImpl;
import br.com.multitela.quiz.servidor.service.QuestionarioService;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by arthurpereira on 1/13/17.
 */
@Stateless
public class QuestionarioImpl extends RepositoryImpl<Questionario> implements QuestionarioService {

    /**
     * Realiza a busca de questionários por um intervalo de datas.
     *
     * @param data1
     * @param data2
     * @return
     */
    @Override
    public List<Questionario> consultaPorData(Date data1, Date data2) {
        if (data1 != null && data2 != null) {
            TypedQuery query = getEntityManager().createQuery("SELECT q FROM " + Questionario.class.getSimpleName()
                    + " q WHERE DATE(q.data) BETWEEN DATE(:data1) AND DATE(:data2)", Questionario.class);

            query.setParameter("data1", data1);
            query.setParameter("data2", data2);

            return query.getResultList();

        } else if (data1 != null || data2 != null) {
            TypedQuery query = getEntityManager().createQuery("SELECT q FROM " + Questionario.class.getSimpleName()
                    + " q WHERE DATE(q.data) = DATE(:data)", Questionario.class);

            if (data1 != null)
                query.setParameter("data", data1);
            else
                query.setParameter("data", data2);

            return query.getResultList();
        }

        return new ArrayList<>();
    }
}
