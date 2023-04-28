package com.driver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
// Included the below for :
// <<Consider defining a bean of type 'com.driver.repository.AdminRepository' in your configuration.>> ;
@ComponentScan("com.abc.repository")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class Uber {

	public static void main(String[] args) {
		SpringApplication.run(Uber.class, args);
	}

}
