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
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author arek
 */
public class BeforeDatabaseAccess implements MethodBeforeAdvice {

    private static final Logger LOG = Logger.getLogger(BeforeDatabaseAccess.class.getName());

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void before(Method method, Object[] arg1, Object arg2) throws Throwable {

        try {
            LOG.log(Level.SEVERE, "Próba otwarcia sesji ");
            this.sessionFactory.openSession();
            LOG.log(Level.SEVERE, "Otrworzono nowąsesje");

            try {
                LOG.log(Level.SEVERE, "Próba rozpoczęcia transakcji ");
                this.sessionFactory.openSession().getTransaction().begin();
                LOG.log(Level.SEVERE, "Transakcja rozpoczęta");

            } catch (TransactionException ex) {
                LOG.log(Level.SEVERE, "Rozpoczęcie transakcji nie powiodło się ", ex);
            }

        } catch (HibernateException ex) {
            LOG.log(Level.SEVERE, "Otwarcie siesji nie powiodło się ", ex);
            this.sessionFactory.openSession();
        }

    }

}
