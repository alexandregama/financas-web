package br.com.caelum.financas.agendador;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;

//@Stateless Funciona com @Stateless
//@Stateful Não funciona com @Stateful
@Singleton
@Startup
public class AgendadorDeEmailComScheduleAnnotation {

	@Schedule(hour = "9", minute = "10", second = "10", persistent = false)
	public void enviaEmail() {
		System.out.println("Enviando email utilizando @Schedule");
	}
	
}
