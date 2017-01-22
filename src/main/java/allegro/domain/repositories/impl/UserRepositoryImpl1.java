/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.domain.repositories.impl;


public class UserRepositoryImpl1  {
//
//    private static final Logger LOG = Logger.getLogger(UserRepositoryImpl1.class.getName());
//
//    @Autowired
//    private SessionFactory sessionFactory;
//
//    @Override
//    public User findByUserName(String userName) {
//        User u;
//        Session session;
//        try {
//            session = this.sessionFactory.getCurrentSession();
//        } catch (HibernateException ex) {
//            LOG.log(Level.SEVERE, "an exception was thrown", ex);
//            session = this.sessionFactory.openSession();
//        }
//
//        try {
//
//            Query q = sessionFactory.getCurrentSession()
//                    .createQuery("from User where username= :login")
//                    .setParameter("login", userName)
//                    .setMaxResults(1);
//
//            session.getTransaction().begin();
//            u = (User) q.uniqueResult();
//            session.getTransaction().commit();
//
//            return u;
//
//        } catch (HibernateException ex) {
//            LOG.log(Level.SEVERE, "an exception was thrown", ex);
//            return null;
//
//        } finally {
//            session.close();
//        }
//    }
//
//    @Override
//    public void addNewUser(User user) {
//
//        Session session;
//        try {
//            session = this.sessionFactory.getCurrentSession();
//        } catch (HibernateException ex) {
//            LOG.log(Level.SEVERE, "an exception was thrown", ex);
//            session = this.sessionFactory.openSession();
//        }
//
//        try {
//
//            session.getTransaction().begin();
//            session.save(user);
//            session.getTransaction().commit();
//
//        } catch (HibernateException ex) {
//            LOG.log(Level.SEVERE, "an exception was thrown", ex);
//
//        } finally {
//            session.close();
//        }
//    }
//
//    @Override
//    public void addNewUserRole(UserRole userRole) {
//        Session session;
//        try {
//            session = this.sessionFactory.getCurrentSession();
//        } catch (HibernateException ex) {
//            LOG.log(Level.SEVERE, "an exception was thrown", ex);
//            session = this.sessionFactory.openSession();
//        }
//
//        try {
//
//            session.getTransaction().begin();
//            session.save(userRole);
//            session.getTransaction().commit();
//
//        } catch (HibernateException ex) {
//            LOG.log(Level.SEVERE, "an exception was thrown", ex);
//
//        } finally {
//            session.close();
//        }
//    }
//
//    @Override
//    public void addNewVerificationToken(VerificationToken token) {
//        Session session;
//        try {
//            session = this.sessionFactory.getCurrentSession();
//        } catch (HibernateException ex) {
//            LOG.log(Level.SEVERE, "an exception was thrown", ex);
//            session = this.sessionFactory.openSession();
//        }
//
//        try {
//
//            session.getTransaction().begin();
//            session.save(token);
//            session.getTransaction().commit();
//
//        } catch (HibernateException ex) {
//            LOG.log(Level.SEVERE, "an exception was thrown", ex);
//
//        } finally {
//            session.close();
//        }
//    }
//
//    @Override
//    public int enableUserAccount(String username) {
//        int result = 0;
//        Session session;
//        try {
//            session = this.sessionFactory.getCurrentSession();
//        } catch (HibernateException ex) {
//            LOG.log(Level.SEVERE, "an exception was thrown", ex);
//            session = this.sessionFactory.openSession();
//        }
//
//        try {
//
//            Query q = session.createQuery("UPDATE User set enabled = :enabled WHERE username = :login");
//            q.setParameter("enabled", true);
//            q.setParameter("login", username);
//            q.setMaxResults(1);
//
//            session.getTransaction().begin();
//
//            result = q.executeUpdate();
//
//            session.getTransaction().commit();
//
//            return result;
//
//        } catch (HibernateException ex) {
//            LOG.log(Level.SEVERE, "an exception was thrown", ex);
//            return result;
//        } finally {
//            session.close();
//        }
//    }
//
//    @Override
//    public VerificationToken findToken(String token) {
//
//        Session session;
//        try {
//            session = this.sessionFactory.getCurrentSession();
//        } catch (HibernateException ex) {
//            LOG.log(Level.SEVERE, "an exception was thrown", ex);
//            session = this.sessionFactory.openSession();
//        }
//
//        try {
//            Query q = sessionFactory.getCurrentSession()
//                    .createQuery("from VerificationToken where token = :token")
//                    .setParameter("token", token)
//                    .setMaxResults(1);
//
//            session.getTransaction().begin();
//            VerificationToken verificationToken = (VerificationToken) q.uniqueResult();
//            session.getTransaction().commit();
//
//            return verificationToken;
//
//        } catch (HibernateException ex) {
//            LOG.log(Level.SEVERE, "an exception was thrown", ex);
//            return null;
//
//        } finally {
//            session.close();
//        }
//    }
//
//    @Override
//    public void addNewUserSettings(UserOptions options) {
//        Session session;
//        try {
//            session = this.sessionFactory.getCurrentSession();
//        } catch (HibernateException ex) {
//            LOG.log(Level.SEVERE, "an exception was thrown", ex);
//            session = this.sessionFactory.openSession();
//        }
//
//        try {
//
//            session.getTransaction().begin();
//            session.save(options);
//            session.getTransaction().commit();
//
//        } catch (HibernateException ex) {
//            LOG.log(Level.SEVERE, "an exception was thrown", ex);
//
//        } finally {
//            session.close();
//        }
//    }
//
//    @Override
//    public void updateUserSettings(UserOptions options) {
//        Session session;
//        try {
//            session = this.sessionFactory.getCurrentSession();
//        } catch (HibernateException ex) {
//            session = this.sessionFactory.openSession();
//            LOG.log(Level.SEVERE, "an exception was thrown", ex);
//        }
//
//        try {
//
//            session.getTransaction().begin();
//            session.update(options);
//            session.getTransaction().commit();
//
//        } catch (HibernateException ex) {
//            LOG.log(Level.SEVERE, "an exception was thrown", ex);
//
//        } finally {
//            session.close();
//        }
//    }
//
//    @Override
//    public UserOptions findUserSettings(String userName) {
//
//        Session session;
//        try {
//            session = this.sessionFactory.getCurrentSession();
//        } catch (HibernateException ex) {
//            LOG.log(Level.SEVERE, "an exception was thrown", ex);
//            session = this.sessionFactory.openSession();
//        }
//
//        try {
//            Query q = sessionFactory.getCurrentSession()
//                    .createQuery("from UserOptions where username= :login")
//                    .setParameter("login", userName)
//                    .setMaxResults(1);
//
//            session.getTransaction().begin();
//            UserOptions options = (UserOptions) q.uniqueResult();
//            session.getTransaction().commit();
//
//            return options;
//
//        } catch (HibernateException ex) {
//            LOG.log(Level.SEVERE, "an exception was thrown", ex);
//            return null;
//
//        } finally {
//            session.close();
//        }
//    }
}
