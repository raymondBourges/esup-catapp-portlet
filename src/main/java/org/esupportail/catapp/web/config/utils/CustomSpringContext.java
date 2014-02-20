package org.esupportail.catapp.web.config.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.portlet.context.ConfigurablePortletApplicationContext;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;

public final class CustomSpringContext extends AnnotationConfigWebApplicationContext
        implements ConfigurablePortletApplicationContext {
    private PortletContext portletContext;
    private PortletConfig portletConfig;

    { scan("org.esupportail.catapp.web.config.mvc"); }

    public void setPortletContext(PortletContext portletContext) {
        this.portletContext = portletContext;
    }

    public PortletContext getPortletContext() {
        return portletContext;
    }

    public void setPortletConfig(PortletConfig portletConfig) {
        this.portletConfig = portletConfig;
    }

    public PortletConfig getPortletConfig() {
        return portletConfig;
    }

    @Override
    public void setParent(ApplicationContext parent) {
        super.setParent(parent);
        setServletContext(((WebApplicationContext) parent).getServletContext());
    }

}
