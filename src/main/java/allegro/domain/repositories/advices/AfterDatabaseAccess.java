/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.domain.repositories.advices;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.TransactionException;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author arek
 */
public class AfterDatabaseAccess implements AfterReturningAdvice {

    private static final Logger LOG = Logger.getLogger(AfterDatabaseAccess.class.getName());

    @Autowired
    SessionFactory sessionFactory;

    /**
     * We are trying to close transaction and session here and logs errors; also
     * we will log all access request to database
     *
     * @param arg0
     * @param method
     * @param arg2
     * @param arg3
     * @throws Throwable
     */
    @Override
    public void afterReturning(Object arg0, Method method, Object[] arg2, Object arg3) throws Throwable {

        try {
            LOG.log(Level.SEVERE, "Próba zakończenia transakcji ");
            if (this.sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE) {
                this.sessionFactory.getCurrentSession().getTransaction().commit();
                LOG.log(Level.SEVERE, "Pomyślnie zakonczono transakcje");
            } else {
                LOG.log(Level.SEVERE, "Transakcja jest już zakonczona");
            }

            try {
                LOG.log(Level.SEVERE, "Próba zamknięcia sesji ");
                if (this.sessionFactory.getCurrentSession().isOpen()) {
                    this.sessionFactory.getCurrentSession().close();
                    LOG.log(Level.SEVERE, "Pomyśnie zamknięto sesję");
                } else {
                    LOG.log(Level.SEVERE, "Sesja jest już zamknięta");
                }

            } catch (HibernateException ex) {
                LOG.log(Level.SEVERE, "Zamknięcie siesji nie powiodło się sesja jest już zamknięta");

            }

        } catch (HibernateException ex) {
            LOG.log(Level.SEVERE, "Zakończenie transakcji nie powiodło się, transakcja jest już zamknięta");
        }

    }
}
