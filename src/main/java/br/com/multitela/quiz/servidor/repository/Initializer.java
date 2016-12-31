package br.com.multitela.quiz.servidor.repository;

import br.com.multitela.quiz.servidor.entity.Alternativa;
import br.com.multitela.quiz.servidor.entity.Pergunta;
import br.com.multitela.quiz.servidor.entity.Usuario;
import org.springframework.util.DigestUtils;

import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;

/**
 * Created by arthurpereira on 12/1/16.
 */
@Singleton
@Startup
public class Initializer {

    @Inject
    EntityManager em;

    @PostConstruct
    private void init() {
        cadastraAdmin();
    }

    private void cadastraAdmin() {
        Query query = em.createQuery("SELECT COUNT(u) FROM Usuario u");
        Long usuarios = (Long) query.getSingleResult();

        if (usuarios == 0) {
            Usuario usuario = new Usuario();

            usuario.setNome("Administrador");
            usuario.setUsuario("admin");
            usuario.setSenha(DigestUtils.md5DigestAsHex(("123456").getBytes()));

            em.persist(usuario);
            em.flush();
        }
        
        query = em.createQuery("SELECT COUNT(p) FROM Pergunta p");
        Long perguntas = (Long) query.getSingleResult();
        
        if (perguntas == 0) {
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

            em.persist(pergunta);
            em.flush();

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

            em.persist(pergunta);
            em.flush();

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

            em.persist(pergunta);
            em.flush();

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

            em.persist(pergunta);
            em.flush();
        }
    }
}
