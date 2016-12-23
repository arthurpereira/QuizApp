/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.multitela.quiz.servidor.filter;

import br.com.multitela.quiz.servidor.entity.Usuario;
import java.io.IOException;
import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author arthurpereira
 */
public class LoginFilter extends AbstractFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();

        if (session.isNew()) {
            doLogin(request, response, httpRequest);
        } else {
            Usuario usuario = (Usuario) session.getAttribute("usuario-logado");

            if (usuario == null && !httpRequest.getRequestURI().endsWith("/login.xhtml")
                    && !httpRequest.getRequestURI().startsWith(httpRequest.getContextPath()
                            + ResourceHandler.RESOURCE_IDENTIFIER)) {
                doLogin(request, response, httpRequest);
            } else {
                chain.doFilter(request, response);
            }
        }
    }

    @Override
    public void destroy() {

    }

}
