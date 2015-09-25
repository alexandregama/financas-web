package br.com.caelum.financas.service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;

@Singleton
@Startup
public class AgendadorDeEmailSingleton {

	@Resource
	private TimerService timer;

	public AgendadorDeEmailSingleton() {
		System.out.println("Criando AgendadorDeEmailSingleton");
	}
	
	@PostConstruct
	public void posContrucao() {
		System.out.println("Vai criar o timer..");
		
		timer.createTimer(2_000L, "Enviador de email Singleton");
	}
	
	@Timeout
	public void executa(Timer timer) {
		System.out.println("Enviando email..");
	}
	
}
