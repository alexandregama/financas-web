package br.com.caelum.financas.agendador;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;

//@Stateless Funciona com @Stateless
//@Stateful Não funciona com @Stateful
@Singleton
@Startup
public class AgendadorDeEmailComScheduleAnnotation {

	@Schedule(hour = "9", minute = "*", second = "*", persistent = false)
	public void enviaEmail() {
		System.out.println("Enviando email utilizando @Schedule");
	}
	
}
