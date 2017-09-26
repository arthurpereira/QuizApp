package br.com.multitela.quiz.servidor.filter;

import br.com.multitela.quiz.servidor.entity.Jogador;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by arthurpereira on 1/21/17.
 */
public class JogadorFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        Jogador jogador = (Jogador) httpServletRequest.getSession(true).getAttribute("jogador");

        if (jogador == null) {
            accessDenied(servletRequest, servletResponse, httpServletRequest);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }

    private void doLogin(ServletRequest request, ServletResponse response, HttpServletRequest httpServletRequest) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher("/login/index.xhtml");
        requestDispatcher.forward(request, response);
    }

    private void accessDenied(ServletRequest request, ServletResponse response, HttpServletRequest httpServletRequest) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher("/login/index.xhtml");
        requestDispatcher.forward(request, response);
    }
}
