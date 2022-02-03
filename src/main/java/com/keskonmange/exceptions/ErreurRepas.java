package com.keskonmange.exceptions;

public class ErreurRepas extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ErreurRepas()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public ErreurRepas(String message,Throwable cause,boolean enableSuppression,boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public ErreurRepas(String message,Throwable cause)
	{
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ErreurRepas(String message)
	{
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ErreurRepas(Throwable cause)
	{
		super(cause);
		// TODO Auto-generated constructor stub
	}
}
