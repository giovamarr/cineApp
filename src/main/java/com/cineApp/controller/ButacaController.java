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

import com.cineApp.model.Butaca;
import com.cineApp.repository.ButacaRepository;

@RestController
@RequestMapping(value="/butaca")
@CrossOrigin
public class ButacaController {
	
	@Autowired
	private ButacaRepository repo;
	
	@PostMapping(value = "/add")
	public ResponseEntity<?> addButaca(@RequestBody  Butaca butaca) {		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(butaca));
	}
	
	
	@GetMapping(value = "/get/{id}")
	public ResponseEntity<?>  getByIdButaca(@PathVariable  Integer id) {

		Optional<Butaca> butaca = repo.findById(id);
		
		if(!butaca.isPresent()) {
			return ResponseEntity.notFound().build();
					}

		return ResponseEntity.ok(butaca);
	}
	

	@PutMapping(value="/update/{id}")
	public ResponseEntity<?>  updateButaca(@RequestBody  Butaca details,@PathVariable Integer id) {

		Optional<Butaca> butaca = repo.findById(id);
		
		if(!butaca.isPresent()) {
			return ResponseEntity.notFound().build();
					}
		
		//faltan los otros atributos (ver peliculacontrolles)
		
		return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(butaca.get()));
	}
	
	@DeleteMapping(value="/delete/{id}")
	public ResponseEntity<?>  deleteButaca(@PathVariable Integer id) {
		
		if(!repo.findById(id).isPresent()) {
			return ResponseEntity.notFound().build();
					}
		repo.deleteById(id);
				
		return ResponseEntity.ok().build();
	}
	
}
