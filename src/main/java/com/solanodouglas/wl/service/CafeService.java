package com.solanodouglas.wl.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.solanodouglas.wl.model.Cafe;
import com.solanodouglas.wl.model.Colaborador;
import com.solanodouglas.wl.repository.CafeRepository;
import com.solanodouglas.wl.service.exceptions.DatabaseException;
import com.solanodouglas.wl.service.exceptions.ModelNotFoundException;

@Service
public class CafeService {

	@Autowired
	private CafeRepository repository;
		
	public List<Cafe> findAll() {
		return repository.findAll();
	}
	
	public Cafe findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new ModelNotFoundException("Id: " + id + " não encontrado"));
	}	
	
	public Cafe insert(Cafe cafe) {
		if (repository.findByNome(cafe.getNome()) == null) {
			return repository.save(cafe);
		} else {
			throw new DatabaseException("Este café já foi escolhido por um colaborador.");
		}
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ModelNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public Cafe update(Long id, Cafe cafe, Colaborador colab) {
		try {
			Cafe model = repository.getOne(id);
			updateData(model, cafe, colab);
			return repository.save(model);
		} catch (EntityNotFoundException e) {
			throw new ModelNotFoundException(id);
		}
	}

	private void updateData(Cafe model, Cafe cafe, Colaborador colab) {
		model.setNome(cafe.getNome());
		model.setColaborador(colab);
	}
}
