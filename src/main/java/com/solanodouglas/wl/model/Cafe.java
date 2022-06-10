package com.solanodouglas.wl.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "tb_cafe")
public class Cafe implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@NotBlank
	private String nome;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "colaborador_id")
	private Colaborador colaborador;
	
	public Cafe() {
	}

	public Cafe(Long id, String nome, Colaborador colaborador) {
		super();
		this.id = id;
		this.nome = nome;
		this.colaborador = colaborador;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String cafe) {
		this.nome = cafe;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}
	

	@Override
	public int hashCode() {
		return Objects.hash(nome, colaborador, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cafe other = (Cafe) obj;
		return Objects.equals(nome, other.nome) && Objects.equals(colaborador, other.colaborador)
				&& Objects.equals(id, other.id);
	}
}
