package br.com.caelum.financas.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class ContaAgenciaInvalidaException extends RuntimeException { //unchecked exception - System Exception

	private static final long serialVersionUID = -4911605099120204477L;

	public ContaAgenciaInvalidaException(String message) {
		super(message);
	}
	
}
