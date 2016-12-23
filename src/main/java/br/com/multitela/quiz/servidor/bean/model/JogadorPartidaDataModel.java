/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.multitela.quiz.servidor.bean.model;

import br.com.multitela.quiz.servidor.entity.JogadorPartidaAssociativa;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author arthurpereira
 */
public class JogadorPartidaDataModel extends ListDataModel<JogadorPartidaAssociativa> implements SelectableDataModel<JogadorPartidaAssociativa>, Serializable {
    
    public JogadorPartidaDataModel() {
        
    }
    
    public JogadorPartidaDataModel(List<JogadorPartidaAssociativa> data) {
        super(data);
    }

    @Override
    public Object getRowKey(JogadorPartidaAssociativa jogadorPartida) {
        return jogadorPartida.getId();
    }

    @Override
    public JogadorPartidaAssociativa getRowData(String rowKey) {
        List<JogadorPartidaAssociativa> jogadoresPorPartida = (List<JogadorPartidaAssociativa>) getWrappedData();
        
        for (JogadorPartidaAssociativa jogadorPartida : jogadoresPorPartida) {
            if (Objects.equals(jogadorPartida.getId(), Long.valueOf(rowKey))) {
                return jogadorPartida;
            }
        }
        
        return null;
    }
    
}
