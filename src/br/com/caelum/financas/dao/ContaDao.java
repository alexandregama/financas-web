package br.com.caelum.financas.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;

import br.com.caelum.financas.exception.ContaAgenciaInvalidaException;
import br.com.caelum.financas.exception.ContaTitularInvalidoException;
import br.com.caelum.financas.modelo.Conta;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ContaDao {

	@PersistenceContext
	private EntityManager manager;
	
	@PostConstruct
	public void posConstrucao() {
		System.out.println("Bean de ContaDao foi construido");
	}
	
	@PreDestroy
	public void destroy() {
		System.out.println("Bean de ContaDao sendo destruido");
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED) //default
	public void adicionaComRequired(Conta conta) {
		adiciona(conta); 
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void adicionaComRequiresNew(Conta conta) {
		adiciona(conta); 
	}

	//Teremos uma TransactionRequiredException pois o Container está gerenciando a transação, porém nao pode comitar
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public void adicionaComNever(Conta conta) {
		adiciona(conta);  //TransactionRequiredException
	}
	
	//Teremos uma EJBTransactionRequiredException
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public void adicionaComMandatory(Conta conta) {
		adiciona(conta); //EJBTransactionRequiredException
	}

	private void adiciona(Conta conta) {
		manager.persist(conta);
		
		if (conta.getTitular().isEmpty()) {
			throw new ContaTitularInvalidoException("O titular nao pode estar em branco - sua transação não foi comitada e seu bean eliminado");
		}
		
		if (conta.getAgencia().isEmpty()) {
			throw new ContaAgenciaInvalidaException("A agencia nao pode estar em branco - sua transacao nao será comitada e seu bean será eliminado");
		}
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
