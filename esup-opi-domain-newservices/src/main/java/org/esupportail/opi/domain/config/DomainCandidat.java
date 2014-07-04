package org.esupportail.opi.domain.config;

import org.esupportail.opi.dao.DaoService;
import org.esupportail.opi.dao.IndividuDaoService;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.domain.services.DomainCandidatService;
import org.esupportail.opi.domain.services.DomainCandidatServiceImpl;
import org.springframework.context.annotation.*;

import javax.inject.Inject;

@Configuration
@Lazy
public class DomainCandidat {

    @Inject
    DaoService daoService;

    @Inject
    IndividuDaoService individuDaoSrv;

    @Inject
    DomainApoService apoService;

    @Inject
    DomainService domainService;

    @Bean
    public DomainCandidatService domainCandidatService() {
        return DomainCandidatServiceImpl.domainCandidatServiceImpl(daoService, individuDaoSrv, apoService, domainService);
    }

}
