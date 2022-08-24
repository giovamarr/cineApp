package com.cineApp.controller;



import java.util.Optional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.cineApp.exception.ApiRequestException;
import com.cineApp.model.Butaca;
import com.cineApp.model.Funcion;
import com.cineApp.model.Reserva;
import com.cineApp.model.Sala;
import com.cineApp.repository.ButacaRepository;
import com.cineApp.repository.FuncionRepository;
import com.cineApp.repository.ReservaRepository;
import com.cineApp.repository.SalaRepository;
import com.cineApp.service.EmailSenderService;




@RestController
@RequestMapping(value="/salas")
@CrossOrigin
public class SalaController {
	
	@Autowired
	private SalaRepository repo;
	@Autowired
	private ButacaRepository butacaRepository;
	@Autowired
	private FuncionRepository funcionRepository;
	@Autowired
	private ReservaRepository reservaRepository;
	@Autowired
	private EmailSenderService emailSenderService;
	
	/** Add   **/
	@PostMapping(value = "/")
	public ResponseEntity<?> addSala(@RequestBody  Sala sala) {		
		try {
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
	}catch(Exception e){
		throw new ApiRequestException("Ha ocurrido un error", e);
	}
	}
	
	/** Get One By Name   **/
	@GetMapping(value = "/name/{name}")
	public ResponseEntity<?> getByNameSala(@PathVariable  String name) {
		try {
		Optional<Sala> sala = repo.findByName(name);
		
		if(!sala.isPresent()) {
			return ResponseEntity.notFound().build();
					}
		

		return ResponseEntity.ok(sala);
	}	catch(Exception e){
		throw new ApiRequestException("Ha ocurrido un error", e);
	}
	}
	
	/** Get One   **/
	@GetMapping(value = "/{id}")
	public ResponseEntity<?>  getByIdSala(@PathVariable  Integer id) {
		try {
		Optional<Sala> sala = repo.findById(id);
		
		if(!sala.isPresent()) {
			return ResponseEntity.notFound().build();
					}

		return ResponseEntity.ok(sala);
	}catch(Exception e){
		throw new ApiRequestException("Ha ocurrido un error", e);
	}}
	
	/** Get All   **/
	@GetMapping(value = "/")
	public ResponseEntity<?> getAllPelicula( ) {
		try {
			System.out.print("asdas");
		List<Sala> salas = repo.findAll();

		return ResponseEntity.ok(salas);	
		}	catch(Exception e){
			throw new ApiRequestException("Ha ocurrido un error", e);
		}}
	
	/** Update   **/
	@PutMapping(value="/")
	public ResponseEntity<?>  updateSala(@RequestBody  Sala details) {
		try {
		Optional<Sala> sala = repo.findById(details.getId());
		
		if(!sala.isPresent()) {
			return ResponseEntity.notFound().build();
					}
		sala.get().setName(details.getName());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(sala.get()));
		}catch(Exception e){
			throw new ApiRequestException("Ha ocurrido un error", e);
		}
	}
	
	/** Update   **/
	@PutMapping(value="/mantenimiento")
	public ResponseEntity<?>  mantenimientoSala(@RequestBody  Sala details) {
		try {
		Optional<Sala> sala = repo.findById(details.getId());
		
		if(!sala.isPresent()) {
			return ResponseEntity.notFound().build();
					}

		List<Funcion> funciones = funcionRepository.findBySalaAfterToday(sala.get().getId());
		for (Funcion funcion:funciones) 
		{ 
			emailSenderService.sendDataModifiedFunction(funcion.getId());

			reservaRepository.deleteByFuncion(funcion.getId());
			
			funcionRepository.deleteById(funcion.getId());
			
			
		}
		sala.get().setState(false);
		repo.save(sala.get());
		
		Map<String,Object> msg= new HashMap<String, Object>();
        msg.put("message", "Sala en mantenimiento.");
        return ResponseEntity.status(HttpStatus.OK).body(msg);
        
		}catch(Exception e){
			throw new ApiRequestException("Ha ocurrido un error", e);
		}
	}	
	
	
	
	
	/** Update   **/
	@PutMapping(value="/axis/{isrow}")
	public ResponseEntity<?>  updateAxis(@RequestBody Sala details, @PathVariable  Integer isrow) {
		try {
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
	}catch(Exception e){
		throw new ApiRequestException("Ha ocurrido un error", e);
	}
	}
	
	
	/** Delete   **/
	@DeleteMapping(value="/{id}")
	public ResponseEntity<?>  deleteSala(@PathVariable Integer id) {
		try {
		if(!repo.findById(id).isPresent()) {
			return ResponseEntity.notFound().build();
					}
		repo.deleteById(id);
				
		return ResponseEntity.ok().build();
	}	catch(Exception e){
		throw new ApiRequestException("Ha ocurrido un error", e);
	}}
}
