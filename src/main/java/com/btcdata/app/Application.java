package com.btcdata.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.client.RestTemplate;

@EnableAsync
@SpringBootApplication
@ComponentScan("com.btcdata")
@EnableMongoRepositories(basePackages = {"com.btcdata"})
public class Application extends SpringBootServletInitializer implements WebApplicationInitializer {

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
	}
	
	@Autowired  
    private RestTemplateBuilder builder;
	
	@Bean  
    public RestTemplate restTemplate() {  
        return builder.build();  
    } 
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
