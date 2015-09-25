package br.com.caelum.financas.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.caelum.financas.modelo.Conta;

@Stateless
public class ContaDao {

	@PersistenceContext
	private EntityManager manager;

	public void adiciona(Conta conta) {
		manager.persist(conta);
	}

	public Conta busca(Integer id) {
		return manager.find(Conta.class, id);
	}

	public void atualiza(Conta conta) {
		manager.merge(conta);
	}
	
	public List<Conta> lista() {
		String jpql = "select c from Conta c";
		TypedQuery<Conta> query = manager.createQuery(jpql, Conta.class);

		return query.getResultList();
	}

	public void remove(Conta conta) {
		Conta contaParaRemover = this.manager.find(Conta.class, conta.getId());

		manager.remove(contaParaRemover);
	}

}
