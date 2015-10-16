package br.com.caelum.financas.mb;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.financas.dao.ContaDao;
import br.com.caelum.financas.modelo.Conta;

@Named
@ViewScoped
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
	
	@PostConstruct
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void posConstrucao() {
//		conta = new Conta("Gustavo", "11111", "22222", "Bradesco"); Apenas teste de insert no @PostConstruct
//		contaDao.adicionaComRequired(conta);
	}

	public void gravaDefault() {
		if (conta.getId() != null) {
			contaDao.atualiza(conta);
		} else {
			contaDao.adicionaComRequired(conta);
		}
		
		limpaFormularioDoJSF();
	}
	
	public void gravaDefaultValidandoNaUnha() {
		if (conta.getId() != null) {
			contaDao.atualiza(conta);
		} else {
			contaDao.adicionaComRequiredValidandoNaUnhaComBeanValidation(conta);
		}
		
		limpaFormularioDoJSF();
	}
	
	public void gravaComRequired() {
		if (conta.getId() == null) {
			contaDao.adicionaComRequired(conta);
		} else {
			contaDao.atualiza(conta);
		}
		
		limpaFormularioDoJSF();
	}
	
	public void gravaComTransacaoRequiresNew() {
		if (conta.getId() == null) {
			contaDao.adicionaComRequiresNew(conta);
		} else {
			contaDao.atualiza(conta);
		}
		
		limpaFormularioDoJSF();
	}
	
	public void gravaComTransacaoNever() {
		if (conta.getId() == null) {
			contaDao.adicionaComNever(conta);
		} else {
			contaDao.atualiza(conta);
		}
		
		limpaFormularioDoJSF();
	}
	
	public void gravaComTransacaoMandatory() {
		if (conta.getId() == null) {
			contaDao.adicionaComMandatory(conta);
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

	public void lockar() {
		contaDao.lockar(conta);
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
