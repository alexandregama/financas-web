package br.com.caelum.financas.exception;

public class ValorMuitoAltoException extends Exception { //Checked Exception

	private static final long serialVersionUID = 6689567535968686662L;

	public ValorMuitoAltoException(String message) {
		super(message);
	}
	
}
