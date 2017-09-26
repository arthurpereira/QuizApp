/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.multitela.quiz.servidor.persistence;

import br.com.multitela.quiz.servidor.controller.PerguntaController;
import br.com.multitela.quiz.servidor.entity.Alternativa;
import br.com.multitela.quiz.servidor.entity.Pergunta;
import java.util.ArrayList;

/**
 *
 * @author arthurpereira
 */
public class CadastraPerguntas {
    
    public static void main(String args[]) {
        
        PerguntaController perguntaController = new PerguntaController();
        
        Pergunta pergunta = new Pergunta();
        Alternativa alt1 = new Alternativa(pergunta);
        Alternativa alt2 = new Alternativa(pergunta);
        Alternativa alt3 = new Alternativa(pergunta);
        Alternativa alt4 = new Alternativa(pergunta);
        
        pergunta.setTexto("Qual desses elementos faz parte da produção do som do violão?");
        alt1.setTexto("As teclas");
        alt2.setTexto("As cordas");
        alt3.setTexto("As baquetas");
        alt4.setTexto("O arco");
        pergunta.setAlternativas(new ArrayList<>());
        pergunta.getAlternativas().add(alt1);
        pergunta.getAlternativas().add(alt2);
        pergunta.getAlternativas().add(alt3);
        pergunta.getAlternativas().add(alt4);
        pergunta.setAlternativa_certa(1);
        
        perguntaController.cadastrar(pergunta);
        
        pergunta = new Pergunta();
        alt1 = new Alternativa(pergunta);
        alt2 = new Alternativa(pergunta);
        alt3 = new Alternativa(pergunta);
        alt4 = new Alternativa(pergunta);
        
        pergunta.setTexto("Complete o provérbio: \"Cachorro que late...\"");
        alt1.setTexto("Também mia");
        alt2.setTexto("Morde");
        alt3.setTexto("Não morde");
        alt4.setTexto("Tem medo");
        pergunta.setAlternativas(new ArrayList<>());
        pergunta.getAlternativas().add(alt1);
        pergunta.getAlternativas().add(alt2);
        pergunta.getAlternativas().add(alt3);
        pergunta.getAlternativas().add(alt4);
        pergunta.setAlternativa_certa(2);
        
        perguntaController.cadastrar(pergunta);
        
        pergunta = new Pergunta();
        alt1 = new Alternativa(pergunta);
        alt2 = new Alternativa(pergunta);
        alt3 = new Alternativa(pergunta);
        alt4 = new Alternativa(pergunta);
        
        pergunta.setTexto("Quantas letras contém a escrita da bandeira nacional brasileira?");
        alt1.setTexto("13");
        alt2.setTexto("15");
        alt3.setTexto("14");
        alt4.setTexto("16");
        pergunta.setAlternativas(new ArrayList<>());
        pergunta.getAlternativas().add(alt1);
        pergunta.getAlternativas().add(alt2);
        pergunta.getAlternativas().add(alt3);
        pergunta.getAlternativas().add(alt4);
        pergunta.setAlternativa_certa(1);
        
        perguntaController.cadastrar(pergunta);
        
    }
    
}
