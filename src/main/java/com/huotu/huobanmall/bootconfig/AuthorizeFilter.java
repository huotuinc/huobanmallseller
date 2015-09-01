package com.huotu.huobanmall.bootconfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lgh on 2015/8/31.
 */
public class AuthorizeFilter implements Filter {
    private static final Log log = LogFactory.getLog(AuthorizeFilter.class);

    private ServletContext servletContext;

    private SecurityContextRepository httpSessionSecurityContextRepository = new HttpSessionSecurityContextRepository();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("filter!");

        HttpRequestResponseHolder holder = new HttpRequestResponseHolder((HttpServletRequest) request, (HttpServletResponse) response);
        SecurityContext context = httpSessionSecurityContextRepository.loadContext(holder);

        httpSessionSecurityContextRepository.saveContext(context, holder.getRequest(), holder.getResponse());
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
