package br.com.caelum.financas.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.caelum.financas.exception.ValorMuitoAltoException;
import br.com.caelum.financas.modelo.Movimentacao;
import br.com.caelum.financas.modelo.TipoMovimentacao;

@Stateless
public class MovimentacaoDao {

	@PersistenceContext
	private EntityManager manager;

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

}
