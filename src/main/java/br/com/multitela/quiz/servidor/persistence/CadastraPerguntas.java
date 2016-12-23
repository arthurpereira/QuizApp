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
        Alternativa alt1 = new Alternativa();
        Alternativa alt2 = new Alternativa();
        Alternativa alt3 = new Alternativa();
        Alternativa alt4 = new Alternativa();
        
        pergunta.setTexto("Qual desses elementos faz parte da produção do som do violão?");
        alt1.setTexto("As teclas");
        alt2.setTexto("As cordas");
        alt3.setTexto("As baquetas");
        alt4.setTexto("O arco");
        pergunta.setAlternativa(new ArrayList<>());
        pergunta.getAlternativa().add(alt1);
        pergunta.getAlternativa().add(alt2);
        pergunta.getAlternativa().add(alt3);
        pergunta.getAlternativa().add(alt4);
        pergunta.setAlternativa_certa(1);
        
        perguntaController.cadastrar(pergunta);
        
        pergunta = new Pergunta();
        alt1 = new Alternativa();
        alt2 = new Alternativa();
        alt3 = new Alternativa();
        alt4 = new Alternativa();
        
        pergunta.setTexto("Complete o provérbio: \"Cachorro que late...\"");
        alt1.setTexto("Também mia");
        alt2.setTexto("Morde");
        alt3.setTexto("Não morde");
        alt4.setTexto("Tem medo");
        pergunta.setAlternativa(new ArrayList<>());
        pergunta.getAlternativa().add(alt1);
        pergunta.getAlternativa().add(alt2);
        pergunta.getAlternativa().add(alt3);
        pergunta.getAlternativa().add(alt4);
        pergunta.setAlternativa_certa(2);
        
        perguntaController.cadastrar(pergunta);
        
        pergunta = new Pergunta();
        alt1 = new Alternativa();
        alt2 = new Alternativa();
        alt3 = new Alternativa();
        alt4 = new Alternativa();
        
        pergunta.setTexto("Quantas letras contém a escrita da bandeira nacional brasileira?");
        alt1.setTexto("13");
        alt2.setTexto("15");
        alt3.setTexto("14");
        alt4.setTexto("16");
        pergunta.setAlternativa(new ArrayList<>());
        pergunta.getAlternativa().add(alt1);
        pergunta.getAlternativa().add(alt2);
        pergunta.getAlternativa().add(alt3);
        pergunta.getAlternativa().add(alt4);
        pergunta.setAlternativa_certa(1);
        
        perguntaController.cadastrar(pergunta);
        
    }
    
}
