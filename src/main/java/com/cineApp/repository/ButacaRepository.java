package com.cineApp.repository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.cineApp.model.Butaca;
import com.cineApp.model.Funcion;
import com.cineApp.model.Pelicula;
import com.cineApp.model.Sala;

public interface ButacaRepository extends JpaRepository< Butaca, Integer> {
	@Transactional
	@Modifying
	@Query("DELETE FROM Butaca but WHERE but.sala.id=?1 and but.position_x=?2")
	void deleteByRow(Integer idSala, Integer position_x);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM Butaca but WHERE but.sala.id=?1 and but.position_y=?2")
	void deleteByColumn(Integer idSala, Integer position_y);
	
	@Query("SELECT but from Reserva res join res.funcion fun join fun.sala sal join sal.butaca but where but.state = 1 and fun.id = ?1")
	List<Butaca> findByFuncionId(Integer funcionId);
}
