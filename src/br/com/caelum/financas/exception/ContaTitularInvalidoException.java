package br.com.caelum.financas.exception;

//Neste caso teremos essa exceção encapsulada pela Exceção javax.ejb.EJBException
public class ContaTitularInvalidoException extends RuntimeException { //unchecked exception - System Application

	private static final long serialVersionUID = 952864874436276753L;

	public ContaTitularInvalidoException(String message) {
		super(message);
	}
	
}
