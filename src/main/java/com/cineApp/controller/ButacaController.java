package com.cineApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cineApp.model.Butaca;
import com.cineApp.repository.ButacaRepository;

@RestController
@RequestMapping(value="/butaca")
public class ButacaController {
	
	@Autowired
	private ButacaRepository repo;
	
	@PostMapping(value = "/add")
	public String addButaca(@RequestBody  Butaca but) {
		System.out.print(but);

		repo.save(but);
		return "StatusCode: 200";
	}
	@GetMapping(value = "/all")
	public List<Butaca> getAllButacas() {
		List<Butaca> butacas=repo.findAll();
		return butacas;
	}
	
}
