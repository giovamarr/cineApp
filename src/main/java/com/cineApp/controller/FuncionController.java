package com.cineApp.controller;


import java.time.LocalDate;
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
import com.cineApp.service.EmailSenderService;
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
	private FuncionRepository funcionRepository;
	@Autowired
	private SalaRepository repoSala;
	@Autowired
	private PeliculaRepository repoPel;
	@Autowired
	private EmailSenderService emailSenderService;

	
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
		funcion.setHoraFuncion(details.horaFuncion);
		

		return ResponseEntity.status(HttpStatus.CREATED).body(funcionRepository.save(funcion));
	} 
	
//	/** Get One   **/
	@GetMapping(value = "/{id}")
	public ResponseEntity<?>  getByIdFuncion(@PathVariable  Integer id) {
		
		Optional<Funcion> funcion = funcionRepository.findById(id);
		if(!funcion.isPresent()) {
			return ResponseEntity.notFound().build();
		}		

		return ResponseEntity.ok(funcion);
	}
	
	/** Get Dates by Movie Id   **/
	@GetMapping(value = "/byMovie/{id}")
	public ResponseEntity<?>  getByIdMovie(@PathVariable  Integer id) {

		List<LocalDate> fechasFunciones= funcionRepository.findByMovieId(id);
		
//		Sin la validacion manejo mejor en los dropdown list dinamicos de react
//		if (fechasFunciones == null || fechasFunciones.isEmpty()) {
//			return ResponseEntity.ok("No hay fecha disponible");
//		}
		
		return ResponseEntity.ok(fechasFunciones);
	}
	
	/** Get Dates by Movie Id   **/
	@GetMapping(value = "/byMovie/{id}/{fechaFuncion}")
	public ResponseEntity<?>  getByIdMovie(@PathVariable  Integer id, @PathVariable String fechaFuncion) {

		LocalDate localDate = LocalDate.parse(fechaFuncion);
		List<Funcion> funciones= funcionRepository.findByMovieIdAndDate(id, localDate);
		
//		Sin la validacion manejo mejor en los dropdown list dinamicos de react
//		if (fechasFunciones == null || fechasFunciones.isEmpty()) {
//			return ResponseEntity.ok("No hay fecha disponible");
//		}
		return ResponseEntity.ok(funciones);
	}
	
	/** Get All   **/
	@GetMapping(value = "/")
	public ResponseEntity<?> getAllFunciones( ) {

		List<Funcion> funciones = funcionRepository.findAll();
		
		return ResponseEntity.ok(funciones);	
		}
	
	/** Update   **/
	@PutMapping(value="/{id}")
	public ResponseEntity<?>  updateFuncion(@RequestBody FuncionSchema details,@PathVariable Integer id) {

		Optional<Funcion> funcion = funcionRepository.findById(id);
		Optional<Sala> sala = repoSala.findById(details.sala_id);
		Optional<Pelicula> pelicula = repoPel.findById(details.pelicula_id);

		if((!sala.isPresent()) || (!pelicula.isPresent()) || (!funcion.isPresent())) {
			return ResponseEntity.notFound().build();
		}
		
		funcion.get().setPelicula(pelicula.get());
		funcion.get().setSala(sala.get());
		funcion.get().setFechaFuncion(details.fechaFuncion);
		funcion.get().setHoraFuncion(details.horaFuncion);		
		
		emailSenderService.sendDataModifiedFunction(id);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(funcionRepository.save(funcion.get()));
	}
	
	/** Delete   **/
	@DeleteMapping(value="/{id}")
	public ResponseEntity<?>  deleteFuncion(@PathVariable Integer id) {
		
		if(!funcionRepository.findById(id).isPresent()) {
			return ResponseEntity.notFound().build();
					}
		funcionRepository.deleteById(id);
		
		emailSenderService.sendCancelFunction(id);
						
		return ResponseEntity.ok().build();
	}
}