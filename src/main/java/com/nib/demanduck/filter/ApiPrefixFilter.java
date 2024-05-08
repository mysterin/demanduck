package com.nib.demanduck.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

/**
 * @author linxiaobin
 * @Description
 * @date 2024/5/8 18:11
 */
public class ApiPrefixFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        if (httpRequest.getRequestURI().startsWith("/api")) {
            filterChain.doFilter(new RemoveApiPrefixRequestWrapper(httpRequest), servletResponse);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private static class RemoveApiPrefixRequestWrapper extends HttpServletRequestWrapper {

        RemoveApiPrefixRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String getRequestURI() {
            return super.getRequestURI().replaceFirst("/api", "");
        }

        @Override
        public StringBuffer getRequestURL() {
            return super.getRequestURL().delete(0, 4);
        }
    }
}
