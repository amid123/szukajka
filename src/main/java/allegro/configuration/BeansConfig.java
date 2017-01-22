/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.configuration;

import allegro.domain.repositories.UserRepository;
import allegro.domain.repositories.impl.UserRepositoryImpl;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Configuration class contains config for hibernate and partialy of spring, in
 * additional it imports security congiguration enable transactional , web
 * security configure properties sources and enable wem mvc support.
 *
 *
 * @author arek
 */
@EnableWebMvc
@ComponentScan({"allegro"})
@Import({SecurityConfig.class})
@EnableTransactionManagement
@EnableWebSecurity
@PropertySource({"classpath:database.properties",
    "classpath:app.properties"})
@Configuration
public class BeansConfig {

    @Value("${jdbc.driverClassName}")
    private String driverClassName;
    @Value("${jdbc.url}")
    private String jdbcURL;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;

    @Autowired
    Environment environment;

    @Bean(name = "dataSource")
    public DriverManagerDataSource dataSource() {

        DriverManagerDataSource dataSources = new DriverManagerDataSource();
        dataSources.setDriverClassName(this.driverClassName);
        dataSources.setUrl(this.jdbcURL);
        dataSources.setUsername(this.username);
        dataSources.setPassword(this.password);
        return dataSources;
    }

    /**
     * Factoring hibernate session factory
     *
     * @return SessionFactory singleton instance
     */
    @Bean
    public SessionFactory sessionFactory() {
        Properties properties;
        try {

            properties = PropertiesLoaderUtils.loadAllProperties("database.properties");

            LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource());
            builder.scanPackages("allegro.domain").addProperties(properties);

            return builder.buildSessionFactory();
        } catch (IOException ex) {

            Logger.getLogger(BeansConfig.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Factoring propertySourcePlaceHolderConfigurer for using EL expresions
     * like ${} in @Value annotaions and some other.
     *
     * @return
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * Factory hibernate tx manager
     *
     * @return tx manager
     */
    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
        hibernateTransactionManager.setSessionFactory(this.sessionFactory());
        return hibernateTransactionManager;
    }

    /**
     * Factoring message source for internationalization support
     *
     * @return
     */
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        String[] resources = {"classpath:messages_en"};
        messageSource.setBasenames(resources);
        return messageSource;
    }

    @Bean
    UserRepository userRepository() {
        return new UserRepositoryImpl();
    }

    @Bean
    Authentication authenticationContext() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
