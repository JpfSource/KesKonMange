package com.keskonmange.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErreurController {

	public ErreurController() {
	}

	@ExceptionHandler(value = { Exception.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public String errorGeneralException(Exception e) {
		return e.getMessage();
	}

	@ExceptionHandler(value = { ErreurAbsence.class })
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String errorAbsenceException(ErreurAbsence e) {
		return e.getMessage();
	}

	@ExceptionHandler(value = { ErreurAliment.class })
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String errorAlimentException(ErreurAliment e) {
		return e.getMessage();
	}
	
	@ExceptionHandler(value = { ErreurAllergie.class })
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String errorAllergieException(ErreurAllergie e) {
		return e.getMessage();
	}
	
	@ExceptionHandler(value = { ErreurGroupe.class })
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String errorGroupeException(ErreurGroupe e) {
		return e.getMessage();
	}
	
	@ExceptionHandler(value = { ErreurPersonne.class })
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String errorPersonneException(ErreurPersonne e) {
		return e.getMessage();
	}
	
	@ExceptionHandler(value = { ErreurPlat.class })
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String errorPlatException(ErreurPlat e) {
		return e.getMessage();
	}

	@ExceptionHandler(value = { ErreurRepartitionCalorique.class })
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String errorRepartitionCaloriqueException(ErreurRepartitionCalorique e) {
		return e.getMessage();
	}
	
	@ExceptionHandler(value = { ErreurRepas.class })
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String errorRepasException(ErreurRepas e) {
		return e.getMessage();
	}
	
	@ExceptionHandler(value = { ErreurScore.class })
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String errorScoreException(ErreurScore e) {
		return e.getMessage();
	}

}
