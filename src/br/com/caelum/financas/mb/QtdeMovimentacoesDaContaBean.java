package br.com.caelum.financas.mb;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.financas.dao.ContaDao;
import br.com.caelum.financas.modelo.Conta;

@Named
@RequestScoped
public class QtdeMovimentacoesDaContaBean {
	
	private Conta conta = new Conta();
	private int quantidade;
	
	@Inject
	private ContaDao contas;
	
	public void lista() {
		System.out.println("Exibindo as quantidades de movimentacoes da conta");
		Conta contaBuscada = contas.busca(conta.getId());
		this.quantidade = contaBuscada.getQuantidadeDeMovimentacoes();
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
}
