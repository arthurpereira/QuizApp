package br.com.multitela.quiz.servidor.serviceImpl;

import br.com.multitela.quiz.servidor.dto.RespostasPorAlternativaDTO;
import br.com.multitela.quiz.servidor.entity.JogadorPartidaAssociativa;
import br.com.multitela.quiz.servidor.entity.Partida;
import br.com.multitela.quiz.servidor.entity.Pergunta;
import br.com.multitela.quiz.servidor.entity.Resposta;
import br.com.multitela.quiz.servidor.repository.RepositoryImpl;
import br.com.multitela.quiz.servidor.service.JogadorPartidaAssociativaService;
import br.com.multitela.quiz.servidor.service.RespostaService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arthurpereira on 12/6/16.
 */
@Stateless
public class RespostaImpl extends RepositoryImpl<Resposta> implements RespostaService {

    @EJB
    private JogadorPartidaAssociativaService partidaAssociativaService;

    @Override
    public List<RespostasPorAlternativaDTO> countRespostasPorPergunta(Partida partida, Pergunta pergunta) {
        List<RespostasPorAlternativaDTO> listaRespostasPorAlternativa = new ArrayList<>();

        List<JogadorPartidaAssociativa> listaJogadoresPorPartida;
        listaJogadoresPorPartida = partidaAssociativaService.consultaTodosJogadoresPorPartida(partida);

        Query query = getEntityManager().createNativeQuery(
                "SELECT a.id, c.count FROM alternativa a" +
                " LEFT JOIN" +
                " (SELECT r.alternativa_id, count(r)" +
                " FROM resposta r" +
                " WHERE r.pergunta_id = :pergunta_id AND r.jogador_partida_id IN :listaJogadoresPorPartida" +
                " GROUP BY alternativa_id) c" +
                " ON a.id = c.alternativa_id" +
                " WHERE a.pergunta_id = :pergunta_id" +
                " ORDER BY a.id");
        query.setParameter("pergunta_id", pergunta);
        query.setParameter("listaJogadoresPorPartida", listaJogadoresPorPartida);

        List<Object[]> resultados = query.getResultList();

        for (Object[] resultado : resultados) {
            RespostasPorAlternativaDTO rpa;

            if (resultado[1] != null)
                rpa = new RespostasPorAlternativaDTO(
                    ((BigInteger) resultado[1]).longValue()
                );
            else
                rpa = new RespostasPorAlternativaDTO(Long.valueOf(0));

            listaRespostasPorAlternativa.add(rpa);
        }

        return listaRespostasPorAlternativa;
    }
}
