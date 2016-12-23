/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.multitela.quiz.servidor.bean.model;

import br.com.multitela.quiz.servidor.entity.Alternativa;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author arthurpereira
 */
public class AlternativaDataModel extends ListDataModel<Alternativa> implements SelectableDataModel<Alternativa>, Serializable {
    
    public AlternativaDataModel() {
        
    }
    
    public AlternativaDataModel(List<Alternativa> data) {
        super(data);
    }

    @Override
    public Object getRowKey(Alternativa alternativa) {
        return alternativa.getId();
    }

    @Override
    public Alternativa getRowData(String rowKey) {
        List<Alternativa> alternativas = (List<Alternativa>) getWrappedData();
        
        for (Alternativa alternativa : alternativas) {
            if (Objects.equals(alternativa.getId(), Long.valueOf(rowKey))) {
                return alternativa;
            }
        }
        
        return null;
    }
}
