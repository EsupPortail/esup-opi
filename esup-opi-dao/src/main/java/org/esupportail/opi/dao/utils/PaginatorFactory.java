package org.esupportail.opi.dao.utils;

import org.esupportail.commons.dao.HibernateTransactionManager;
import org.esupportail.opi.domain.beans.user.Individu;
import org.hibernate.Session;

import com.mysema.query.jpa.hibernate.HibernateQuery;

import fj.P1;

public class PaginatorFactory {

    private final Paginator<HibernateQuery, Individu> indPag;
    
    private PaginatorFactory(final HibernateTransactionManager txm) {
        indPag = new Paginator<HibernateQuery, Individu>(new P1<Session>() {
                     public Session _1() {
                         return txm.getSessionFactory().getCurrentSession();
                     }}) {};
    }
    
    public static PaginatorFactory pagFact(HibernateTransactionManager tx) {
        return new PaginatorFactory(tx);
    }
    
    public Paginator<HibernateQuery, Individu> indPaginator() {
        return indPag;
    }
    

}
