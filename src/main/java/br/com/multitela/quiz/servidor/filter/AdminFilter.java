/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.multitela.quiz.servidor.filter;

import br.com.multitela.quiz.servidor.entity.Usuario;
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
 *
 * @author arthurpereira
 */
public class AdminFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        Usuario usuarioLogado = (Usuario) httpServletRequest.getSession(true).getAttribute("usuario-logado");

        if (usuarioLogado == null) {
            accessDenied(request, response, httpServletRequest);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }

    private void doLogin(ServletRequest request, ServletResponse response, HttpServletRequest httpServletRequest) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher("/admin/login.xhtml");
        requestDispatcher.forward(request, response);
    }

    private void accessDenied(ServletRequest request, ServletResponse response, HttpServletRequest httpServletRequest) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher("/admin/login.xhtml");
        requestDispatcher.forward(request, response);
    }

}
