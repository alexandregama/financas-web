package br.com.caelum.financas.agendador;

import java.util.Calendar;
import java.util.Timer;

public class AgendadorNaUnha {

	public static void main(String[] args) {
		Timer timer = new Timer();
		Calendar data = Calendar.getInstance();
		data.set(Calendar.HOUR, 19);
		
		timer.schedule(new EnviadorDeEmail(), 1000, 10000);
	}
	
}
