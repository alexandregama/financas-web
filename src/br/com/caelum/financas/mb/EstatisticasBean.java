package br.com.caelum.financas.mb;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.stat.EntityStatistics;
import org.hibernate.stat.Statistics;

@Named
@ApplicationScoped
public class EstatisticasBean {

	@Inject
	private EntityManager manager;
	
	private Statistics estatisticas;
	
	private EntityStatistics movimentacaoEntityStatistics;
	
	private EntityStatistics contaEntityStatistics;
	
	public void gera() {
		Session session = manager.unwrap(Session.class);
		estatisticas = session.getSessionFactory().getStatistics();
		movimentacaoEntityStatistics = estatisticas.getEntityStatistics("br.com.caelum.financas.modelo.Movimentacao");
		contaEntityStatistics = estatisticas.getEntityStatistics("br.com.caelum.financas.modelo.Conta");
		
		System.out.println("Gerando estatisitcas");
	}

	public Statistics getEstatisticas() {
		return estatisticas;
	}

	public EntityStatistics getMovimentacaoEntityStatistics() {
		return movimentacaoEntityStatistics;
	}
	
	public EntityStatistics getContaEntityStatistics() {
		return contaEntityStatistics;
	}
	
}
