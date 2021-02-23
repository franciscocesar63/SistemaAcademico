/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fafic.util;

import br.com.fafic.model.Administrador;
import br.com.fafic.model.Aluno;
import br.com.fafic.model.Coordenador;
import br.com.fafic.model.Diretor;
import br.com.fafic.model.Pessoa;
import br.com.fafic.model.Professor;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



@WebFilter(filterName = "filtroSession",urlPatterns = {"/administrador/*", "/diretor/*", "/coordenador/*", "/professor/*", "/aluno/*"})
public class SesseionFiltro implements Filter {

    @Override
    public void init(FilterConfig fc) throws ServletException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) sr;
        HttpServletResponse httpResponse = (HttpServletResponse) sr1;
        HttpSession httpSession = httpRequest.getSession();
        Pessoa usuario = (Pessoa) httpSession.getAttribute("pessoa");
    
        String uri = httpRequest.getRequestURI();
        String appContext = httpRequest.getContextPath();
        
        StringBuilder builder = new StringBuilder();
        builder.append(appContext);
        
        if(usuario == null) {
            httpResponse.sendRedirect(builder.append("/index.xhtml").toString());
        } else if(usuario instanceof Administrador && !uri.contains("/administrador")) {
            httpResponse.sendRedirect(builder.append("/administrador/index.xhtml").toString());
        } else if(usuario instanceof Diretor && !uri.contains("/diretor")) {
            httpResponse.sendRedirect(builder.append("/diretor/index.xhtml").toString());
        } else if(usuario instanceof Coordenador && !uri.contains("/coordenador")) {
            httpResponse.sendRedirect(builder.append("/coordenador/index.xhtml").toString());
        } else if(usuario instanceof Professor && !uri.contains("/professor")) {
            httpResponse.sendRedirect(builder.append("/professor/index.xhtml").toString());
        } else if(usuario instanceof Aluno && !uri.contains("/aluno")) {
            httpResponse.sendRedirect(builder.append("/aluno/index.xhtml").toString());
        } else {
            try{
            fc.doFilter(sr, sr1);
            }catch(ServletException ex) {
                System.out.println("Erro: "+ex.getMessage());
            }
        }
    }

    @Override
    public void destroy() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
