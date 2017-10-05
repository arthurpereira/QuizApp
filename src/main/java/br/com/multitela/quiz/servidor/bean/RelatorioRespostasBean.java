package br.com.multitela.quiz.servidor.bean;

import br.com.multitela.quiz.servidor.dto.PerguntasPorPartidaDTO;
import br.com.multitela.quiz.servidor.dto.RespostasPorJogadorDTO;
import br.com.multitela.quiz.servidor.entity.Partida;
import br.com.multitela.quiz.servidor.service.JogadorPartidaAssociativaService;
import br.com.multitela.quiz.servidor.service.PartidaService;
import br.com.multitela.quiz.servidor.service.PerguntaService;
import br.com.multitela.quiz.servidor.service.RespostaService;
import br.com.multitela.quiz.servidor.util.AlternativaUtil;
import br.com.multitela.quiz.servidor.util.ExcelUtil;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by arthurpereira on 03/10/17.
 */
@Named
@ViewScoped
public class RelatorioRespostasBean extends AbstractBean {

    @EJB
    private PartidaService partidaService;

    @EJB
    private JogadorPartidaAssociativaService jogadorPartidaService;

    @EJB
    private RespostaService respostaService;

    @EJB
    private PerguntaService perguntaService;

    private Partida partida;
    private Date data1, data2;
    private List<Partida> partidasBuscadas;

    private List<RespostasPorJogadorDTO> listaRespostasPorJogador;
    private List<PerguntasPorPartidaDTO> listaPerguntasPorPartida;

    public RelatorioRespostasBean() {}

    /**
     * Realiza a busca de resultados das partidas no sistema.
     */
    public void buscar() {
        try {
            partidasBuscadas = partidaService.consultaPorData(data1, data2);

            for (Partida partida : partidasBuscadas) {
                partida.setJogadores(jogadorPartidaService.consultaTodosJogadoresPorPartida(partida));
            }

            if (partidasBuscadas.isEmpty())
                mensagemInfo("Nenhuma Partida foi encontrada.");
        } catch (Exception ex) {
            keepDialogOpen();
            mensagemAtencao("Não foi possível realizar a busca. Tente novamente mais tarde.");
            ex.printStackTrace();
        }
    }

    public void consultaRespostasPorJogadorPorPartida(Partida partida) {
        listaRespostasPorJogador = respostaService.consultaRespostasPorJogadorPorPartida(partida);
        listaPerguntasPorPartida = perguntaService.consultaPerguntasPorPartida(partida);
    }

    public void postProcessorXLSX(Object obj) {
        ExcelUtil.formatExcel(obj);
    }

    // GETTERS AND SETTERS

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    public Date getData1() {
        return data1;
    }

    public void setData1(Date data1) {
        this.data1 = data1;
    }

    public Date getData2() {
        return data2;
    }

    public void setData2(Date data2) {
        this.data2 = data2;
    }

    public List<Partida> getPartidasBuscadas() {
        return partidasBuscadas;
    }

    public List<RespostasPorJogadorDTO> getListaRespostasPorJogador() {
        return listaRespostasPorJogador;
    }

    public List<PerguntasPorPartidaDTO> getListaPerguntasPorPartida() {
        return listaPerguntasPorPartida;
    }
}
