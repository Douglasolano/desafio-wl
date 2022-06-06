package com.solanodouglas.wl.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.solanodouglas.wl.model.Cafe;
import com.solanodouglas.wl.model.Colaborador;
import com.solanodouglas.wl.repository.CafeRepository;
import com.solanodouglas.wl.repository.ColaboradorRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner{

	@Autowired
	private ColaboradorRepository colaboradorRepository;
	
	@Autowired
	private CafeRepository cafeRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		Colaborador colaborador1 = new Colaborador(null, "Douglas", "85671380006");
		Colaborador colaborador2 = new Colaborador(null, "Maria", "56641672034");
		
		Cafe cafe1 = new Cafe(null, "Salgadinhos", colaborador1);
		Cafe cafe2 = new Cafe(null, "Bolinhos de chuva", colaborador2);
		Cafe cafe3 = new Cafe(null, "Bolo de chocolate", colaborador1);
		
		colaboradorRepository.saveAll(Arrays.asList(colaborador1, colaborador2));
		cafeRepository.saveAll(Arrays.asList(cafe1, cafe2, cafe3));
	}
}
