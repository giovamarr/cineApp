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

import com.cineApp.model.Funcion;
import com.cineApp.repository.FuncionRepository;

@RestController
@RequestMapping(value="/funcion")
@CrossOrigin
public class FuncionController {
	
	@Autowired
	private FuncionRepository repo;
	
	@PostMapping(value = "/add")
	public ResponseEntity<?> addSFuncion(@RequestBody  Funcion funcion) {		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(funcion));
	}
	
	
	@GetMapping(value = "/get/{id}")
	public ResponseEntity<?>  getByIdFuncion(@PathVariable  Integer id) {

		Optional<Funcion> funcion = repo.findById(id);
		
		if(!funcion.isPresent()) {
			return ResponseEntity.notFound().build();
					}

		return ResponseEntity.ok(funcion);
	}
	
	@GetMapping(value = "/all")
	public ResponseEntity<?> getAllFunciones( ) {

		List<Funcion> funciones = repo.findAll();

		return ResponseEntity.ok(funciones);	
		}
	
	@PutMapping(value="/update/{id}")
	public ResponseEntity<?>  updateFuncion(@RequestBody  Funcion details,@PathVariable Integer id) {

		Optional<Funcion> funcion = repo.findById(id);
		
		if(!funcion.isPresent()) {
			return ResponseEntity.notFound().build();
					}
		
		//faltan los otros atributos
		
		return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(funcion.get()));
	}
	
	@DeleteMapping(value="/delete/{id}")
	public ResponseEntity<?>  deleteFuncion(@PathVariable Integer id) {
		
		if(!repo.findById(id).isPresent()) {
			return ResponseEntity.notFound().build();
					}
		repo.deleteById(id);
				
		return ResponseEntity.ok().build();
	}
}