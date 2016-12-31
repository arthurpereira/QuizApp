/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.multitela.quiz.servidor.singleton;

import br.com.multitela.quiz.servidor.bean.observer.PartidaObservada;

/**
 *
 * @author arthurpereira
 */
public class PartidaSingleton {
    
    private static PartidaObservada partidaObservada = null;
    
    public static PartidaObservada getPlacarSingletonInstance() {
        return partidaObservada;
    }
    
    public static void setPlacarSingletonInstance(PartidaObservada obs) {
        partidaObservada = obs;
    }
    
    public static void removePlacarSingletonInstance() {
        partidaObservada = null;
    }
    
}
