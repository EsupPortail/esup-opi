package org.esupportail.opi.batch;

import com.mysema.query.jpa.hibernate.HibernateQuery;
import com.mysema.query.types.EntityPath;
import com.mysema.query.types.path.EntityPathBase;
import fj.Effect;
import fj.F;
import fj.Unit;
import fj.data.Either;
import org.esupportail.opi.domain.beans.mails.MailContent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import static fj.Bottom.error;
import static fj.data.Either.left;
import static fj.data.Either.right;
import static fj.data.Option.fromNull;
import static fj.data.Stream.iterableStream;

public class PatchMailsContent {
    public static void main(String[] args) {
        // Helper Class
        final class Dao {
            final TransactionTemplate txTemplate;
            final HibernateTemplate hibTemplate;

            Dao(HibernateTransactionManager txm, HibernateTemplate template) {
                txTemplate = new TransactionTemplate(txm);
                hibTemplate = template;
            }

            HibernateQuery from(EntityPath<?>... ents) {
                return new HibernateQuery(hibTemplate.getSessionFactory().getCurrentSession()).from(ents);
            }

            final F<MailContent, MailContent> treatContent = new F<MailContent, MailContent>() {
                public MailContent f(final MailContent mailContent) {
                    mailContent.setBody(mailContent.getBody()
                            .replaceAll("commissionPojo", "commissionDTO")
                            .replaceAll("adressePojo", "adresseDTO"));
                    return mailContent;
                }
            };

            final F<MailContent, Unit> save = new Effect <MailContent>() {
                public void e(MailContent mailContent) {
                    hibTemplate.save(mailContent);
                }
            }.e();

            Either<Throwable, Unit> run() {
                return txTemplate.execute(new TransactionCallback<Either<Throwable, Unit>>() {
                    public Either<Throwable, Unit> doInTransaction(TransactionStatus status) {
                        try {
                            final EntityPathBase<MailContent> mail = new EntityPathBase<>(MailContent.class, "mail");
                            return right(iterableStream(from(mail).list(mail)).foreach(treatContent.andThen(save)));
                        } catch (Throwable t) {
                            return left(t);
                        }
                    }
                });
            }
        }

        // Main program

        if (fromNull(System.getProperty("config.location")).isNone())
            error("Le paramètre 'config.location' n'est pas positionné !");

        final ApplicationContext context =
                new ClassPathXmlApplicationContext("properties/applicationContext.xml");

        final HibernateTransactionManager txm = context.getBean("txManager", HibernateTransactionManager.class);
        final HibernateTemplate template = context.getBean("jdbcHibernateTemplate", HibernateTemplate.class);

        for (Throwable t: new Dao(txm, template).run().left())
            throw new Error("Un problème est survenu lors de l'opération", t);

        System.out.println("Opération effectuée");
    }
}
