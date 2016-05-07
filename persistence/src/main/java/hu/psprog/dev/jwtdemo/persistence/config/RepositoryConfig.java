package hu.psprog.dev.jwtdemo.persistence.config;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jndi.JndiTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories
public class RepositoryConfig {

    private static final String REPOSITORY_PACKAGE = "hu.psprog.dev.jwtdemo.persistence.repository";

    @Bean
    public DataSource dataSource() throws NamingException {
        
        JndiTemplate jndiTemplate = new JndiTemplate();
        DataSource dataSource = (DataSource)jndiTemplate.lookup("jdbc/mysql");
        
        return dataSource;
    }
    
    @Bean
    public EntityManagerFactory entityManagerFactory() throws NamingException {
        
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(false);
        
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan(REPOSITORY_PACKAGE);
        factory.setDataSource(dataSource());
        factory.setPersistenceProvider(vendorAdapter.getPersistenceProvider());
        factory.setJpaDialect(new HibernateJpaDialect());
        factory.afterPropertiesSet();
        
        return factory.getObject();
    }
    
    @Bean
    public PlatformTransactionManager transactionManager() throws NamingException {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        
        return txManager;
    }
}
