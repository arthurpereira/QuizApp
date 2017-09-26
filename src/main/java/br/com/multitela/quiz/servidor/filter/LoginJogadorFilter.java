package br.com.multitela.quiz.servidor.filter;

import br.com.multitela.quiz.servidor.entity.Jogador;

import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by arthurpereira on 1/21/17.
 */
public class LoginJogadorFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpSession httpSession = httpServletRequest.getSession();

        if (httpSession.isNew()) {
            doLogin(servletRequest, servletResponse, httpServletRequest);
        } else {
            Jogador jogador = (Jogador) httpSession.getAttribute("jogador");

            if (jogador == null && !httpServletRequest.getRequestURI().contains("login")
                    && !httpServletRequest.getRequestURI().startsWith(httpServletRequest.getContextPath()
                            + ResourceHandler.RESOURCE_IDENTIFIER)) {
                doLogin(servletRequest, servletResponse, httpServletRequest);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
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
