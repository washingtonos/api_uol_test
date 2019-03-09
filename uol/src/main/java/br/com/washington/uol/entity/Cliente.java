package br.com.washington.uol.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TB_CLIENTE")
public class Cliente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String nome;

	@NotNull
	private String idade;

	@NotNull
	private BigDecimal min_temp;

	@NotNull
	private BigDecimal max_temp;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getIdade() {
		return idade;
	}

	public void setIdade(String idade) {
		this.idade = idade;
	}

	public BigDecimal getMin_temp() {
		return min_temp;
	}

	public void setMin_temp(BigDecimal min_temp) {
		this.min_temp = min_temp;
	}

	public BigDecimal getMax_temp() {
		return max_temp;
	}

	public void setMax_temp(BigDecimal max_temp) {
		this.max_temp = max_temp;
	}

}
