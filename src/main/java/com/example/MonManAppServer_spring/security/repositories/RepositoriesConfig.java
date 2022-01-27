package com.example.MonManAppServer_spring.security.repositories;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef="securityEntityManagerFactory", transactionManagerRef = "securityTransactionManager")
public class RepositoriesConfig {
}
