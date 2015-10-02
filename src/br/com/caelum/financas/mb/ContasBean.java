package br.com.caelum.financas.mb;

import java.io.Serializable;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.financas.dao.ContaDao;
import br.com.caelum.financas.modelo.Conta;

@Named
@ViewScoped
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ContasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private ContaDao contaDao;

	private Conta conta = new Conta();
	
	private List<Conta> contas;

	@Inject
	public ContasBean(ContaDao contaDao) {
		this.contaDao = contaDao;
	}
	
	@Deprecated //default constructor for CDI 
	ContasBean() {
	}

	public void gravaDefault() {
		grava();
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void gravaComTransacaoRequired() {
		grava();
	}
	
	private void grava() {
		if (conta.getId() == null) {
			contaDao.adiciona(conta);
		} else {
			contaDao.atualiza(conta);
		}
		
		limpaFormularioDoJSF();
	}

	public List<Conta> getContas() {
		if (contas == null) {
			contas = contaDao.lista();
		}
		return contas;
	}

	public void remove() {
		contaDao.remove(conta);

		limpaFormularioDoJSF();
	}

	private void limpaFormularioDoJSF() {
		conta = new Conta();
		contas = null; 
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}
}
