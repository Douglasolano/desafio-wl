package com.solanodouglas.wl.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.solanodouglas.wl.model.Cafe;
import com.solanodouglas.wl.model.Colaborador;

public class RequisicaoFormColaborador {

	@NotNull
	@NotBlank
	private String nome;
	
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Colaborador toColaborador() {
		Colaborador colaborador = new Colaborador();
		colaborador.setNome(this.nome);
		
		return colaborador;
	}
	
	public Colaborador toColaborador(Colaborador colaborador) {
		colaborador.setNome(this.nome);
		return colaborador;
	}
	
	public void fromColaborador(Colaborador colaborador) {
		this.nome = colaborador.getNome();
	}

	@Override
	public String toString() {
		return "RequisicaoFormColaborador [nome=" + nome + "]";
	}

	public Cafe toCafe(Cafe cafe) {
		// TODO Auto-generated method stub
		return null;
	}
}
