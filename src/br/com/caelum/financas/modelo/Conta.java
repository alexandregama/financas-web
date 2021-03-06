package br.com.caelum.financas.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotBlank;

import br.com.caelum.financas.validator.AgenciaENumero;

@Entity
@Cacheable
@AgenciaENumero
public class Conta implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "{conta.titular.vazio}")
	private String titular;
	
	@NotNull
	private String agencia;
	
	private String numero;
	private String banco;
	
	@OneToOne
	@JoinColumn(unique = true)
	private Gerente gerente;
	
	@Version
	private Integer version;

	@OneToMany(mappedBy = "conta")
	@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
	private List<Movimentacao> movimentacoes = new ArrayList<>();

	public Conta(String titular, String agencia, String numero, String banco) {
		this.titular = titular;
		this.agencia = agencia;
		this.numero = numero;
		this.banco = banco;
	}

	public Conta() {
	}

	@Override
	public String toString() {
		return "Conta [id=" + id + ", titular=" + titular + ", agencia="
				+ agencia + ", numero=" + numero + ", banco=" + banco + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public int getQuantidadeDeMovimentacoes() {
		return this.movimentacoes.size();
	}

}
