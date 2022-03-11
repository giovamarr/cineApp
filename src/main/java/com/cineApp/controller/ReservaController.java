package com.cineApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cineApp.model.Pelicula;
import com.cineApp.model.Reserva;
import com.cineApp.repository.ReservaRepository;

@RestController
@RequestMapping(value="/reservas")
@CrossOrigin
public class ReservaController {
	
	@Autowired
	private ReservaRepository repo;
	
	@PostMapping(value = "/")
	public ResponseEntity<?> addReserva(@RequestBody  Reserva res) {

		return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(res));
	}
	
}