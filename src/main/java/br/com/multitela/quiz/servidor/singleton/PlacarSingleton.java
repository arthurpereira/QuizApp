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
public class PlacarSingleton {
    
    private static PartidaObservada placarObservado = null;
    
    public static PartidaObservada getPlacarSingletonInstance() {
        return placarObservado;
    }
    
    public static void setPlacarSingletonInstance(PartidaObservada obs) {
        placarObservado = obs;
    }
    
    public static void removePlacarSingletonInstance() {
        placarObservado = null;
    }
    
}
