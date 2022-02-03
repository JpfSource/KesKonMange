package com.keskonmange.exceptions;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErreurController {

	@Autowired
	private MessageSource messageSource;

	public ErreurController() {
	}

	@ExceptionHandler(value = {Exception.class})
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public String errorGeneralException(Exception e) {
		return messageSource.getMessage("erreur.prefix", null, Locale.getDefault()) +" "+ e.getMessage();
	}

	@ExceptionHandler(value = {ErreurPersonne.class})
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String errorPersonneException(ErreurPersonne e) {
		return messageSource.getMessage("erreur.personne.prefix", null, Locale.getDefault()) +" "+ e.getMessage();
	}

}
