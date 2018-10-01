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

    /**
     * @author Matheus Siqueira <matheussiqueira.ec@gmail.com>
     * @param texto_pergunta texto da pergunta a ser cadastrada
     * @param alternativas array de alternativas da pergunta a ser cadastrada
     * @param alternativa_certa índice do array que identifica a alternativa certa
     */

    private void montaPerguntas(String texto_pergunta, String[] alternativas, int alternativa_certa) {
        // Instancia nova pergunta
        Pergunta pergunta = new Pergunta();

        // Instancia alternativas
        Alternativa alt1 = new Alternativa(pergunta);
        Alternativa alt2 = new Alternativa(pergunta);
        Alternativa alt3 = new Alternativa(pergunta);
        Alternativa alt4 = new Alternativa(pergunta);
        Alternativa alt5 = new Alternativa(pergunta);

        // Seta texto da instância de pergunta
        pergunta.setTexto(texto_pergunta);

        // Seta texto das instâncias de alternativas
        alt1.setTexto(alternativas[0]);
        alt2.setTexto(alternativas[1]);
        alt3.setTexto(alternativas[2]);
        alt4.setTexto(alternativas[3]);
        alt5.setTexto(alternativas[4]);

        // Seta alternativas da instância de perguntas
        pergunta.setAlternativas(new ArrayList<>());
        pergunta.getAlternativas().add(alt1);
        pergunta.getAlternativas().add(alt2);
        pergunta.getAlternativas().add(alt3);
        pergunta.getAlternativas().add(alt4);
        pergunta.getAlternativas().add(alt5);

        // Seta alternativa certa
        pergunta.setAlternativa_certa(alternativa_certa);

        // Salva pergunta no banco
        em.persist(pergunta);

        // Destrói instância
        em.flush();
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

        cadastraPerguntas();
    }

    private void cadastraPerguntas() {
        Query query = em.createQuery("SELECT COUNT(p) FROM Pergunta p");;
        Long perguntas = (Long) query.getSingleResult();
        
        if (perguntas == 0) {
            montaPerguntas("O ângulo raso possui:", new String[]{"30°", "45°", "90°", "180°", "360°"}, 3);
            montaPerguntas("As retas concorrentes que formam 4 ângulos retos são as:", new String[]{"Paralelas", "Perpendiculares", "Coincidentes", "Reversas", "Concisas"}, 1);
            montaPerguntas("O ângulo cujo valor está entre 0° e 90° se denomina:", new String[]{"Agudo", "Obtuso", "Reto", "Pleno", "Plano"}, 0);
            montaPerguntas("Uma fração equivalente de <sup>2</sup>&frasl;<sub>5</sub> é:", new String[]{"<sup>4</sup>&frasl;<sub>5</sub>", "<sup>6</sup>&frasl;<sub>15</sub>", "<sup>1</sup>&frasl;<sub>2</sub>", "<sup>8</sup>&frasl;<sub>10</sub>", "<sup>3</sup>&frasl;<sub>9</sub>"}, 1);
            montaPerguntas("Como se lê a fração <sup>3</sup>&frasl;<sub>21</sub>?", new String[]{"Três vinte e um", "Três vigésimo primeiro", "Terceiro vinte e um", "Três vinte e um avos", "Três sobre vinte e um"}, 3);
            montaPerguntas("A fração <sup>12</sup>&frasl;<sub>3</sub> é classificada como:", new String[]{"Aparente", "Própria", "Mista", "Equivalente", "Equidistante"}, 0);
            montaPerguntas("O número misto 2<sup>1</sup>&frasl;<sub>5</sub> é a fração imprópria:", new String[]{"<sup>1</sup>&frasl;<sub>5</sub>", "<sup>7</sup>&frasl;<sub>5</sub>", "<sup>11</sup>&frasl;<sub>5</sub>", "<sup>15</sup>&frasl;<sub>5</sub>", "<sup>13</sup>&frasl;<sub>5</sub>"}, 2);
            montaPerguntas("A expressão <sup>3</sup>&frasl;<sub>9</sub> + <sup>5</sup>&frasl;<sub>9</sub> - <sup>4</sup>&frasl;<sub>9</sub> resulta em:", new String[]{"<sup>1</sup>&frasl;<sub>9</sub>", "<sup>4</sup>&frasl;<sub>9</sub>", "<sup>5</sup>&frasl;<sub>9</sub>", "<sup>7</sup>&frasl;<sub>9</sub>", "<sup>8</sup>&frasl;<sub>9</sub>"}, 1);
            montaPerguntas("A adição <sup>2</sup>&frasl;<sub>3</sub> + <sup>1</sup>&frasl;<sub>4</sub> resulta em:", new String[]{"<sup>11</sup>&frasl;<sub>12</sub>", "<sup>3</sup>&frasl;<sub>12</sub>", "<sup>3</sup>&frasl;<sub>7</sub>", "<sup>3</sup>&frasl;<sub>4</sub>", "<sup>4</sup>&frasl;<sub>7</sub>"}, 0);
            montaPerguntas("Jorge recebeu 50 reais de mesada e deu <sup>2</sup>&frasl;<sub>5</sub> para sua irmã. Quanto Jorge ficou?", new String[]{"5 reais", "10 reais", "20 reais", "25 reais", "35 reais"}, 3);
        }
    }
}