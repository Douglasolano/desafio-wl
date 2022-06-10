package com.solanodouglas.wl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages="com.solanodouglas.controller")
@EntityScan(basePackages = "com.solanodouglas.wl.model")
@ComponentScan(basePackages= {"com.*"})
@EnableJpaRepositories(basePackages= {"com.solanodouglas.wl.repository"})
@EnableTransactionManagement
public class DesafiowlApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafiowlApplication.class, args);
	}

}
