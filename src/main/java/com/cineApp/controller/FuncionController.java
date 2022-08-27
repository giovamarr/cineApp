package com.cineApp.controller;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.cineApp.exception.ApiRequestException;
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
		try {
		Optional<Sala> sala = repoSala.findById(details.sala_id);
		Optional<Pelicula> pelicula = repoPel.findById(details.pelicula_id);
		List<Funcion> funciones = funcionRepository.findByDateAndSala(details.fechaFuncion, details.sala_id);
				
		if((!sala.isPresent()) || (!pelicula.isPresent())) {
			return ResponseEntity.notFound().build();
		}
		if(details.fechaFuncion.compareTo(LocalDate.now()) < 0) {
			return ResponseEntity.badRequest().body("La fecha debe ser mayor o igual a hoy");
		}
		LocalTime hora_inicio_pelicula_nueva = details.horaFuncion;
		LocalTime hora_fin_pelicula_nueva = hora_inicio_pelicula_nueva.plusMinutes(pelicula.get().getDuration());
		
		//Valido que no haya una funcion en ese horario
		for (Funcion fun: funciones) {
			LocalTime hora_inicio_pelicula_existente = fun.getHoraFuncion();
			LocalTime hora_fin_pelicula_existente = hora_inicio_pelicula_existente.plusMinutes(fun.getPelicula().getDuration());
		
			if (((hora_inicio_pelicula_nueva.compareTo(hora_fin_pelicula_existente) < 0 ) 
					& hora_inicio_pelicula_nueva.compareTo(hora_inicio_pelicula_existente) > 0) 
					|| 
					((hora_fin_pelicula_nueva.compareTo(hora_inicio_pelicula_existente) > 0 ) 
							& hora_fin_pelicula_nueva.compareTo(hora_fin_pelicula_existente) < 0)
				)
			{
					return ResponseEntity.badRequest().body("Ya hay una funcion en ese horario en esa sala");
			}
		}	
		
		Funcion funcion = new Funcion();
		funcion.setSala(sala.get());
		funcion.setPelicula(pelicula.get());
		funcion.setFechaFuncion(details.fechaFuncion);
		funcion.setHoraFuncion(details.horaFuncion);
		

		return ResponseEntity.status(HttpStatus.CREATED).body(funcionRepository.save(funcion));
	}catch(Exception e){
		throw new ApiRequestException("Ha ocurrido un error", e);
	}

}
	
//	/** Get One   **/
	@GetMapping(value = "/{id}")
	public ResponseEntity<?>  getByIdFuncion(@PathVariable  Integer id) {
		try {
		Optional<Funcion> funcion = funcionRepository.findById(id);
		if(!funcion.isPresent()) {
			return ResponseEntity.notFound().build();
		}		

		return ResponseEntity.ok(funcion);
	}catch(Exception e){
		throw new ApiRequestException("Ha ocurrido un error", e);
	}

}
	
	/** Get Dates by Movie Id   **/
	@GetMapping(value = "/byMovie/{id}")
	public ResponseEntity<?>  getByIdMovie(@PathVariable  Integer id) {
		try {
		List<LocalDate> fechasFunciones= funcionRepository.findByMovieId(id);
		
//		Sin la validacion manejo mejor en los dropdown list dinamicos de react
//		if (fechasFunciones == null || fechasFunciones.isEmpty()) {
//			return ResponseEntity.ok("No hay fecha disponible");
//		}
		
		return ResponseEntity.ok(fechasFunciones);
	}catch(Exception e){
		throw new ApiRequestException("Ha ocurrido un error", e);
	}

}
	
	/** Get Dates by Movie Id   **/
	@GetMapping(value = "/byMovie/{id}/{fechaFuncion}")
	public ResponseEntity<?>  getByIdMovie(@PathVariable  Integer id, @PathVariable String fechaFuncion) {
		try {
		LocalDate localDate = LocalDate.parse(fechaFuncion);
		List<Funcion> funciones= funcionRepository.findByMovieIdAndDate(id, localDate);
		
//		Sin la validacion manejo mejor en los dropdown list dinamicos de react
//		if (fechasFunciones == null || fechasFunciones.isEmpty()) {
//			return ResponseEntity.ok("No hay fecha disponible");
//		}
		return ResponseEntity.ok(funciones);
	}catch(Exception e){
		throw new ApiRequestException("Ha ocurrido un error", e);
	}

}
	
	/** Get All   **/
	@GetMapping(value = "/")
	public ResponseEntity<?> getAllFunciones( ) {
		try {
		List<Funcion> funciones = funcionRepository.findAllAfterToday();
		return ResponseEntity.ok(funciones);
		}catch(Exception e){
			throw new ApiRequestException("Ha ocurrido un error", e);
		}
	
	}
	
	/** Update   **/
	@PutMapping(value="/{id}")
	public ResponseEntity<?>  updateFuncion(@RequestBody FuncionSchema details,@PathVariable Integer id) {
		try {
		Optional<Funcion> funcion = funcionRepository.findById(id);
		Optional<Sala> sala = repoSala.findById(details.sala_id);
		Optional<Pelicula> pelicula = repoPel.findById(details.pelicula_id);
		List<Funcion> funciones = funcionRepository.findByDateAndSala(details.fechaFuncion, details.sala_id);

		
		if((!sala.isPresent()) || (!pelicula.isPresent()) || (!funcion.isPresent())) {
			return ResponseEntity.notFound().build();
		}
		LocalTime hora_inicio_pelicula_nueva = details.horaFuncion;
		LocalTime hora_fin_pelicula_nueva = hora_inicio_pelicula_nueva.plusMinutes(pelicula.get().getDuration());
		
		//Valido que no haya una funcion en ese horario
		for (Funcion fun: funciones) {
			LocalTime hora_inicio_pelicula_existente = fun.getHoraFuncion();
			LocalTime hora_fin_pelicula_existente = hora_inicio_pelicula_existente.plusMinutes(fun.getPelicula().getDuration());
		
			if (((hora_inicio_pelicula_nueva.compareTo(hora_fin_pelicula_existente) < 0 ) 
					& hora_inicio_pelicula_nueva.compareTo(hora_inicio_pelicula_existente) > 0) 
					|| 
					((hora_fin_pelicula_nueva.compareTo(hora_inicio_pelicula_existente) > 0 ) 
							& hora_fin_pelicula_nueva.compareTo(hora_fin_pelicula_existente) < 0)
				)
			{
					return ResponseEntity.badRequest().body("Ya hay una funcion en ese horario en esa sala");
			}
		}	
		
		funcion.get().setPelicula(pelicula.get());
		funcion.get().setSala(sala.get());
		funcion.get().setFechaFuncion(details.fechaFuncion);
		funcion.get().setHoraFuncion(details.horaFuncion);		
		
		emailSenderService.sendDataModifiedFunction(id);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(funcionRepository.save(funcion.get()));
		}catch(Exception e){
			throw new ApiRequestException("Ha ocurrido un error", e);
		}
	
	}
	
	
	/** Delete   **/
	@DeleteMapping(value="/{id}")
	public ResponseEntity<?>  deleteFuncion(@PathVariable Integer id) {
		try {
		if(!funcionRepository.findById(id).isPresent()) {
			return ResponseEntity.notFound().build();
					}
		funcionRepository.deleteById(id);
		
		emailSenderService.sendCancelFunction(id);
						
		Map<String, String> map = new HashMap<String, String>();
		map.put("msg", "Funcion borrada correctamente");

        return new ResponseEntity<Object>(map,HttpStatus.OK);
	}catch(Exception e){
		throw new ApiRequestException("Ha ocurrido un error", e);
	}

}
}