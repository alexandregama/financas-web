package br.com.caelum.financas.agendador;

import java.util.TimerTask;

public class EnviadorDeEmail extends TimerTask {

	@Override
	public void run() {
		System.out.println("Enviando email na unha");
	}
	
}
