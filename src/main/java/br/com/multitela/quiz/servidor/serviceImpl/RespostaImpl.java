package br.com.multitela.quiz.servidor.serviceImpl;

import br.com.multitela.quiz.servidor.dto.RespostasPorAlternativaDTO;
import br.com.multitela.quiz.servidor.dto.RespostasPorJogadorDTO;
import br.com.multitela.quiz.servidor.dto.RespostasPorPerguntaDTO;
import br.com.multitela.quiz.servidor.entity.JogadorPartidaAssociativa;
import br.com.multitela.quiz.servidor.entity.Partida;
import br.com.multitela.quiz.servidor.entity.Pergunta;
import br.com.multitela.quiz.servidor.entity.Resposta;
import br.com.multitela.quiz.servidor.enums.AlternativaStatus;
import br.com.multitela.quiz.servidor.repository.RepositoryImpl;
import br.com.multitela.quiz.servidor.service.JogadorPartidaAssociativaService;
import br.com.multitela.quiz.servidor.service.RespostaService;
import br.com.multitela.quiz.servidor.util.AlternativaUtil;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
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

        try {
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

        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<String> consultaRespostasPorJogadorPartida(JogadorPartidaAssociativa jogadorPartida, Partida partida) {
        List<String> listaRespostaAlternativa = new ArrayList<>();

        List<JogadorPartidaAssociativa> listaJogadoresPorPartida;
        listaJogadoresPorPartida = partidaAssociativaService.consultaTodosJogadoresPorPartida(partida);

        try {
            Query query = getEntityManager().createNativeQuery(
                    "SELECT r.alternativa_indice, p.id FROM resposta r" +
                    " RIGHT JOIN" +
                    " (SELECT p.id FROM pergunta p, resposta r" +
                    " WHERE r.pergunta_id = p.id AND r.jogador_partida_id IN :listaJogadoresPorPartida" +
                    " GROUP BY p.id) p" +
                    " ON r.pergunta_id = p.id AND r.jogador_partida_id = :jogador_partida_id" +
                    " ORDER BY p.id");
            query.setParameter("jogador_partida_id", jogadorPartida);
            query.setParameter("listaJogadoresPorPartida", listaJogadoresPorPartida);

            List<Object[]> resultados = query.getResultList();

            for (Object[] resultado : resultados) {
                String alternativa;

                if (resultado[0] != null)
                    alternativa = AlternativaUtil.retornaLetraAlternativa(
                        ((Integer) resultado[0]).intValue()
                    );
                else
                    alternativa = "NÃO RESPONDEU";

                listaRespostaAlternativa.add(alternativa);
            }
            return listaRespostaAlternativa;

        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<RespostasPorJogadorDTO> consultaRespostasPorJogadorPorPartida(Partida partida) {
        List<RespostasPorJogadorDTO> listaRespostasPorJogador = new ArrayList<>();

        List<JogadorPartidaAssociativa> listaJogadoresPorPartida;
        listaJogadoresPorPartida = partidaAssociativaService.consultaTodosJogadoresPorPartida(partida);

        for (JogadorPartidaAssociativa jpa : listaJogadoresPorPartida) {
            RespostasPorJogadorDTO rpjDTO = new RespostasPorJogadorDTO();
            rpjDTO.setJogador(jpa.getJogador());
            rpjDTO.setListaRespostas(consultaRespostasPorJogadorPartida(jpa, partida));

            listaRespostasPorJogador.add(rpjDTO);
        }

        return listaRespostasPorJogador;
    }

    @Override
    public List<RespostasPorPerguntaDTO> consultaTop10RespostasPorPerguntaPartida(Pergunta pergunta, Partida partida) {
        List<RespostasPorPerguntaDTO> listaRespostasPorPergunta = new ArrayList<>();

        try {
            Query query = getEntityManager().createNativeQuery(
                    "SELECT r.alternativa_indice, jp.id, jp.acertos FROM resposta r" +
                    " RIGHT JOIN" +
                    " (SELECT jp.id, jp.acertos FROM jogador_partida jp" +
                    " WHERE jp.partida_id = :partida ORDER BY jp.acertos DESC LIMIT 10) jp" +
                    " ON r.jogador_partida_id = jp.id AND r.pergunta_id = :pergunta" +
                    " ORDER BY jp.acertos DESC");
            query.setParameter("partida", partida);
            query.setParameter("pergunta", pergunta);

            List<Object[]> resultados = query.getResultList();

            for (Object[] resultado : resultados) {
                RespostasPorPerguntaDTO rppDTO = new RespostasPorPerguntaDTO();;

                if (resultado[0] != null) {
                    rppDTO.setAlternativa(AlternativaUtil.retornaLetraAlternativa(
                            ((Integer) resultado[0]).intValue()).charAt(0));

                    if (((Integer) resultado[0]).intValue() == pergunta.getAlternativa_certa())
                        rppDTO.setStatus(AlternativaStatus.CERTA);
                    else
                        rppDTO.setStatus(AlternativaStatus.ERRADA);
                } else {
                    rppDTO.setAlternativa('-');
                    rppDTO.setStatus(AlternativaStatus.ERRADA);
                }
                listaRespostasPorPergunta.add(rppDTO);
            }
            return listaRespostasPorPergunta;

        } catch (NoResultException ex) {
            return null;
        }
    }

}
