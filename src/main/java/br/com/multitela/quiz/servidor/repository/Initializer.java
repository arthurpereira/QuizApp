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
            Alternativa alt1 = new Alternativa(pergunta);
            Alternativa alt2 = new Alternativa(pergunta);
            Alternativa alt3 = new Alternativa(pergunta);
            Alternativa alt4 = new Alternativa(pergunta);
//            Alternativa alt5 = new Alternativa(pergunta);

            pergunta.setTexto("O ângulo raso possui:");
            alt1.setTexto("45°");
            alt2.setTexto("90°");
            alt3.setTexto("180°");
            alt4.setTexto("360°");
//            alt5.setTexto("A descrição de qualquer movimento ou repouso depende de um referencial.");
            pergunta.setAlternativas(new ArrayList<>());
            pergunta.getAlternativas().add(alt1);
            pergunta.getAlternativas().add(alt2);
            pergunta.getAlternativas().add(alt3);
            pergunta.getAlternativas().add(alt4);
//            pergunta.getAlternativas().add(alt5);
            pergunta.setAlternativa_certa(2);

            em.persist(pergunta);
            em.flush();

            pergunta = new Pergunta();
            alt1 = new Alternativa(pergunta);
            alt2 = new Alternativa(pergunta);
            alt3 = new Alternativa(pergunta);
            alt4 = new Alternativa(pergunta);
//            alt5 = new Alternativa(pergunta);

            pergunta.setTexto("As retas concorrentes que formam 4 ângulos retos são as:");
            alt1.setTexto("Paralelas");
            alt2.setTexto("Perpendiculares");
            alt3.setTexto("Coincidentes");
            alt4.setTexto("Reversas");
//            alt5.setTexto("A descrição de qualquer movimento ou repouso depende de um referencial.");
            pergunta.setAlternativas(new ArrayList<>());
            pergunta.getAlternativas().add(alt1);
            pergunta.getAlternativas().add(alt2);
            pergunta.getAlternativas().add(alt3);
            pergunta.getAlternativas().add(alt4);
//            pergunta.getAlternativas().add(alt5);
            pergunta.setAlternativa_certa(1);

            em.persist(pergunta);
            em.flush();

            pergunta = new Pergunta();
            alt1 = new Alternativa(pergunta);
            alt2 = new Alternativa(pergunta);
            alt3 = new Alternativa(pergunta);
            alt4 = new Alternativa(pergunta);
//            alt5 = new Alternativa(pergunta);

            pergunta.setTexto("O ângulo cujo valor está entre 0° e 90° se denomina:");
            alt1.setTexto("Agudo");
            alt2.setTexto("Obtuso");
            alt3.setTexto("Reto");
            alt4.setTexto("Pleno");
//            alt5.setTexto("A descrição de qualquer movimento ou repouso depende de um referencial.");
            pergunta.setAlternativas(new ArrayList<>());
            pergunta.getAlternativas().add(alt1);
            pergunta.getAlternativas().add(alt2);
            pergunta.getAlternativas().add(alt3);
            pergunta.getAlternativas().add(alt4);
//            pergunta.getAlternativas().add(alt5);
            pergunta.setAlternativa_certa(0);

            em.persist(pergunta);
            em.flush();

            pergunta = new Pergunta();
            alt1 = new Alternativa(pergunta);
            alt2 = new Alternativa(pergunta);
            alt3 = new Alternativa(pergunta);
            alt4 = new Alternativa(pergunta);
//            alt5 = new Alternativa(pergunta);

            pergunta.setTexto("Uma fração equivalente de <sup>2</sup>&frasl;<sub>5</sub> é:");
            alt1.setTexto("<sup>4</sup>&frasl;<sub>5</sub>");
            alt2.setTexto("<sup>6</sup>&frasl;<sub>15</sub>");
            alt3.setTexto("<sup>1</sup>&frasl;<sub>2</sub>");
            alt4.setTexto("<sup>8</sup>&frasl;<sub>10</sub>");
//            alt5.setTexto("A descrição de qualquer movimento ou repouso depende de um referencial.");
            pergunta.setAlternativas(new ArrayList<>());
            pergunta.getAlternativas().add(alt1);
            pergunta.getAlternativas().add(alt2);
            pergunta.getAlternativas().add(alt3);
            pergunta.getAlternativas().add(alt4);
//            pergunta.getAlternativas().add(alt5);
            pergunta.setAlternativa_certa(1);

            em.persist(pergunta);
            em.flush();

            pergunta = new Pergunta();
            alt1 = new Alternativa(pergunta);
            alt2 = new Alternativa(pergunta);
            alt3 = new Alternativa(pergunta);
            alt4 = new Alternativa(pergunta);
//            alt5 = new Alternativa(pergunta);

            pergunta.setTexto("Como se lê a fração <sup>3</sup>&frasl;<sub>21</sub>?");
            alt1.setTexto("Três vinte e um");
            alt2.setTexto("Três vigésimo primeiro");
            alt3.setTexto("Terceiro vinte e um");
            alt4.setTexto("Três vinte e um avos");
//            alt5.setTexto("A descrição de qualquer movimento ou repouso depende de um referencial.");
            pergunta.setAlternativas(new ArrayList<>());
            pergunta.getAlternativas().add(alt1);
            pergunta.getAlternativas().add(alt2);
            pergunta.getAlternativas().add(alt3);
            pergunta.getAlternativas().add(alt4);
//            pergunta.getAlternativas().add(alt5);
            pergunta.setAlternativa_certa(3);

            em.persist(pergunta);
            em.flush();

            pergunta = new Pergunta();
            alt1 = new Alternativa(pergunta);
            alt2 = new Alternativa(pergunta);
            alt3 = new Alternativa(pergunta);
            alt4 = new Alternativa(pergunta);
//            alt5 = new Alternativa(pergunta);

            pergunta.setTexto("A fração <sup>12</sup>&frasl;<sub>3</sub> é classificada como:");
            alt1.setTexto("Aparente");
            alt2.setTexto("Própria");
            alt3.setTexto("Mista");
            alt4.setTexto("Equivalente");
//            alt5.setTexto("A descrição de qualquer movimento ou repouso depende de um referencial.");
            pergunta.setAlternativas(new ArrayList<>());
            pergunta.getAlternativas().add(alt1);
            pergunta.getAlternativas().add(alt2);
            pergunta.getAlternativas().add(alt3);
            pergunta.getAlternativas().add(alt4);
//            pergunta.getAlternativas().add(alt5);
            pergunta.setAlternativa_certa(0);

            em.persist(pergunta);
            em.flush();

            pergunta = new Pergunta();
            alt1 = new Alternativa(pergunta);
            alt2 = new Alternativa(pergunta);
            alt3 = new Alternativa(pergunta);
            alt4 = new Alternativa(pergunta);
//            alt5 = new Alternativa(pergunta);

            pergunta.setTexto("O número misto 2<sup>1</sup>&frasl;<sub>5</sub> é a fração imprópria :");
            alt1.setTexto("<sup>1</sup>&frasl;<sub>5</sub>");
            alt2.setTexto("<sup>7</sup>&frasl;<sub>5</sub>");
            alt3.setTexto("<sup>11</sup>&frasl;<sub>5</sub>");
            alt4.setTexto("<sup>15</sup>&frasl;<sub>5</sub>");
//            alt5.setTexto("A descrição de qualquer movimento ou repouso depende de um referencial.");
            pergunta.setAlternativas(new ArrayList<>());
            pergunta.getAlternativas().add(alt1);
            pergunta.getAlternativas().add(alt2);
            pergunta.getAlternativas().add(alt3);
            pergunta.getAlternativas().add(alt4);
//            pergunta.getAlternativas().add(alt5);
            pergunta.setAlternativa_certa(2);

            em.persist(pergunta);
            em.flush();

            pergunta = new Pergunta();
            alt1 = new Alternativa(pergunta);
            alt2 = new Alternativa(pergunta);
            alt3 = new Alternativa(pergunta);
            alt4 = new Alternativa(pergunta);
//            alt5 = new Alternativa(pergunta);

            pergunta.setTexto("A expressão <sup>3</sup>&frasl;<sub>9</sub> + <sup>5</sup>&frasl;<sub>9</sub> - <sup>4</sup>&frasl;<sub>9</sub> resulta em:");
            alt1.setTexto("<sup>1</sup>&frasl;<sub>9</sub>");
            alt2.setTexto("<sup>4</sup>&frasl;<sub>9</sub>");
            alt3.setTexto("<sup>5</sup>&frasl;<sub>9</sub>");
            alt4.setTexto("<sup>7</sup>&frasl;<sub>9</sub>");
//            alt5.setTexto("A descrição de qualquer movimento ou repouso depende de um referencial.");
            pergunta.setAlternativas(new ArrayList<>());
            pergunta.getAlternativas().add(alt1);
            pergunta.getAlternativas().add(alt2);
            pergunta.getAlternativas().add(alt3);
            pergunta.getAlternativas().add(alt4);
//            pergunta.getAlternativas().add(alt5);
            pergunta.setAlternativa_certa(1);

            em.persist(pergunta);
            em.flush();

            pergunta = new Pergunta();
            alt1 = new Alternativa(pergunta);
            alt2 = new Alternativa(pergunta);
            alt3 = new Alternativa(pergunta);
            alt4 = new Alternativa(pergunta);
//            alt5 = new Alternativa(pergunta);

            pergunta.setTexto("A adição <sup>2</sup>&frasl;<sub>3</sub> + <sup>1</sup>&frasl;<sub>4</sub> resulta em:");
            alt1.setTexto("<sup>11</sup>&frasl;<sub>12</sub>");
            alt2.setTexto("<sup>3</sup>&frasl;<sub>12</sub>");
            alt3.setTexto("<sup>3</sup>&frasl;<sub>7</sub>");
            alt4.setTexto("<sup>3</sup>&frasl;<sub>4</sub>");
//            alt5.setTexto("A descrição de qualquer movimento ou repouso depende de um referencial.");
            pergunta.setAlternativas(new ArrayList<>());
            pergunta.getAlternativas().add(alt1);
            pergunta.getAlternativas().add(alt2);
            pergunta.getAlternativas().add(alt3);
            pergunta.getAlternativas().add(alt4);
//            pergunta.getAlternativas().add(alt5);
            pergunta.setAlternativa_certa(0);

            em.persist(pergunta);
            em.flush();

            pergunta = new Pergunta();
            alt1 = new Alternativa(pergunta);
            alt2 = new Alternativa(pergunta);
            alt3 = new Alternativa(pergunta);
            alt4 = new Alternativa(pergunta);
//            alt5 = new Alternativa(pergunta);

            pergunta.setTexto("Jorge recebeu 50 reais de mesada e deu <sup>2</sup>&frasl;<sub>5</sub> para sua irmã. Quanto Jorge ficou?");
            alt1.setTexto("5 reais");
            alt2.setTexto("10 reais");
            alt3.setTexto("20 reais");
            alt4.setTexto("30 reais");
//            alt5.setTexto("A descrição de qualquer movimento ou repouso depende de um referencial.");
            pergunta.setAlternativas(new ArrayList<>());
            pergunta.getAlternativas().add(alt1);
            pergunta.getAlternativas().add(alt2);
            pergunta.getAlternativas().add(alt3);
            pergunta.getAlternativas().add(alt4);
//            pergunta.getAlternativas().add(alt5);
            pergunta.setAlternativa_certa(3);

            em.persist(pergunta);
            em.flush();
//
        }
    }
}
