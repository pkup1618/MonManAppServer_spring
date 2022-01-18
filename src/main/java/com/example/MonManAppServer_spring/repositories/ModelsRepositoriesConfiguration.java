package com.example.MonManAppServer_spring.repositories;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef="modelEntityManagerFactory", transactionManagerRef = "modelTransactionManager")
public class ModelsRepositoriesConfiguration {
}
