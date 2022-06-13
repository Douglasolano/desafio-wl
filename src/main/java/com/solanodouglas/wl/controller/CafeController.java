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

import com.solanodouglas.wl.dto.RequisicaoFormCafe;
import com.solanodouglas.wl.dto.RequisicaoFormColaborador;
import com.solanodouglas.wl.model.Cafe;
import com.solanodouglas.wl.model.Colaborador;
import com.solanodouglas.wl.repository.CafeRepository;
import com.solanodouglas.wl.repository.ColaboradorRepository;
import com.solanodouglas.wl.service.CafeService;
import com.solanodouglas.wl.service.ColaboradorService;
import com.solanodouglas.wl.service.exceptions.DatabaseException;
import com.solanodouglas.wl.service.exceptions.ModelNotFoundException;

@Controller
public class CafeController {

	@Autowired
	private CafeService service;
	
	@Autowired
	private CafeRepository repository;
	
	@Autowired
	private ColaboradorRepository colabRepository;
	
	@Autowired
	private ColaboradorService colabService;
	
	@GetMapping("cafes")
	public ModelAndView index() {
		List<Cafe> cafes = service.findAll();
        ModelAndView mv = new ModelAndView("cafes/index");
        mv.addObject("cafes", cafes);
		return mv;
	}
	
	@GetMapping("cafes/new")
	public ModelAndView novo(Cafe cafe) {
		List<Colaborador> colaboradores = colabService.findAll();
		ModelAndView mv = new ModelAndView("cafes/new");
		mv.addObject("colaboradores", colaboradores);
		return mv;
	}
	
	@PostMapping("cafes")
	public ModelAndView insert(@Valid Cafe cafe, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			ModelAndView mv = new ModelAndView("cafes/new");
			mv.addObject("mensagem", "Cafe não inserido.");
			return mv;
		} else {
			if (repository.findByNome(cafe.getNome()) == null && cafe.getColaborador().getId() != null) {
				Long aux =  cafe.getColaborador().getId(); 
				Optional<Colaborador> optional = colabRepository.findById(aux);
				ModelAndView mv = new ModelAndView("redirect:/cafes");
				if (optional.isPresent()) {
					mv.addObject("mensagem", "Cafe " + cafe.getId() + "inserido com sucesso.");
					 service.insert(cafe);
				 }
				return mv;
			} else {
				ModelAndView mv = new ModelAndView("redirect:/cafes");
				mv.addObject("mensagem", "Cafe não inserido.");
				return mv;
			}
		}
	}
	
	@GetMapping("cafes/{id}/delete")
	public ModelAndView delete(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("redirect:/cafes");
		
		try {
			service.delete(id);
			mv.addObject("mensagem", "Cafe " + id + " deletado com sucesso.");
		}
		catch (ModelNotFoundException e) {
			mv.addObject("mensagem", "Cafe " + id + " não encontrado.");
		} 
		catch (DatabaseException e) {
			mv.addObject("mensagem", "Cafe " + id + " não pode ser deletado por conter café(s) em seu nome");
		}
		return mv;
	}
	
	@GetMapping("/cafes/{id}/edit")
	public ModelAndView edit(@PathVariable Long id, RequisicaoFormCafe requisicao) {
		Optional<Cafe> optional = repository.findById(id);
		
		if (optional.isPresent()) {
			Cafe cafe = optional.get();
			requisicao.fromCafe(cafe);
			
			ModelAndView mv = new ModelAndView("cafes/edit");
			mv.addObject("cafeId", cafe.getId());
			
			return mv;
		} else {
			return new ModelAndView("redirect:/cafes");
		}
	}
	
	@PostMapping("/cafes/{id}")
	public ModelAndView update(@PathVariable Long id, @Valid RequisicaoFormColaborador requisicao, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return null;
		} else {
			Optional<Cafe> optional = repository.findById(id);
			
			if (optional.isPresent()) {
				Cafe cafe = requisicao.toCafe(optional.get());
				repository.save(cafe);
				
				return new ModelAndView("redirect:/cafes");
			} else {
				return new ModelAndView("redirect:/cafes");
			}
		}
	}
}
