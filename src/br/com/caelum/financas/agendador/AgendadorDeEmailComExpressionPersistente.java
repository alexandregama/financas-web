package br.com.caelum.financas.agendador;

import javax.annotation.Resource;
import javax.ejb.ScheduleExpression;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

@Stateless
public class AgendadorDeEmailComExpressionPersistente {

	@Resource
	private TimerService timer;
	
	public void agenda(String horas, String minutos, String segundos) {
		ScheduleExpression expression = new ScheduleExpression();
		expression.hour(horas);
		expression.minute(minutos);
		expression.second(segundos);
		
		TimerConfig config = new TimerConfig();
		config.setPersistent(true);
		timer.createCalendarTimer(expression, config);
	}
	
	@Timeout
	public void executa(Timer timer) {
		System.out.println("Enviando email utilizando Expression do TimerService agendado pelo Usuario");
		System.out.println("Informações sobre o Timer:");
		System.out.println(timer.getInfo());
		System.out.println("Próximo Timeout: " + timer.getNextTimeout());
		System.out.println("Schedule sendo executado: " + timer.getSchedule());
		System.out.println("Tempo restante: " + timer.getTimeRemaining());
		System.out.println("É persistente?: " + timer.isPersistent());
	}
	
	//@Timeout Com 2 TimeOuts a aplicação não sobe - DeploymentUnitProcessingException
	public void executaOutroTimeout(Timer timer) {
		System.out.println("Outro envio de email utilizando Expression do TimerService agendado pelo Usuario");
	}
	
}
