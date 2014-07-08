package org.esupportail.opi.web.candidat.config;

import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.web.candidat.config.rewrite.Navigation;
import org.esupportail.opi.web.candidat.services.security.AuthSuccessHandler;
import org.esupportail.opi.web.candidat.services.security.CandidatService;
import org.jasig.cas.client.validation.Cas20ProxyTicketValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.header.writers.CacheControlHeadersWriter;
import org.springframework.security.web.header.writers.HstsHeaderWriter;
import org.springframework.security.web.header.writers.XContentTypeOptionsHeaderWriter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import static java.lang.String.format;
import static org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter.*;

@Configuration
@EnableWebSecurity
public class Security {

    @Configuration
    @Order(1)
    public static class FormSecurity extends WebSecurityConfigurerAdapter {

        @Inject
        private DomainService domainService;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService());
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring()
                    .antMatchers("/javax.faces.resource/**");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http
                .authorizeRequests()
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/")
                    .successHandler(new AuthSuccessHandler())
                    .permitAll();

            http.sessionManagement().invalidSessionUrl("/");

            http.logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .deleteCookies("JSESSIONID");
        }

        @Override
        protected UserDetailsService userDetailsService() {
            return new CandidatService(domainService);
        }
    }

    public static class CasSecurity extends WebSecurityConfigurerAdapter {

        @Inject
        private DomainService domainService;

        @Value("${cas.service}")
        private String CAS_URL = "";

        @Value("${serviceName}")
        private String SERVICE_URL = "";

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService());
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring()
                    .antMatchers("/javax.faces.resource/**");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.addFilter(casFilter());

            http.exceptionHandling()
                    .authenticationEntryPoint(casEntryPoint());

            http.anonymous().disable();

            http.antMatcher("/cas/**")
                    .authorizeRequests().anyRequest().authenticated();

            http.sessionManagement()
                    .invalidSessionUrl(SERVICE_URL);

            http.logout().logoutUrl("/logout").deleteCookies("JSESSIONID");
        }

        private CasAuthenticationFilter casFilter() throws Exception {
            CasAuthenticationFilter filter = new CasAuthenticationFilter();
            filter.setServiceProperties(serviceProperties());
            filter.setAuthenticationManager(authenticationManager());
            filter.setAuthenticationSuccessHandler(new AuthSuccessHandler());
            return filter;
        }

        @Override
        protected AuthenticationManager authenticationManager() throws Exception {
            return new AuthenticationManager() {
                public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                    return casAuthenticationProvider().authenticate(authentication);
                }
            };
        }

        @Bean
        public CasAuthenticationProvider casAuthenticationProvider() {
            CasAuthenticationProvider provider = new CasAuthenticationProvider();
            provider.setServiceProperties(serviceProperties());
            provider.setKey("an_awesome_secret_key");
            provider.setTicketValidator(new Cas20ProxyTicketValidator(CAS_URL));
            provider.setAuthenticationUserDetailsService(
                    new UserDetailsByNameServiceWrapper<CasAssertionAuthenticationToken>(userDetailsService()));
            return provider;
        }

        @Override
        protected UserDetailsService userDetailsService() {
            return new CandidatService(domainService);
        }

        private CasAuthenticationEntryPoint casEntryPoint() {
            CasAuthenticationEntryPoint entryPoint = new CasAuthenticationEntryPoint();
            entryPoint.setLoginUrl(format("%s/login", CAS_URL));
            entryPoint.setServiceProperties(serviceProperties());
            return entryPoint;
        }


        private ServiceProperties serviceProperties() {
            ServiceProperties sp = new ServiceProperties();
            sp.setSendRenew(false);
            sp.setService(format("%s/j_spring_cas_security_check", SERVICE_URL));
            return sp;
        }
    }
}
