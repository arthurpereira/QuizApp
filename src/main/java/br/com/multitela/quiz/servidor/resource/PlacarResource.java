package br.com.multitela.quiz.servidor.resource;

import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.annotation.PushEndpoint;
import org.primefaces.push.annotation.Singleton;
import org.primefaces.push.impl.JSONEncoder;

import javax.faces.application.FacesMessage;

/**
 * Created by arthurpereira on 10/27/16.
 */
@PushEndpoint(value = "/placar")
@Singleton
public class PlacarResource {

    @OnMessage(encoders = {JSONEncoder.class})
    public FacesMessage onMessage(FacesMessage message) {
        return message;
    }

}
