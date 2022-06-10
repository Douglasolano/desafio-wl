package com.solanodouglas.wl.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.solanodouglas.wl.model.Colaborador;
import com.solanodouglas.wl.repository.ColaboradorRepository;
import com.solanodouglas.wl.service.ColaboradorService;
import com.solanodouglas.wl.service.exceptions.DatabaseException;
import com.solanodouglas.wl.service.exceptions.ModelNotFoundException;

@Controller
public class ColaboradorController {

	@Autowired
	private ColaboradorService service;
	
	@Autowired
	private ColaboradorRepository repository;
	
	
	@GetMapping("/colaboradores")
	public ModelAndView index() {
		List<Colaborador> colaboradores = service.findAll();
        ModelAndView mv = new ModelAndView("colaboradores/index");
        mv.addObject("colaboradores", colaboradores);
		return mv;
	}
	
	@GetMapping("/colaboradores/new")
	public ModelAndView novo(Colaborador colaborador) {
		ModelAndView mv = new ModelAndView("colaboradores/new");
		return mv;
	}
	
	@PostMapping("/colaboradores")
	public ModelAndView insert(@Valid Colaborador colaborador, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			ModelAndView mv = new ModelAndView("colaboradores/new");
			return mv;
		} else {
			if (repository.findByCpf(colaborador.getCpf()) == null) {
				repository.save(colaborador);
				return new ModelAndView("redirect:/colaboradores");
			} else {
				return new ModelAndView("redirect:/colaboradores");
			}
		}
	}
	
	@GetMapping("/colaboradores/{id}")
	public ModelAndView show(@PathVariable Long id) {
		Optional<Colaborador> optional = repository.findById(id);
		if (optional.isPresent()) {
			Colaborador colaborador = optional.get();
			
			ModelAndView mv = new ModelAndView("colaboradores/show");
			mv.addObject("colaborador", colaborador);
			
			return mv;
		} else {
			ModelAndView mv = new ModelAndView("redirect:/colaboradores");
			mv.addObject("mensagem", "Colaborador " + id + " não encontrado.");
			return mv;
		}
	}
	
	@GetMapping("/colaboradores/{id}/delete")
	public ModelAndView delete(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("redirect:/colaboradores");
		
		try {
			service.delete(id);
			mv.addObject("mensagem", "Colaborador " + id + " deletado com sucesso.");
		}
		catch (DatabaseException e) {
			mv.addObject("mensagem", "Colaborador " + id + " não pode ser deletado por conter café(s) em seu nome");
		}
		catch (ModelNotFoundException e) {
			mv.addObject("mensagem", "Colaborador " + id + " não encontrado.");
		}
		return mv;
	}
} 
