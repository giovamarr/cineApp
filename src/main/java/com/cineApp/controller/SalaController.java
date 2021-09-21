package com.cineApp.controller;



import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public ResponseEntity<?> addSala(@RequestBody  Sala sala) {		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(sala));
	}
	
	
	@GetMapping(value = "/get/name/{name}")
	public ResponseEntity<?> getByNameSala(@PathVariable  String name) {

		Optional<Sala> pel = repo.findByName(name);
		
		if(!pel.isPresent()) {
			return ResponseEntity.notFound().build();
					}

		return ResponseEntity.ok(pel);
	}
	
	@GetMapping(value = "/get/{id}")
	public ResponseEntity<?>  getByIdSala(@PathVariable  Integer id) {

		Optional<Sala> sala = repo.findById(id);
		
		if(!sala.isPresent()) {
			return ResponseEntity.notFound().build();
					}

		return ResponseEntity.ok(sala);
	}
	
	@GetMapping(value = "/all")
	public ResponseEntity<?> getAllPelicula( ) {

		List<Sala> salas = repo.findAll();

		return ResponseEntity.ok(salas);	
		}
	
	@PutMapping(value="/update/{id}")
	public ResponseEntity<?>  updateSala(@RequestBody  Sala details,@PathVariable Integer id) {

		Optional<Sala> sala = repo.findById(id);
		
		if(!sala.isPresent()) {
			return ResponseEntity.notFound().build();
					}
		sala.get().setName(details.getName());
		//faltan los otros atributos
		
		return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(sala.get()));
	}
	
	@DeleteMapping(value="/delete/{id}")
	public ResponseEntity<?>  deleteSala(@PathVariable Integer id) {
		
		if(!repo.findById(id).isPresent()) {
			return ResponseEntity.notFound().build();
					}
		repo.deleteById(id);
				
		return ResponseEntity.ok().build();
	}

}
