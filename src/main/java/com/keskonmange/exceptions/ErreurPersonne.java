package com.keskonmange.exceptions;

/**
 * On créé une exception fonctionnelle pour gérer
 * les erreurs sur les personnes
 */
public class ErreurPersonne extends Exception {
	private static final long serialVersionUID = 1L;

	public ErreurPersonne() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ErreurPersonne(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public ErreurPersonne(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ErreurPersonne(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ErreurPersonne(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
