/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.domain.repositories.impl;

import allegro.domain.User;
import allegro.domain.UserSettings;
import allegro.domain.UserRole;
import allegro.domain.VerificationToken;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import allegro.domain.repositories.UserRepository;
import java.util.logging.Logger;
import javax.transaction.Transactional;
import org.hibernate.Query;

@Transactional
public class UserRepositoryImpl implements UserRepository {

    private static final Logger LOG = Logger.getLogger(UserRepositoryImpl.class.getName());

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User findByUserName(String userName) {
        User u;

        Query q = sessionFactory.getCurrentSession()
                .createQuery("from User where username= :login")
                .setParameter("login", userName)
                .setMaxResults(1);

        u = (User) q.uniqueResult();
        return u;
    }

    @Override
    public void addNewUser(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public void addNewUserRole(UserRole userRole) {
        sessionFactory.getCurrentSession().save(userRole);
    }

    @Override
    public void addNewVerificationToken(VerificationToken token) {
        sessionFactory.getCurrentSession().save(token);
    }

    @Override
    public int enableUserAccount(String username) {

        Query q = sessionFactory.getCurrentSession().createQuery("UPDATE User set enabled = :enabled WHERE username = :login");
        q.setParameter("enabled", true);
        q.setParameter("login", username);
        q.setMaxResults(1);

        return q.executeUpdate();
    }

    @Override
    public VerificationToken findToken(String token) {

        Query q = sessionFactory.getCurrentSession()
                .createQuery("from VerificationToken where token = :token")
                .setParameter("token", token)
                .setMaxResults(1);

        return (VerificationToken) q.uniqueResult();
    }

    @Override
    public void addNewUserSettings(UserSettings options) {
        this.sessionFactory.getCurrentSession().save(options);
    }

    @Override
    public void updateUserSettings(UserSettings options) {
        this.sessionFactory.getCurrentSession().update(options);
    }

    @Override
    public UserSettings findUserSettings(String userName) {

        Query q = sessionFactory.getCurrentSession()
                .createQuery("from UserOptions where username= :login")
                .setParameter("login", userName)
                .setMaxResults(1);

        return (UserSettings) q.uniqueResult();
    }
}
