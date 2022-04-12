package com.cineApp.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.cineApp.model.Butaca;
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
}
