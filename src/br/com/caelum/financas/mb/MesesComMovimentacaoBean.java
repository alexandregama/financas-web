package br.com.caelum.financas.mb;

import java.math.BigDecimal;
import java.util.List;

import br.com.caelum.financas.dao.MovimentacaoDao;
import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.TipoMovimentacao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class MesesComMovimentacaoBean {

	private Conta conta = new Conta();

	private TipoMovimentacao tipoMovimentacao;

	private MovimentacaoDao movimentacaoDao;
	
	private List<ValoresPorMesEAno> valoresPorMesEAno;
	
	private BigDecimal valorMinimo;

	@Inject
	public MesesComMovimentacaoBean(MovimentacaoDao movimentacaoDao) {
		this.movimentacaoDao = movimentacaoDao;
	}
	
	@Deprecated //CDI eyes only
	MesesComMovimentacaoBean() {
	}
	
	public void lista() {
		System.out.println("Listando as contas pelos valores movimentados no mes");
		valoresPorMesEAno = movimentacaoDao.buscaPorMesEAnoUsandoConstructorExpression(conta, tipoMovimentacao);
	}
	
	public void listaValorMinimo() {
		System.out.println("Listando as contas pelos valores movimentados no mes utilizando Having");
		valoresPorMesEAno = movimentacaoDao.buscaPorMesEAnoUsandoConstructorExpressionEHaving(conta, tipoMovimentacao, valorMinimo);
	}

	public TipoMovimentacao getTipoMovimentacao() {
		return tipoMovimentacao;
	}

	public void setTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
		this.tipoMovimentacao = tipoMovimentacao;
	}

	public Conta getConta() {
		return conta;
	}
	
	public List<ValoresPorMesEAno> getValoresPorMesEAno() {
		return valoresPorMesEAno;
	}

	public BigDecimal getValorMinimo() {
		return valorMinimo;
	}

	public void setValorMinimo(BigDecimal valorMinimo) {
		this.valorMinimo = valorMinimo;
	}

}
