package org.esupportail.opi.web.candidat.services.security;

import org.esupportail.opi.web.candidat.config.rewrite.Navigation;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.String.format;
import static org.esupportail.opi.web.candidat.services.security.CandidatService.LoggedUser;

public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        final LoggedUser loggedUser = (LoggedUser) authentication.getPrincipal();
        setDefaultTargetUrl(format("%s?dossier=%s", Navigation.COORDONNEES, loggedUser.getUsername()));
        setAlwaysUseDefaultTargetUrl(true);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
