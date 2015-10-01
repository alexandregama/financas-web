package br.com.caelum.financas.mb;

import java.math.BigDecimal;

public class ValoresPorMesEAno {

	private int mes;
	
	private int ano;
	
	private BigDecimal valor;
	
	public ValoresPorMesEAno(int mes, int ano, BigDecimal valor) {
		super();
		this.mes = mes;
		this.ano = ano;
		this.valor = valor;
	}

	public int getMes() {
		return mes;
	}

	public int getAno() {
		return ano;
	}

	public BigDecimal getValor() {
		return valor;
	}
	
}
