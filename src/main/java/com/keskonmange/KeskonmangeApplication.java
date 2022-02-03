package com.keskonmange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

@SpringBootApplication
public class KeskonmangeApplication {

	public static void main(String[] args) {
		SpringApplication.run(KeskonmangeApplication.class, args);
	}
	
	/**
     * Configuration pour le chargement des messages Intenationaux
     * messages.properties
     * 
     * @return
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("i18n/messages");
        return messageSource;
    }

}
