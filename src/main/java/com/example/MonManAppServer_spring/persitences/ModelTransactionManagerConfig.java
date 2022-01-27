package com.example.MonManAppServer_spring.persitences;


import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "modelEntityManagerFactory",
                       transactionManagerRef = "modelTransactionManager")
public class ModelTransactionManagerConfig
{

    @Bean
    PlatformTransactionManager modelTransactionManager() {
        return new JpaTransactionManager(modelEntityManagerFactory().getObject());
    }

    @Bean
    LocalContainerEntityManagerFactoryBean modelEntityManagerFactory() {

        var vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        var factoryBean = new LocalContainerEntityManagerFactoryBean();

        factoryBean.setDataSource(modelDataSource());
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        factoryBean.setPackagesToScan("com.example.MonManAppServer_spring.models");

        Properties prop = new Properties();
        prop.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        prop.setProperty("hibernate.ddl-auto", "update");
        factoryBean.setJpaProperties(prop);

        return factoryBean;
    }

    @Bean
    @Primary
    DataSource modelDataSource() {

        return DataSourceBuilder
                .create()
                .username("postgres")
                .password("password")
                .url("jdbc:postgresql://localhost:5709/money_management_db")
                .driverClassName("org.postgresql.Driver")
                .build();
    }
}
