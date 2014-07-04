package org.esupportail.opi.web.candidat.config;

import org.esupportail.opi.web.candidat.utils.exceptions.ExceptionUtils;
import org.esupportail.opi.web.candidat.utils.jsf.ViewScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

@Configuration
@ImportResource({"classpath*:/META-INF/archives/archives.xml",
        "classpath*:/META-INF/auth/auth.xml",
        "classpath*:/META-INF/cache/cache.xml",
        "classpath*:/META-INF/esup-opi-dao-dao.xml",
        "classpath*:/META-INF/domain/domain.xml",
//        "exceptionHandling/exceptionHandling.xml",
        "classpath*:/META-INF/i18n/i18n.xml",
        "classpath*:/META-INF/ldap/ldap.xml",
        "classpath*:/META-INF/misc/abstractBeans.xml",
        "classpath*:/META-INF/esup-opi-domain-services-application.xml",
//        "portal/portal.xml",
        "classpath*:/META-INF/remote/remote.xml",
//        "classpath*:/META-INF/export/castor.xml",
        "classpath*:/META-INF/mails/mails.xml",
        "classpath*:/META-INF/mails/mailConvocations.xml",
        "classpath*:/META-INF/smtp/smtp.xml",
        "classpath*:/META-INF/init/init.xml",
        "classpath*:/META-INF/parameters/accessType.xml",
        "classpath*:/META-INF/urlGeneration/urlGeneration.xml",
//        "web/managedAccess.xml",
        "classpath*:/META-INF/web/beans.xml"})
@Import({ ControllerConfig.class, Mail.class, Security.class })
public class ContextConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer(@Value("#{systemProperties['config.location']?:''}") String configLocation) {
        PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();

        Resource[] resources = new Resource[] {
                new ClassPathResource("/properties/defaults.properties")
        };

        if (!configLocation.isEmpty()) {
            final List<Resource> resourceList = new ArrayList<>(asList(resources));
            resourceList.add(new PathResource(configLocation));
            resources = resourceList.toArray(new Resource[resourceList.size()]);
        }

        pspc.setLocations(resources);
        pspc.setIgnoreUnresolvablePlaceholders(true);

        return pspc;
    }



    @Bean
    public static CustomScopeConfigurer customScopeConfigurer() {
        Map<String, Object> scopes = new HashMap<String, Object>() {{
            put("view", new ViewScope());
        }};
        CustomScopeConfigurer configurer = new CustomScopeConfigurer();
        configurer.setScopes(scopes);
        return configurer;
    }


    @Bean
    public ExceptionUtils exceptionUtils() {
        return ExceptionUtils.exceptionUtils;
    }
}
