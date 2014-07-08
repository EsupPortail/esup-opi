package org.esupportail.opi.web.candidat.services.security;

import fj.F;
import fj.data.Either;
import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.domain.beans.user.Gestionnaire;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.text.SimpleDateFormat;
import java.util.*;

import static fj.data.Either.reduce;
import static java.lang.String.format;


public class CandidatService implements UserDetailsService {

    private final DomainService domainService;

    public CandidatService(DomainService domainService) {
        this.domainService = domainService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // l'utilisateur essaye de se connecter via le formulaire
        final Individu individu = domainService.getIndividu(username, null);
        if (individu != null) {
            return new LoggedUser(individu);
        }

        // l'utilisateur essaye de se connecter via le sso
        final User user = domainService.getUser(username);
        if (user != null) {
            return new LoggedUser(user);
        }
        throw new UsernameNotFoundException(format("No user with %s as username was found.", username));
    }


    public static class LoggedUser implements UserDetails {

        private static final long serialVersionUID = 3576780846826105391L;

        public static final String ROLE_CANDIDAT = "ROLE_CANDIDAT";
        public static final String ROLE_GESTIONNAIRE = "ROLE_GESTIONNAIRE";

        private final Either<Gestionnaire, Individu> eitherIndividu;

        public LoggedUser(User user) {
            this.eitherIndividu = user instanceof Individu ?
                    Either.<Gestionnaire, Individu>right((Individu) user) :
                    Either.<Gestionnaire, Individu>left((Gestionnaire) user);
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return reduce(eitherIndividu
                    .left().map(new F<Gestionnaire, List<GrantedAuthority>>() {
                        public List<GrantedAuthority> f(Gestionnaire g) {
                            final List<GrantedAuthority> ga = new ArrayList<>();
                            ga.add(new SimpleGrantedAuthority(ROLE_GESTIONNAIRE));
                            return ga;
                        }
                    })
                    .right().map(new F<Individu, List<GrantedAuthority>>() {
                        public List<GrantedAuthority> f(Individu i) {
                            final List<GrantedAuthority> ga = new ArrayList<>();
                            ga.add(new SimpleGrantedAuthority(ROLE_CANDIDAT));
                            return ga;
                        }
                    }));
        }

        @Override
        public String getPassword() {
            return reduce(eitherIndividu
                    .left().map(new F<Gestionnaire, String>() {
                        public String f(Gestionnaire g) {
                            return "";
                        }
                    })
                    .right().map(new F<Individu, String>() {
                        public String f(Individu i) {
                            final SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
                            return sdf.format(i.getDateNaissance());
                        }
                    }));
        }

        @Override
        public String getUsername() {
            return reduce(eitherIndividu
                    .left().map(new F<Gestionnaire, String>() {
                        public String f(Gestionnaire g) {
                            return g.getLogin();
                        }
                    })
                    .right().map(new F<Individu, String>() {
                        public String f(Individu i) {
                            return i.getNumDossierOpi();
                        }
                    }));
        }

        @Override
        public boolean isAccountNonExpired() {
            return reduce(eitherIndividu
                    .left().map(new F<Gestionnaire, Boolean>() {
                        public Boolean f(Gestionnaire g) {
                            Calendar cal = Calendar.getInstance();
                            cal.set(Calendar.HOUR_OF_DAY, 0);
                            cal.set(Calendar.MINUTE, 0);
                            cal.set(Calendar.SECOND, 0);
                            return g.getDateFinValidite() == null ||
                                    g.getDateFinValidite().after(cal.getTime());
                        }
                    })
                    .right().map(new F<Individu, Boolean>() {
                        public Boolean f(Individu i) {
                            return true;
                        }
                    }));
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }

        public boolean isGestionnaire() {
            return eitherIndividu.isLeft();
        }

        public boolean isCandidat() {
            return eitherIndividu.isRight();
        }
    }
}
