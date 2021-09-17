package com.cineApp.controller;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cineApp.model.Pelicula;
import com.cineApp.repository.PeliculaRepository;




@RestController
@RequestMapping(value="/pelicula")
public class PeliculaController {
	
	@Autowired
	private PeliculaRepository repo;
	
	@PostMapping(value = "/add")
	public String addPelicula(@RequestBody  Pelicula pel) {
		System.out.print(pel);

		repo.save(pel);
		return "StatusCode: 200";
	}
	
	@GetMapping(value = "/all")
	public List<Pelicula> getAllPelicula( ) {

		List<Pelicula> pelis = repo.findAll();

		return pelis;
	}
	
	@GetMapping(value = "/get/name/{name}")
	public Pelicula getByNamePelicula(@PathVariable  String name) {
		System.out.print(name);

		Pelicula pel = repo.findByName(name);
		System.out.print(pel);

		return pel;
	}
	@GetMapping(value = "/get/id/{id}")
	public Optional getByIdPelicula(@PathVariable  Integer id) {
		System.out.print(id);

		Optional<Pelicula> pel = repo.findById(id);
		System.out.print(pel);

		return pel;
	}

}
