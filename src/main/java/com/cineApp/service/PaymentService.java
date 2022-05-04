package com.cineApp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cineApp.model.Pelicula;
import com.cineApp.model.Tarjeta;
import com.cineApp.repository.TarjetaRepository;

@Service
public class PaymentService {
	@Autowired
	private TarjetaRepository tarjetaRepository;
	public Boolean searchCreditCard(String numberCard, String cvc, String owner, String expiry) {
		Optional<Tarjeta> tarjeta =tarjetaRepository.findCreditCardBydata(numberCard, cvc, owner, expiry);
		if(!tarjeta.isPresent()) {
			return false;
		}else {
			return true;
		}
	}
	


}
