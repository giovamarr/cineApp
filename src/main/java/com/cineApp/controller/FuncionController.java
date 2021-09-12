package com.cineApp.controller;


import org.springframework.beans.factory.annotation.Autowired;
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
		System.out.print(fun);

		repo.save(fun);
		return "StatusCode: 200";
	}
}