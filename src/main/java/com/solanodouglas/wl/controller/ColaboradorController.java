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

import com.solanodouglas.wl.dto.RequisicaoFormColaborador;
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
				ModelAndView mv = new ModelAndView("redirect:/colaboradores");
				repository.save(colaborador);
				mv.addObject("mensagem", "Colaborador " + colaborador.getId() + " inserido com sucesso.");
				return mv;
			} else {
				ModelAndView mv = new ModelAndView("redirect:/colaboradores/new");
				mv.addObject("mensagem", "Colaborador n??o inserido, cpf invalido.");
				return mv;
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
			mv.addObject("mensagem", "Colaborador " + id + " n??o encontrado.");
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
			mv.addObject("mensagem", "Colaborador " + id + " n??o pode ser deletado por conter caf??(s) em seu nome");
		}
		catch (ModelNotFoundException e) {
			mv.addObject("mensagem", "Colaborador " + id + " n??o encontrado.");
		}
		return mv;
	}
	
	@GetMapping("/colaboradores/{id}/edit")
	public ModelAndView edit(@PathVariable Long id, RequisicaoFormColaborador requisicao) {
		Optional<Colaborador> optional = repository.findById(id);
		
		if (optional.isPresent()) {
			Colaborador colaborador = optional.get();
			requisicao.fromColaborador(colaborador);
			
			ModelAndView mv = new ModelAndView("colaboradores/edit");
			mv.addObject("colaboradorId", colaborador.getId());
			mv.addObject("mensagem", "Colaborador " + id + "atualizado com sucesso.");
			return mv;
		} else {
			return new ModelAndView("redirect:/colaboradores");
		}
	}
	
	@PostMapping("/colaboradores/{id}")
	public ModelAndView update(@PathVariable Long id, @Valid RequisicaoFormColaborador requisicao, BindingResult bindingResult) {
		ModelAndView mv = new ModelAndView("redirect:/colaboradores");
		if (bindingResult.hasErrors()) {
			return null;
		} else {
			Optional<Colaborador> optional = repository.findById(id);
			
			if (optional.isPresent()) {
				Colaborador colaborador = requisicao.toColaborador(optional.get());
				repository.save(colaborador);
				return mv;
			} else {
				return mv;
			}
		}
	}
}

