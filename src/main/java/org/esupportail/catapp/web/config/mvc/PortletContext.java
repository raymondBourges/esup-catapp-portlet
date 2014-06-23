package org.esupportail.catapp.web.config.mvc;

import org.esupportail.catapp.config.AppContext;
import org.esupportail.catapp.domain.service.ICatAppServ;
import org.esupportail.catapp.web.controllers.CatalogueController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Pour faire du springMVC en portlet par annotations
 */
@Configuration
@Import(AppContext.class)
@EnableWebMvc
@ComponentScan(basePackages = { "org.esupportail.catapp" })
public class PortletContext extends WebMvcConfigurerAdapter {

    @Autowired
    private ICatAppServ catAppSrev;

    @Bean
    CatalogueController catalogueController() { return CatalogueController.catalogueController(catAppSrev); }

}
