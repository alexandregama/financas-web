package br.com.caelum.financas.mb;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.financas.dao.ContaDao;
import br.com.caelum.financas.dao.MovimentacaoDao;
import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.Movimentacao;
import br.com.caelum.financas.modelo.TipoMovimentacao;

@Named
@ViewScoped
public class MovimentacoesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private MovimentacaoDao movimentacaoDao;
	private ContaDao contaDao;
	private List<Movimentacao> movimentacoes;
	private Movimentacao movimentacao = new Movimentacao();
	private Integer contaId;
	private Integer categoriaId;

	@Inject
	public MovimentacoesBean(MovimentacaoDao movimentacaoDao, ContaDao contaDao) {
		this.movimentacaoDao = movimentacaoDao;
		this.contaDao = contaDao;
	}

	@Deprecated //default constructor for CDI
	MovimentacoesBean() {
	}
	
	public void grava() {
		Conta conta = contaDao.busca(contaId);
		movimentacao.setConta(conta);
		try {
			movimentacaoDao.adiciona(movimentacao);
		} catch (Exception e) {
			e.printStackTrace();
		}

		limpaFormularioDoJSF();
	}

	public void remove() {
		movimentacaoDao.remove(movimentacao);

		limpaFormularioDoJSF();
	}

	public List<Movimentacao> getMovimentacoes() {
		if (movimentacoes == null) {
			movimentacoes = movimentacaoDao.lista();
		}
		return movimentacoes;
	}

	public Movimentacao getMovimentacao() {
		return movimentacao;
	}

	private void limpaFormularioDoJSF() {
		movimentacao = new Movimentacao();
		movimentacoes = null;
	}

	public void setMovimentacao(Movimentacao movimentacao) {
		this.movimentacao = movimentacao;
	}

	public Integer getContaId() {
		return contaId;
	}

	public void setContaId(Integer contaId) {
		this.contaId = contaId;
	}

	public Integer getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Integer categoriaId) {
		this.categoriaId = categoriaId;
	}

	public TipoMovimentacao[] getTiposDeMovimentacao() {
		return TipoMovimentacao.values();
	}

}
