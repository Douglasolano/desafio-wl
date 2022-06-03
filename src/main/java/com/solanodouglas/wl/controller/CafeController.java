package com.solanodouglas.wl.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.solanodouglas.wl.model.Cafe;
import com.solanodouglas.wl.model.Colaborador;
import com.solanodouglas.wl.service.CafeService;

@RestController
@RequestMapping(value = "/cafes")
public class CafeController {

	@Autowired
	private CafeService service;
	
	@GetMapping
	public ResponseEntity<List<Cafe>> findAll() {
		List<Cafe> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Cafe> findById(@PathVariable Long id) {
		Cafe obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping
	public ResponseEntity<Cafe> insert(@RequestBody Cafe cafe) {
		cafe = service.insert(cafe);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cafe.getId()).toUri();
		return ResponseEntity.created(uri).body(cafe);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Cafe> update(@PathVariable Long id, @RequestBody Cafe cafe, @RequestBody Colaborador colab) {
		cafe = service.update(id, cafe, colab);
		return ResponseEntity.ok().body(cafe);
	}
}
