package com.solanodouglas.wl.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.solanodouglas.wl.model.Cafe;
import com.solanodouglas.wl.model.Colaborador;

public class RequisicaoFormCafe {

	@NotNull
	@NotBlank
	private String nome;

	private Colaborador colaborador;

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Cafe toCafe() {
		Cafe cafe = new Cafe();
		cafe.setNome(this.nome);
		
		return cafe;
	}
	
	public Cafe toCafe(Cafe cafe) {
		cafe.setNome(this.nome);
		cafe.setColaborador(this.colaborador);
		return cafe;
	}
	
	public void fromCafe(Cafe cafe) {
		this.nome = cafe.getNome();
		this.colaborador = cafe.getColaborador();
	}

	@Override
	public String toString() {
		return "RequisicaoFormCafe [nome=" + nome + "]";
	}
}
