/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.configuration;

import allegro.domain.repositories.UserRepository;
import allegro.domain.repositories.advices.AfterDatabaseAccess;
import allegro.domain.repositories.advices.BeforeDatabaseAccess;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author arek
 */
@Configuration
public class AdvicesConfig {

    @Bean
    @Scope("prototype")
    BeforeDatabaseAccess beforeDatabaseAccess() {
        return new BeforeDatabaseAccess();
    }

    @Bean
    @Scope("prototype")
    AfterDatabaseAccess afterDatabaseAccess() {
        return new AfterDatabaseAccess();
    }

    @Autowired
    UserRepository repository;

    @Bean("userRepositoryProxy")
    @Primary
    @Scope("prototype")
    ProxyFactoryBean userRepositoryProxyFactoryBean() {
        ProxyFactoryBean proxy = new ProxyFactoryBean();

        proxy.setProxyTargetClass(true);
        proxy.setTarget(this.repository);
        proxy.setInterceptorNames("beforeDatabaseAccess", "afterDatabaseAccess");

        return proxy;
    }

}
