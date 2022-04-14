package com.cineApp.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cineApp.schema.FuncionSchema;

import com.cineApp.model.Funcion;
import com.cineApp.model.Pelicula;
import com.cineApp.model.Sala;
import com.cineApp.repository.FuncionRepository;
import com.cineApp.repository.PeliculaRepository;
import com.cineApp.repository.SalaRepository;

@RestController
@RequestMapping(value="/funciones")
@CrossOrigin
public class FuncionController {
	
	@Autowired
	private FuncionRepository repo;
	@Autowired
	private SalaRepository repoSala;
	@Autowired
	private PeliculaRepository repoPel;

	
	/** Add   **/
	@PostMapping(value = "/")
	public ResponseEntity<?> addFuncion(@RequestBody FuncionSchema details) {		
		
		Optional<Sala> sala = repoSala.findById(details.sala_id);
		Optional<Pelicula> pelicula = repoPel.findById(details.pelicula_id);

		if((!sala.isPresent()) || (!pelicula.isPresent())) {
			return ResponseEntity.notFound().build();
		}
		
		Funcion funcion = new Funcion();
		funcion.setSala(sala.get());
		funcion.setPelicula(pelicula.get());
		funcion.setFechaFuncion(details.fechaFuncion);

		return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(funcion));
	} 
	
	/** Get One   **/
	@GetMapping(value = "/{id}")
	public ResponseEntity<?>  getByIdFuncion(@PathVariable  Integer id) {

		Optional<Funcion> funcion = repo.findById(id);
		
		if(!funcion.isPresent()) {
			return ResponseEntity.notFound().build();
					}

		return ResponseEntity.ok(funcion);
	}
	
	/** Get All   **/
	@GetMapping(value = "/")
	public ResponseEntity<?> getAllFunciones( ) {

		List<Funcion> funciones = repo.findAll();

		return ResponseEntity.ok(funciones);	
		}
	
	/** Update   **/
	@PutMapping(value="/{id}")
	public ResponseEntity<?>  updateFuncion(@RequestBody FuncionSchema details,@PathVariable Integer id) {

		Optional<Funcion> funcion = repo.findById(id);
		Optional<Sala> sala = repoSala.findById(details.sala_id);
		Optional<Pelicula> pelicula = repoPel.findById(details.pelicula_id);

		if((!sala.isPresent()) || (!pelicula.isPresent()) || (!funcion.isPresent())) {
			return ResponseEntity.notFound().build();
		}
		
		funcion.get().setPelicula(pelicula.get());
		funcion.get().setSala(sala.get());
		funcion.get().setFechaFuncion(details.fechaFuncion);
		return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(funcion.get()));
	}
	
	/** Delete   **/
	@DeleteMapping(value="/{id}")
	public ResponseEntity<?>  deleteFuncion(@PathVariable Integer id) {
		
		if(!repo.findById(id).isPresent()) {
			return ResponseEntity.notFound().build();
					}
		repo.deleteById(id);
				
		return ResponseEntity.ok().build();
	}
}