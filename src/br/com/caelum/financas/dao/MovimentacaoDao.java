package br.com.caelum.financas.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.caelum.financas.exception.ValorMuitoAltoException;
import br.com.caelum.financas.mb.ValoresPorMesEAno;
import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.Movimentacao;
import br.com.caelum.financas.modelo.TipoMovimentacao;

@Stateless
public class MovimentacaoDao {

	@PersistenceContext
	private EntityManager manager;
	
	@PostConstruct
	public void posContrucao() {
		System.out.println("Bean de Movimentacao construido");
	}

	public void adiciona(Movimentacao movimentacao) throws Exception {
		
		manager.persist(movimentacao);
		
		if (movimentacao.getValor().compareTo(BigDecimal.ZERO) == -1) {
			throw new RuntimeException("O valor não pode ser negativo - sua transação sofreu Rollback");
		}
		
		if (movimentacao.getValor().compareTo(new BigDecimal(1000)) == 1 && movimentacao.isEntrada()) {
			throw new Exception("O valor não pode ser maior que 1000 e de Entrada - mas sua transação foi comitada");
		}
		
		if (movimentacao.getValor().compareTo(new BigDecimal(3000)) == 1 && movimentacao.isSaida()) {
			throw new ValorMuitoAltoException("O valor nao pode ser acima de 3000 e de Saida - mas sua transacao foi comitada");
		}
	}

	public List<Movimentacao> listaPorConta(Conta conta) {
		String jpql = 
				"select m from Movimentacao m where m.conta = :conta " + 
				"order by m.valor desc";
		TypedQuery<Movimentacao> query = manager.createQuery(jpql, Movimentacao.class);
		query.setParameter("conta", conta);
		
		return query.getResultList();
	}
	
	public Movimentacao busca(Integer id) {
		return manager.find(Movimentacao.class, id);
	}
	
	public void atualiza(Movimentacao movimentacao) {
		manager.merge(movimentacao);
	}

	public List<Movimentacao> lista() {
		String jpql = "select m from Movimentacao m";
		TypedQuery<Movimentacao> query = manager.createQuery(jpql, Movimentacao.class);
		
		return query.getResultList();
	}

	public void remove(Movimentacao movimentacao) {
		Movimentacao movimentacaoParaRemover = manager.find(Movimentacao.class, movimentacao.getId());
		
		manager.remove(movimentacaoParaRemover);
	}

	@PreDestroy
	public void destroy() {
		System.out.println("Bean de Movimentacao destruido");
	}

	public List<Movimentacao> listaPorValorETipo(BigDecimal valor,
			TipoMovimentacao tipoMovimentacao) {
		String jpql = 
				"select m from Movimentacao m " + 
				"where " +
				"m.valor <= :valor and " +
				"m.tipoMovimentacao = :tipo";
		TypedQuery<Movimentacao> query = manager.createQuery(jpql, Movimentacao.class);
		query.setParameter("valor", valor);
		query.setParameter("tipo", tipoMovimentacao);
		
		return query.getResultList();
	}

	public BigDecimal getTotalPorContaETipo(Conta conta,
			TipoMovimentacao tipoMovimentacao) {
		String jpql = 
			"select sum(m.valor) from Movimentacao m " +
			"where "+
			"	m.conta = :conta and " +
			"	m.tipoMovimentacao = :tipo";
		TypedQuery<BigDecimal> query = manager.createQuery(jpql, BigDecimal.class);
		query.setParameter("conta", conta);
		query.setParameter("tipo", tipoMovimentacao);
		
		return query.getSingleResult();
	}

	public List<Movimentacao> buscaPorTitular(String titular) {
		String jpql = 
			"select m from Movimentacao m " +
			"where m.conta.titular like :titular";
		TypedQuery<Movimentacao> query = manager.createQuery(jpql, Movimentacao.class);
		query.setParameter("titular", "%"+titular+"%");
		
		return query.getResultList();
	}

	public List<ValoresPorMesEAno> buscaPorMesEAnoUsandoArray(Conta conta,
			TipoMovimentacao tipoMovimentacao) {
		String jpql = 
			"select " +
			"	month(m.data), year(m.data), sum(m.valor) " +
			"from " +
			"	Movimentacao m " +
			"where " +
			"	m.tipoMovimentacao = :tipo and " +		
			"	m.conta = :conta " +
			"group by " +
			"	month(m.data), year(m.data) ";
		
		TypedQuery<Object[]> query = manager.createQuery(jpql, Object[].class);
		query.setParameter("tipo", tipoMovimentacao);
		query.setParameter("conta", conta);
		
		List<Object[]> lista = query.getResultList();
		
		List<ValoresPorMesEAno> estatisticas = new ArrayList<>();
		for (Object[] linha : lista) {
			int mes = (int) linha[0];
			int ano = (int) linha[1];
			BigDecimal valor = new BigDecimal( String.valueOf(linha[2]));
			
			ValoresPorMesEAno estatistica = new ValoresPorMesEAno(mes, ano, valor);
			estatisticas.add(estatistica);
		}
		
		return estatisticas;
	}

	public List<ValoresPorMesEAno> buscaPorMesEAnoUsandoConstructorExpression(
			Conta conta, TipoMovimentacao tipoMovimentacao) {
		String jpql = 
			"select " +
			" 	new br.com.caelum.financas.mb.ValoresPorMesEAno(month(m.data), year(m.data), sum(m.valor)) " +
			"from " +
			"   Movimentacao m " +
			"where " +
			"	m.tipoMovimentacao = :tipo and " +
			"	m.conta = :conta " +
			"group by " +
			"	month(m.data), year(m.data) ";
		
		TypedQuery<ValoresPorMesEAno> query = manager.createQuery(jpql, ValoresPorMesEAno.class);
		query.setParameter("tipo", tipoMovimentacao);
		query.setParameter("conta", conta);
		
		return query.getResultList();
	}

	public List<ValoresPorMesEAno> buscaPorMesEAnoUsandoConstructorExpressionEHaving(
			Conta conta, TipoMovimentacao tipoMovimentacao, BigDecimal valorMinimo) {
		String jpql = 
			"select " +
			" 	new br.com.caelum.financas.mb.ValoresPorMesEAno(month(m.data), year(m.data), sum(m.valor)) " +
			"from " +		
			" 	Movimentacao m " +		
			"where " +		
			" 	m.tipoMovimentacao = :tipo and " +		
			" 	m.conta = :conta " +		
			"group by " +		
			" 	month(m.data), year(m.data) having sum(m.valor) >= :valorMinimo";
		
		TypedQuery<ValoresPorMesEAno> query = manager.createQuery(jpql, ValoresPorMesEAno.class);
		query.setParameter("tipo", tipoMovimentacao);
		query.setParameter("conta", conta);
		query.setParameter("valorMinimo", valorMinimo);
		
		return query.getResultList();
	}
	
}
