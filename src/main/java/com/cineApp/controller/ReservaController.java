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
import com.cineApp.model.Reserva;
import com.cineApp.model.User;
import com.cineApp.repository.FuncionRepository;
import com.cineApp.repository.ReservaRepository;
import com.cineApp.repository.UserRepository;
import com.cineApp.schema.ReservaSchema;

@RestController
@RequestMapping(value="/reservas")
@CrossOrigin
public class ReservaController {
	
	@Autowired
	private ReservaRepository reservaRepository;
	@Autowired
	private FuncionRepository funcionRepository;
	@Autowired
	private UserRepository userRepository;
	
	
	/** Add   **/
	@PostMapping(value = "/")
	public ResponseEntity<?> addReserva(@RequestBody ReservaSchema details) {		
		
		Optional<User> user = userRepository.findById(details.user_id);
		Optional<Funcion> funcion = funcionRepository.findById(details.funcion_id);

		if((!user.isPresent()) || (!funcion.isPresent())) {
			return ResponseEntity.notFound().build();
		}
		
		Reserva reserva = new Reserva();
		reserva.setUser(user.get());
		reserva.setFuncion(funcion.get());
		reserva.setFechaCompra(details.fechaCompra);

		return ResponseEntity.status(HttpStatus.CREATED).body(reservaRepository.save(reserva));
	} 
	
	/** Get One   **/
	@GetMapping(value = "/{id}")
	public ResponseEntity<?>  getByIdReserva(@PathVariable  Integer id) {

		Optional<Reserva> reserva = reservaRepository.findById(id);
		
		if(!reserva.isPresent()) {
			return ResponseEntity.notFound().build();
					}

		return ResponseEntity.ok(reserva);
	}
	
	
	/** Get All   **/
	@GetMapping(value = "/")
	public ResponseEntity<?> getAllReservas( ) {

		List<Reserva> reserva = reservaRepository.findAll();

		return ResponseEntity.ok(reserva);	
		}
	
	/** Update   **/
	@PutMapping(value="/{id}")
	public ResponseEntity<?>  updateFuncion(@RequestBody ReservaSchema details,@PathVariable Integer id) {

		Optional<Reserva> reserva = reservaRepository.findById(id);
		Optional<User> user = userRepository.findById(details.user_id);
		Optional<Funcion> funcion = funcionRepository.findById(details.funcion_id);

		if((!reserva.isPresent()) || (!user.isPresent()) || (!funcion.isPresent())) {
			return ResponseEntity.notFound().build();
		}
		
		reserva.get().setUser(user.get());
		reserva.get().setFuncion(funcion.get());
		reserva.get().setFechaCompra(details.fechaCompra);
		return ResponseEntity.status(HttpStatus.CREATED).body(reservaRepository.save(reserva.get()));
	}
	
	/** Delete   **/
	@DeleteMapping(value="/{id}")
	public ResponseEntity<?>  deleteReserva(@PathVariable Integer id) {
		
		if(!reservaRepository.findById(id).isPresent()) {
			return ResponseEntity.notFound().build();
					}
		reservaRepository.deleteById(id);
				
		return ResponseEntity.ok().build();
	}
	

	
}