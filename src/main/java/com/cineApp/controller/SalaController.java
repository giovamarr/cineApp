package com.cineApp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cineApp.model.Sala;
import com.cineApp.repository.SalaRepository;




@RestController
@RequestMapping(value="/sala")
public class SalaController {
	
	@Autowired
	private SalaRepository repo;
	
	@PostMapping(value = "/add")
	public String addSala(@RequestBody  Sala sala) {
		System.out.print(sala);

		repo.save(sala);
		return "StatusCode: 200";
	}
	
	@GetMapping(value = "/get/name/{name}")
	public String getByNameSala(@PathVariable  String name) {
		System.out.print(name);

		Sala sal = repo.findByName(name);
		System.out.print(sal);

		return "StatusCode: 200";
	}
	@GetMapping(value = "/get/id/{id}")
	public String getByIdSala(@PathVariable  Integer id) {
		System.out.print(id);

		Optional<Sala> sal = repo.findById(id);
		System.out.print(sal);

		return "StatusCode: 200";
	}

}
