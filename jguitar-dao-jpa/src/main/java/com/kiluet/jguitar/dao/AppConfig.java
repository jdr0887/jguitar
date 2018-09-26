package com.kiluet.jguitar.dao;

import java.io.File;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:dao.properties")
@ComponentScan({ "com.kiluet.jguitar.dao" })
@EnableTransactionManagement
public class AppConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driver"));

        File jguitarDir = new File(System.getProperty("user.home"), ".jguitar");
        jguitarDir.mkdirs();

        System.setProperty("derby.system.home", jguitarDir.getAbsolutePath());

        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.username"));
        dataSource.setPassword(env.getProperty("jdbc.password"));
        dataSource.setInitialSize(2);
        dataSource.setMaxTotal(20);
        dataSource.setMaxIdle(16);
        dataSource.setMinIdle(2);
        dataSource.setRemoveAbandonedTimeout(60);
        dataSource.setLogAbandoned(true);
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(new String[] { "com.kiluet.jguitar.dao.model" });

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Properties props = new Properties();
        props.put("javax.persistence.query.timeout", "300000");
        props.put("hibernate.enable_lazy_load_no_trans", "true");
        props.put("hibernate.hbm2ddl.auto", "update");
        props.put("hibernate.dialect", "org.hibernate.dialect.DerbyTenSevenDialect");
        props.put("hibernate.query.jpaql_strict_compliance", "true");
        props.put("hibernate.show_sql", "false");
        props.put("hibernate.format_sql", "true");
        props.put("hibernate.use_sql_comments", "false");
        props.put("hibernate.jdbc.batch_size", "100");
        props.put("hibernate.order_inserts", "true");
        props.put("hibernate.order_updates", "true");
        props.put("hibernate.jdbc.batch_versioned_data", "true");
        props.put("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory");
        props.put("hibernate.cache.use_second_level_cache", "false");
        props.put("hibernate.cache.use_query_cache", "false");

        em.setJpaProperties(props);

        return em;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

}
