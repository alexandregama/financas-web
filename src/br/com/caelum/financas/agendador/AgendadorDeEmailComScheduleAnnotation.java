package br.com.caelum.financas.agendador;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;

//@Stateless Funciona com @Stateless
//@Stateful Não funciona com @Stateful
@Singleton
@Startup
public class AgendadorDeEmailComScheduleAnnotation {

	@Schedule(hour = "8", minute = "32", second = "1", persistent = false)
	public void enviaEmail() {
		System.out.println("Enviando email utilizando @Schedule");
	}
	
	@Schedule(hour = "8", minute = "32", second = "1", persistent = false) //Podemos ter 2 @Schedule na mesma classe
	public void enviaEmailComOutroSchedule() {
		System.out.println("Enviando outro email utilizando @Schedule");
	}
	
}
