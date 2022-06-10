package com.solanodouglas.wl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.solanodouglas.wl.model.Cafe;

@Repository
public interface CafeRepository extends JpaRepository<Cafe, Long>{

	@Query("SELECT c FROM Cafe c WHERE c.nome = :nome")
	Cafe findByNome(@Param("nome") String nome);
}
