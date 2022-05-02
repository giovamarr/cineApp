package com.cineApp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.cineApp.model.Butaca;
import com.cineApp.model.Funcion;
import com.cineApp.model.Pelicula;
import com.cineApp.model.Reserva;
import com.cineApp.model.Sala;
import com.cineApp.model.User;
import com.cineApp.repository.ButacaRepository;
import com.cineApp.repository.FuncionRepository;
import com.cineApp.repository.PeliculaRepository;
import com.cineApp.repository.ReservaRepository;
import com.cineApp.repository.SalaRepository;
import com.cineApp.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ReservaTests {
	
//	@Autowired
//	private UserRepository repoUser;
	
	@Autowired
	private ReservaRepository repoReserva;
	
	@Autowired
	private SalaRepository repoSala;
	
	@Autowired
	private ButacaRepository repoButa;
	
	
	@Autowired
	private FuncionRepository repoFuncion;

	@Autowired
	private PeliculaRepository repoPelicula;

	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateReserva() {
		Sala sala = new Sala();
		sala.setName("Sala Terciaria");
		sala.setState(true);

		Butaca buta1 = new Butaca();
		buta1.setSala(sala);
		sala.setState(true);
		
		Butaca buta2 = new Butaca();
		buta2.setSala(sala);
		sala.setState(true);
		
		Butaca buta3 = new Butaca();
		buta3.setSala(sala);
		sala.setState(true);
		
		Butaca buta4 = new Butaca();
		buta4.setSala(sala);
		sala.setState(true);

		Pelicula pel = new Pelicula();
		pel.setName("El padrino");
		pel.setDescription("Mata a todos ");
		
		repoSala.save(sala);		
		
		repoButa.save(buta1);		
		repoButa.save(buta2);
		repoButa.save(buta3);
		repoButa.save(buta4);
		
		repoPelicula.save(pel);
		
		Funcion fun = new Funcion();
		fun.setSala(sala);
		fun.setPelicula(pel);
		
		repoFuncion.save(fun);
		
//		String email = "gio@gmail.com";
//		User user = repoUser.findByEmail(email);
		
		
		Reserva res = new Reserva();
		res.setFuncion(fun);
//		res.setUser(user);

		Reserva newRes = repoReserva.save(res);
		Reserva existRes = entityManager.find(Reserva.class, newRes.getId());
		
		assertThat(existRes.getId()).isEqualTo(res.getId());
		
	}
}