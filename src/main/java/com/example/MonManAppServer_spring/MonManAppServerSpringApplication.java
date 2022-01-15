package com.example.MonManAppServer_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


//How autoconfiguration works?
//What is the Groovy?
@SpringBootApplication
@EnableJpaRepositories
@ComponentScan("com.example")
public class MonManAppServerSpringApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(MonManAppServerSpringApplication.class, args);

	}
}
