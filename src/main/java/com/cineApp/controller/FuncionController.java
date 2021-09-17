package com.cineApp.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cineApp.model.Funcion;
import com.cineApp.repository.FuncionRepository;

@RestController
@RequestMapping(value="/funcion")
public class FuncionController {
	
	@Autowired
	private FuncionRepository repo;
	
	@PostMapping(value = "/add")
	public String addFuncion(@RequestBody  Funcion fun) {

		repo.save(fun);
		return "StatusCode: 200";
	}
	@GetMapping(value = "/all")
	public List<Funcion> getAllFuncion() {
		List<Funcion> fun=repo.findAll();
		return fun;
	}
}