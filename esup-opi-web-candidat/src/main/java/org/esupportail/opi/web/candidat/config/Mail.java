package org.esupportail.opi.web.candidat.config;

import org.esupportail.commons.mail.CachingEmailSmtpService;
import org.esupportail.commons.mail.SimpleSmtpService;
import org.esupportail.commons.mail.SmtpService;
import org.esupportail.commons.mail.model.SmtpServerData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;

@Configuration
@Import({ Cache.class })
public class Mail {
	
	@Value("${smtp.host}")
	private String smtpHost;

	@Value("${smtp.port}")
	private Integer smtpPort;

	@Value("${smtp.user}")
	private String smtpUser;

	@Value("${smtp.password}")
	private String smtpPwd;

	@Value("${smtp.fromEmail}")
	private String fromEmail;
	
	@Value("${smtp.fromName}")
	private String fromName;

	@Value("${smtp.interceptAll}")
	private boolean interceptAll;

	@Value("${smtp.interceptEmail}")
	private String interceptEmail;

	@Value("${smtp.interceptName}")
	private String interceptName;

	@Value("${smtp.charset}")
	private String charset;
	
	@Value("${exceptionHandling.email}")
	private String emailException;
	
	@Inject
	@Named("mailCache")
	private org.springframework.cache.Cache mailCache;
	
	@Bean
	public SmtpServerData smtpServerNew() {
		return SmtpServerData.builder()
                .host(smtpHost)
                .port(smtpPort)
                .user(smtpUser)
                .password(smtpPwd)
                .build();
	}
	
	@Bean(destroyMethod = "shutdown")
	public SmtpService smtpServiceNew() throws UnsupportedEncodingException {
		final InternetAddress from =
				new InternetAddress(fromEmail, fromName);
		final InternetAddress intercept = 
				new InternetAddress(interceptEmail, interceptName);
		return CachingEmailSmtpService.create(
                SimpleSmtpService
                        .builder(from, null, intercept)
                        .interceptAll(interceptAll)
                        .charset(charset)
                        .server(smtpServerNew())
                        .build(),
                mailCache);
    }
	
    @Bean
    public InternetAddress exceptionAddress() throws AddressException {
        return new InternetAddress(emailException);
    }
}
