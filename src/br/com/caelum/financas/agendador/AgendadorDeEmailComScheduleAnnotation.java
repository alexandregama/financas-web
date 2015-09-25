package br.com.caelum.financas.agendador;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class AgendadorDeEmailComScheduleAnnotation {

	@Schedule(hour = "9", minute = "15", second = "*")
	public void enviaEmail() {
		System.out.println("Enviando email utilizando @Schedule");
	}
	
}
