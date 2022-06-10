package com.solanodouglas.wl.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.solanodouglas.wl.model.Cafe;
import com.solanodouglas.wl.repository.CafeRepository;
import com.solanodouglas.wl.service.CafeService;
import com.solanodouglas.wl.service.exceptions.ModelNotFoundException;

@Controller
public class CafeController {

	@Autowired
	private CafeService service;
	
	@Autowired
	private CafeRepository repository;
	
	@GetMapping("cafes")
	public ModelAndView index() {
		List<Cafe> cafes = service.findAll();
        ModelAndView mv = new ModelAndView("cafes/index");
        mv.addObject("cafes", cafes);
		return mv;
	}
	
	@GetMapping("/cafes/new")
	public ModelAndView novo(Cafe cafe) {
		ModelAndView mv = new ModelAndView("cafes/new");
		return mv;
	}
	
	@PostMapping("/cafes")
	public ModelAndView insert(@Valid Cafe cafe, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			ModelAndView mv = new ModelAndView("cafes/new");
			return mv;
		} else {
			if (repository.findByNome(cafe.getNome()) == null) {
				service.insert(cafe);
				return new ModelAndView("redirect:/cafes");
			} else {
				return new ModelAndView("redirect:/cafes");
			}
		}
	}
	
	@GetMapping("/cafes/{id}/delete")
	public ModelAndView delete(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("redirect:/cafes");
		
		try {
			service.delete(id);
			mv.addObject("mensagem", "Cafe " + id + " deletado com sucesso.");
		}
		catch (ModelNotFoundException e) {
			mv.addObject("mensagem", "Cafe " + id + " não encontrado.");
		}
		return mv;
	}
}
