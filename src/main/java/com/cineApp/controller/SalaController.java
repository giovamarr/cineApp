package com.cineApp.controller;



import java.util.Optional;
import java.util.List;

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
import com.cineApp.model.Sala;
import com.cineApp.repository.ButacaRepository;
import com.cineApp.repository.SalaRepository;




@RestController
@RequestMapping(value="/salas")
@CrossOrigin
public class SalaController {
	
	@Autowired
	private SalaRepository repo;
	@Autowired
	private ButacaRepository butacaRepository;
	
	@PostMapping(value = "/")
	public ResponseEntity<?> addSala(@RequestBody  Sala sala) {		
		Sala newsala = repo.save(sala);
		for (int x=1; x<=sala.getNumber_row(); x++) 
		{ 
			for (int y=1; y<=sala.getNumber_column(); y++)
			{ 
				Butaca butaca = new Butaca();
				butaca.setSala(sala);
				butaca.setPosition_x(x);
				butaca.setPosition_y(y);
				butaca.setState(true);
				butacaRepository.save(butaca);
			}
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(newsala);
	}
	
	
	@GetMapping(value = "/name/{name}")
	public ResponseEntity<?> getByNameSala(@PathVariable  String name) {

		Optional<Sala> sala = repo.findByName(name);
		
		if(!sala.isPresent()) {
			return ResponseEntity.notFound().build();
					}
		

		return ResponseEntity.ok(sala);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?>  getByIdSala(@PathVariable  Integer id) {

		Optional<Sala> sala = repo.findById(id);
		
		if(!sala.isPresent()) {
			return ResponseEntity.notFound().build();
					}

		return ResponseEntity.ok(sala);
	}
	
	@GetMapping(value = "/")
	public ResponseEntity<?> getAllPelicula( ) {

		List<Sala> salas = repo.findAll();

		return ResponseEntity.ok(salas);	
		}
	
	@PutMapping(value="/")
	public ResponseEntity<?>  updateSala(@RequestBody  Sala details) {

		Optional<Sala> sala = repo.findById(details.getId());
		
		if(!sala.isPresent()) {
			return ResponseEntity.notFound().build();
					}
		sala.get().setName(details.getName());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(sala.get()));
	}
	
	@PutMapping(value="/axis/{isrow}")
	public ResponseEntity<?>  updateAxis(@RequestBody Sala details, @PathVariable  Integer isrow) {

		Optional<Sala> sala = repo.findById(details.getId());
		
		if(!sala.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		if(isrow == 1){
			butacaRepository.deleteByRow(sala.get().getId(),sala.get().getNumber_row());
			sala.get().setNumber_row(sala.get().getNumber_row()-1); 
		}
		else {
			butacaRepository.deleteByColumn(sala.get().getId(),sala.get().getNumber_column());
			sala.get().setNumber_column(sala.get().getNumber_column()-1);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(sala.get()));
	}
	
	
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<?>  deleteSala(@PathVariable Integer id) {
		
		if(!repo.findById(id).isPresent()) {
			return ResponseEntity.notFound().build();
					}
		repo.deleteById(id);
				
		return ResponseEntity.ok().build();
	}

}
