package hu.psprog.dev.jwtdemo.web.init;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

public class MainInitializer implements WebApplicationInitializer {

    private static final String CONFIG_LOCATION = "hu.psprog.dev.jwtdemo.web.config";
    private static final String FILTER_CHAIN_SECURITY = "springSecurityFilterChain";
    private static final String REST_SERVLET_NAME = "rest-dispatcher";
    private static final String REST_SERVLET_ROOT = "/api/*"; 
    
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.setConfigLocation(CONFIG_LOCATION);
        
        DispatcherServlet dispatcher = new DispatcherServlet(appContext);
        ServletRegistration.Dynamic servlet = servletContext.addServlet(REST_SERVLET_NAME, dispatcher);
        
        servlet.addMapping(REST_SERVLET_ROOT);
        servlet.setAsyncSupported(true);
        servlet.setLoadOnStartup(1);
        
        FilterRegistration securityFilterChain = servletContext.addFilter(FILTER_CHAIN_SECURITY, new DelegatingFilterProxy(FILTER_CHAIN_SECURITY));
        securityFilterChain.addMappingForServletNames(null, false, REST_SERVLET_NAME);
    }
}
