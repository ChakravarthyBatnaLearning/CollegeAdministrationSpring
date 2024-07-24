package com.college.student.logging;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class LoggingFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);
    private FilterConfig filterConfig;

    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String remoteAddr = httpServletRequest.getRemoteAddr();
        String method = httpServletRequest.getMethod();
        String URI = httpServletRequest.getRequestURI();
        long startTime = System.currentTimeMillis();
        filterChain.doFilter(servletRequest, servletResponse);
        long endTime = System.currentTimeMillis();
        long totalTimeTaken = (endTime - startTime);
        double totalTimeTakenInSeconds = totalTimeTaken / 1000.0;

        //this goes to app.log
        logger.info("Remote Address : {}, Method : {}, URI :{}, TimeTaken : {}", remoteAddr, method, URI, totalTimeTakenInSeconds);

        //this goes to tomcat logs which is localhost log
        filterConfig.getServletContext().log("Remote Address : " + remoteAddr + "Method : " + method + " URI : " +
                URI + " TimeTaken : " + totalTimeTakenInSeconds);
    }
}
