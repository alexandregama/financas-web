package br.com.caelum.financas.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.caelum.financas.modelo.Conta;

public class AgenciaENumeroValidator implements ConstraintValidator<AgenciaENumero, Conta> {

	public void initialize(AgenciaENumero anotacao) {
		
	}
	
	public boolean isValid(Conta conta, ConstraintValidatorContext ctx) {
		if (conta == null) {
			return true;
		}
		return ! (conta.getAgencia().isEmpty() ^ conta.getNumero().isEmpty());
	}
	
}
