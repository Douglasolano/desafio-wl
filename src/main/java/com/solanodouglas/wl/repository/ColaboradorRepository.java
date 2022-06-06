package com.solanodouglas.wl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.solanodouglas.wl.model.Colaborador;

@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador, Long>{

	@Query("SELECT c FROM Colaborador c WHERE c.cpf = :cpf")
	public Colaborador findByCpf(@Param("cpf") String cpf);
}
