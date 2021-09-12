package com.cineApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cineApp.model.Reserva;
import com.cineApp.repository.ReservaRepository;

@RestController
@RequestMapping(value="/reserva")
public class ReservaController {
	
	@Autowired
	private ReservaRepository repo;
	
	@PostMapping(value = "/add")
	public String addReserva(@RequestBody  Reserva res) {
		System.out.print(res);

		repo.save(res);
		return "StatusCode: 200";
	}
}