package br.com.caelum.financas.mb;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.caelum.financas.modelo.Conta;

@Named
@RequestScoped
public class CacheBean {

	private Integer id;
	private Conta conta;

	@PersistenceContext
	private EntityManager manager;
	
	public void pesquisar() {
		System.out.println("Testando cache de primeiro nivel");
		conta = manager.find(Conta.class, id);
		conta = manager.find(Conta.class, id);
		conta = manager.find(Conta.class, id);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Conta getConta() {
		return conta;
	}
}