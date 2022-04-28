package com.cineApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.cineApp.model.Funcion;
import com.cineApp.model.Reserva;
import com.cineApp.schema.ListaVentaPeliculas;


public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
	
	@Query("SELECT fun.pelicula.name, count(res) from Reserva res JOIN res.funcion fun group by fun.pelicula")
	List<Object[]> listadoVentasPelicula();
	
	@Query("SELECT fun.id,fun.fechaFuncion, fun.horaFuncion, count(res) from Reserva res JOIN res.funcion fun group by fun")
	List<Object[]> listadoVentasFuncion();
	
}
