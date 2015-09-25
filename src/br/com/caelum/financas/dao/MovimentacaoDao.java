package br.com.caelum.financas.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.caelum.financas.modelo.Movimentacao;

@Stateless
public class MovimentacaoDao {

	@PersistenceContext
	private EntityManager manager;

	public void adiciona(Movimentacao movimentacao) {
		manager.persist(movimentacao);
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
