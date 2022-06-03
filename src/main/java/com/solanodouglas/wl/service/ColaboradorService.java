package com.solanodouglas.wl.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.solanodouglas.wl.model.Colaborador;
import com.solanodouglas.wl.repository.ColaboradorRepository;
import com.solanodouglas.wl.service.exceptions.DatabaseException;
import com.solanodouglas.wl.service.exceptions.ModelNotFoundException;

@Service
public class ColaboradorService {

	@Autowired
	private ColaboradorRepository repository;
	
	public List<Colaborador> findAll() {
		return repository.findAll();
	}
	
	public Colaborador findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new ModelNotFoundException("Id: " + id + " não encontrado"));
	}
		
	public Colaborador insert(Colaborador colab) {
		return repository.save(colab);
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
	
	public Colaborador update(Long id, Colaborador colab) {
		try {
			Colaborador model = repository.getOne(id);
			updateData(model, colab);
			return repository.save(model);
		} catch (EntityNotFoundException e) {
			throw new ModelNotFoundException(id);
		}
	}

	private void updateData(Colaborador model, Colaborador colab) {
		model.setNome(colab.getNome());
	}
}
