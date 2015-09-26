package br.com.caelum.financas.mb;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.financas.service.Agendador;

@Named
@RequestScoped
public class CicloDeVidaBean {
	
	private Agendador agendador;
	
	@Inject
	public CicloDeVidaBean(Agendador agendador) {
		this.agendador = agendador;
	}
	
	@Deprecated //CDI
	CicloDeVidaBean() {
	}
	
	public void executeAgendador() {
		agendador.executa();
	}
	
}
