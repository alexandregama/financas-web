package br.com.caelum.financas.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;

import org.jboss.ejb3.annotation.Pool;

@Stateless
@Pool(value = "slsb-strict-max-pool")
public class Agendador {

	private static int totalCriado;
	
	private Integer valor = 0;

	@PostConstruct
	public void posConstrucao() {
		System.out.println("Criando agendador");
		totalCriado++;
		System.out.println("Valor: " + valor);
	}
	
	public void executa() {
		System.out.println("Valor: " + valor);
		valor++;
		System.out.printf("%d instancias criadas %n", totalCriado);
		try {
			System.out.printf("Executando %s %n", this);
			
			simulaPausaDeSegundos(10_000);
		} catch (InterruptedException e) {
		}
	}

	private void simulaPausaDeSegundos(int segundos) throws InterruptedException {
		Thread.sleep(segundos);
	}

	@PreDestroy
	public void preDestruicao() {
		System.out.println("Destruindo agendador");
	}
}
