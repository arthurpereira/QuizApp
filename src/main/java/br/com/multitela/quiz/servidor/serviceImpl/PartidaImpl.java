package br.com.multitela.quiz.servidor.serviceImpl;

import br.com.multitela.quiz.servidor.entity.Partida;
import br.com.multitela.quiz.servidor.repository.RepositoryImpl;
import br.com.multitela.quiz.servidor.service.PartidaService;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by arthurpereira on 12/6/16.
 */
@Stateless
public class PartidaImpl extends RepositoryImpl<Partida> implements PartidaService {

    /**
     * Realiza a busca de partidas por um intervalo de datas.
     *
     * @param data1
     * @param data2
     * @return
     */
    @Override
    public List<Partida> consultaPorData(Date data1, Date data2) {
        if (data1 != null && data2 != null) {
            TypedQuery query = getEntityManager().createQuery("SELECT q FROM " + Partida.class.getSimpleName()
                    + " q WHERE DATE(q.data) BETWEEN DATE(:data1) AND DATE(:data2)", Partida.class);

            query.setParameter("data1", data1);
            query.setParameter("data2", data2);

            return query.getResultList();

        } else if (data1 != null || data2 != null) {
            TypedQuery query = getEntityManager().createQuery("SELECT q FROM " + Partida.class.getSimpleName()
                    + " q WHERE DATE(q.data) = DATE(:data)", Partida.class);

            if (data1 != null)
                query.setParameter("data", data1);
            else
                query.setParameter("data", data2);

            return query.getResultList();
        }

        return new ArrayList<>();
    }

    @Override
    public Partida consultaUltimaPartida() {
        TypedQuery query = getEntityManager().createQuery(
                "SELECT partida FROM " + Partida.class.getName()
                + " AS partida ORDER BY partida.id DESC", Partida.class);
        query.setMaxResults(1);

        return (Partida) query.getSingleResult();
    }
}
