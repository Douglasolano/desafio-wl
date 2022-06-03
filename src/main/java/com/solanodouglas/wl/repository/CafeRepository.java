package com.solanodouglas.wl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.solanodouglas.wl.model.Cafe;

@Repository
public interface CafeRepository extends JpaRepository<Cafe, Long>{

}
