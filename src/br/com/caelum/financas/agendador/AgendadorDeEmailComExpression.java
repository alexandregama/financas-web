package br.com.caelum.financas.agendador;

import javax.annotation.Resource;
import javax.ejb.ScheduleExpression;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

@Stateless
public class AgendadorDeEmailComExpression {

	@Resource
	private TimerService timer;
	
	public void agenda(String minutos, String segundos) {
		ScheduleExpression expression = new ScheduleExpression();
		expression.hour("1");
		expression.minute(minutos);
		expression.second(segundos);
		
		TimerConfig config = new TimerConfig();
		config.setPersistent(false);
		timer.createCalendarTimer(expression, config);
	}
	
	@Timeout
	public void executa(Timer timer) {
		System.out.println("Enviando email utilizando Expression do TimerService");
	}
	
}
