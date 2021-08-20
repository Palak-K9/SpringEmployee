package com.nagarro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Utility Class
 * 
 * @author palakkharbanda
 *
 */
@SpringBootApplication
public class SpringBootEmployeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootEmployeeApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTeamplate() {
		return new RestTemplate();
	}

}
