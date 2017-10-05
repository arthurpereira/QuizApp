package br.com.multitela.quiz.servidor.serviceImpl;

import br.com.multitela.quiz.servidor.dto.PerguntasPorPartidaDTO;
import br.com.multitela.quiz.servidor.entity.Partida;
import br.com.multitela.quiz.servidor.entity.Pergunta;
import br.com.multitela.quiz.servidor.entity.Resposta;
import br.com.multitela.quiz.servidor.repository.RepositoryImpl;
import br.com.multitela.quiz.servidor.service.PerguntaService;
import br.com.multitela.quiz.servidor.util.StringUtil;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
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

    @Override
    public List<PerguntasPorPartidaDTO> consultaPerguntasPorPartida(Partida partida) {
        List<PerguntasPorPartidaDTO> listaPerguntasPorPartida = new ArrayList<>();

        Query query = getEntityManager().createQuery(
                "SELECT p.texto FROM " + Pergunta.class.getName() + " p, " + Resposta.class.getName() + " r"
                        + " WHERE r.pergunta.id = p.id AND r.jogadorPartida.partida = :partida"
                        + " GROUP BY p.id ORDER BY p.id");
        query.setParameter("partida", partida);

        List<String> resultados = query.getResultList();

        int indice = 0;

        for (String resultado : resultados) {
            PerguntasPorPartidaDTO pppDTO;

            if (resultado != null)
                pppDTO = new PerguntasPorPartidaDTO(
                        StringUtil.trimString(resultado, 30, true),
                        indice
                );
            else
                pppDTO = new PerguntasPorPartidaDTO("", indice);

            listaPerguntasPorPartida.add(pppDTO);
            indice++;
        }

        return listaPerguntasPorPartida;
    }

}
