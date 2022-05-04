package com.cineApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cineApp.model.Tarjeta;
import com.cineApp.model.User;

@Repository
public interface TarjetaRepository extends JpaRepository<Tarjeta, Long> {
	@Query("SELECT t from Tarjeta t where t.numberCard=?1 and t.cvc=?2 and t.owner=?3 and t.expiry=?4")
	Optional<Tarjeta> findCreditCardBydata(String numberCard, String cvc, String owner, String expiry);
	
	

}
