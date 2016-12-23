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
public class CadastraPerguntasDeCalculo {
    
    public static void main(String args[]) {
        
        PerguntaController perguntaController = new PerguntaController();
        
        Pergunta pergunta = new Pergunta();
        Alternativa alt1 = new Alternativa();
        Alternativa alt2 = new Alternativa();
        Alternativa alt3 = new Alternativa();
        Alternativa alt4 = new Alternativa();
        
        pergunta.setTexto("O zero de uma função polinomial do primeiro grau f(x)=ax+b é dado por:");
        alt1.setTexto("–b/a");
        alt2.setTexto("2b/a");
        alt3.setTexto("a/2b");
        alt4.setTexto("b/a");
        pergunta.setAlternativa(new ArrayList<>());
        pergunta.getAlternativa().add(alt1);
        pergunta.getAlternativa().add(alt2);
        pergunta.getAlternativa().add(alt3);
        pergunta.getAlternativa().add(alt4);
        pergunta.setAlternativa_certa(0);
        
        perguntaController.cadastrar(pergunta);
        
        pergunta = new Pergunta();
        alt1 = new Alternativa();
        alt2 = new Alternativa();
        alt3 = new Alternativa();
        alt4 = new Alternativa();
        
        pergunta.setTexto("Numa função do polinomial do primeiro grau f(x)=ax+b, quando b=0, é conhecida como:");
        alt1.setTexto("Função afim");
        alt2.setTexto("Função linear");
        alt3.setTexto("Função clássica");
        alt4.setTexto("Função quadrática");
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
        
        pergunta.setTexto("O gráfico de uma função polinomial do primeiro grau é:");
        alt1.setTexto("Uma curva");
        alt2.setTexto("Uma reta");
        alt3.setTexto("Uma parábola");
        alt4.setTexto("Um quadrado");
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
        
        pergunta.setTexto("O gráfico de uma função polinomial do segundo grau é:");
        alt1.setTexto("Um triângulo");
        alt2.setTexto("Uma reta");
        alt3.setTexto("Uma parábola");
        alt4.setTexto("Um contorno");
        pergunta.setAlternativa(new ArrayList<>());
        pergunta.getAlternativa().add(alt1);
        pergunta.getAlternativa().add(alt2);
        pergunta.getAlternativa().add(alt3);
        pergunta.getAlternativa().add(alt4);
        pergunta.setAlternativa_certa(2);
        
        perguntaController.cadastrar(pergunta);
        
    }
    
}
