package org.esupportail.catapp.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.ViewRendererServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebApp implements WebApplicationInitializer {
    public void onStartup(ServletContext servletContext)  {
        // ############### Parameters
        servletContext.setInitParameter("webAppRootKey", "esup-catapp-portlet");

        // ############### Spring app context
        final AnnotationConfigWebApplicationContext springContext =
                new AnnotationConfigWebApplicationContext();
        springContext.scan("org.esupportail.catapp.config");

        // ############### Listeners
        servletContext.addListener(new ContextLoaderListener(springContext));

        // ############### Servlets
        final ServletRegistration.Dynamic viewRendererServlet =
                servletContext.addServlet("ViewRendererServlet", ViewRendererServlet.class);
        viewRendererServlet.setLoadOnStartup(1);
        viewRendererServlet.addMapping("/WEB-INF/servlet/view");
    }
}
