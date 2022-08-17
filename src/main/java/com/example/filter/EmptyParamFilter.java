package com.example.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = "/uppercase")
public class EmptyParamFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        String inputString = servletRequest.getParameter("input");

        if (inputString != null && inputString.matches("[A-Za-z0-9]+")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            servletResponse.getWriter().println("Missing input parameter");
        }
    }

    @Override
    public void destroy() {
    }

}