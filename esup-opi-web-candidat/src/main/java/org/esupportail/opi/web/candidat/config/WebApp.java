package org.esupportail.opi.web.candidat.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.util.Log4jConfigListener;

import javax.servlet.ServletContext;

public class WebApp implements WebApplicationInitializer {

    private static final String LOG4J_PATH = "classpath:log4j.properties";

    @Override
    public void onStartup(ServletContext servletContext) {

        // Hack to prevent Tomcat from converting null to zero
        System.setProperty("org.apache.el.parser.COERCE_TO_ZERO", "false");

        // ######### Parameters

        servletContext.setInitParameter("log4jConfigLocation", LOG4J_PATH);
        servletContext.setInitParameter("com.sun.faces.allowTextChildren", "true");
        servletContext.setInitParameter("javax.faces.DEFAULT_SUFFIX", ".xhtml");
        servletContext.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", "true");
        servletContext.setInitParameter("javax.faces.DATETIMECONVERTER_DEFAULT_TIMEZONE_IS_SYSTEM_TIMEZONE", "true");
        servletContext.setInitParameter("org.apache.myfaces.ERROR_HANDLING", "false");
        servletContext.setInitParameter("org.apache.myfaces.SERIALIZE_STATE_IN_SESSION", "false"); // for viewscope
        servletContext.setInitParameter("primefaces.THEME", "bootstrap");

        createRootContext(servletContext);
    }

    private WebApplicationContext createRootContext(ServletContext servletContext) {

        AnnotationConfigWebApplicationContext root = new AnnotationConfigWebApplicationContext();
        root.register(ContextConfig.class);
        root.refresh();

        servletContext.addListener(new ContextLoaderListener(root));
        servletContext.addListener(new RequestContextListener());
        servletContext.addListener(new Log4jConfigListener());

        return root;
    }
}
