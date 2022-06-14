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
import com.cineApp.model.Reserva;
import com.cineApp.schema.ListaVentaPeliculas;
import com.cineApp.schema.ReservaSinFuncionSchema;


public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
	
	
	@Query("SELECT fun.pelicula.name, count(res) from Reserva res JOIN res.funcion fun group by fun.pelicula")
	List<Object[]> listadoVentasPelicula();
	
	@Query("SELECT fun.id,fun.pelicula.name,fun.sala.name,fun.fechaFuncion, fun.horaFuncion, count(res) from Reserva res JOIN res.funcion fun group by fun")
	List<Object[]> listadoVentasFuncion();
	
	@Query("SELECT res from Reserva res where res.code=?1 and res.email=?2")
	Optional<Reserva> getReservabyCodeandEmail(String code, String email);
	
	@Query("SELECT res from Reserva res JOIN res.funcion fun where fun.id=?1")
	List<Reserva> getallbyFunction(Integer id);
	
	
	@Transactional
	@Modifying
	@Query("delete from Reserva res where res.code=?1 and res.email=?2")
	void deleteReserva(String code,String email);
	
}
