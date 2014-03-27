package org.esupportail.catapp.config;

import org.esupportail.catapp.domain.service.CatAppServImpl;
import org.esupportail.catapp.domain.service.ICatAppServ;
import org.esupportail.catapp.domain.service.mocks.MockCatAppService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.net.MalformedURLException;

@Configuration
public class AppContext {

    @Value("${ws.domain.path}")
    private String wsDomainPath;

    @Value("${ws.user.path}")
    private String wsUserPath;

    @Value("${ws.application.path}")
    private String wsAppPath;

//    @Value("${ws.catApp.url}")
//    private String wsCatAppServiceURL;


    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer() {{
            setIgnoreUnresolvablePlaceholders(true);
            setIgnoreResourceNotFound(true);
            setLocations(new Resource[] {
                    new FileSystemResource("file:${config.location}"),
                    new ClassPathResource("/META-INF/default.properties")
            });
        }};
    }

    @Bean
    public InternalResourceViewResolver jspViewResolver() {
       return new InternalResourceViewResolver() {{
           setCache(true);
           setViewClass(JstlView.class);
           setPrefix("/WEB-INF/stylesheets/");
           setSuffix(".jsp");
       }};
    }

//    @Bean
//    public ICatAppServ catAppService() throws MalformedURLException {
//        return new MockCatAppService();
//    }

    @Bean
    public ICatAppServ catAppService() throws MalformedURLException {
        return CatAppServImpl.CatAppService(wsDomainPath, wsUserPath, wsAppPath);
    }
}