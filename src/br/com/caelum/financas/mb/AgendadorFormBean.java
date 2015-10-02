package br.com.caelum.financas.mb;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.financas.agendador.AgendadorDeEmailComExpression;
import br.com.caelum.financas.agendador.AgendadorDeEmailComExpressionPersistente;

@Named
@RequestScoped
public class AgendadorFormBean {

	private String expressaoHoras;
	private String expressaoMinutos;
	private String expressaoSegundos;
	
	@Inject
	private AgendadorDeEmailComExpression agendador;
	
	@Inject
	private AgendadorDeEmailComExpressionPersistente agendadorPersistente;
	
	public void agendar() {
		agendador.agenda(expressaoHoras, expressaoMinutos, expressaoSegundos);
	}
	
	public void agendarPersistindo() {
		agendadorPersistente.agenda(expressaoHoras, expressaoMinutos, expressaoSegundos);
	}

	public String getExpressaoMinutos() {
		return expressaoMinutos;
	}

	public void setExpressaoMinutos(String expressaoMinutos) {
		this.expressaoMinutos = expressaoMinutos;
	}

	public String getExpressaoSegundos() {
		return expressaoSegundos;
	}

	public void setExpressaoSegundos(String expressaoSegundos) {
		this.expressaoSegundos = expressaoSegundos;
	}

	public String getExpressaoHoras() {
		return expressaoHoras;
	}

	public void setExpressaoHoras(String expressaoHoras) {
		this.expressaoHoras = expressaoHoras;
	}

}
