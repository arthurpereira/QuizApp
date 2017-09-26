package br.com.multitela.quiz.servidor.servlet;

import br.com.multitela.quiz.servidor.util.PropertiesUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by arthurpereira on 06/04/17.
 */
@WebServlet("/imagens/*")
public class ImagemServlet extends HttpServlet {

    private static final String DIRETORIO_IMAGENS = PropertiesUtil.getResource().getString("quizapp.diretorio.imagens");

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filename = request.getPathInfo().substring(1);
        File file = new File(DIRETORIO_IMAGENS, filename);

        response.setHeader("Content-Type", getServletContext().getMimeType(filename));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + filename + "\"");

        Files.copy(file.toPath(), response.getOutputStream());
    }

}
