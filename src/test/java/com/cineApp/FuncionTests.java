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
import com.cineApp.model.Sala;
import com.cineApp.repository.ButacaRepository;
import com.cineApp.repository.FuncionRepository;
import com.cineApp.repository.PeliculaRepository;
import com.cineApp.repository.SalaRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class FuncionTests {
	
	
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
	public void testCreateFuncion() {
		Sala sala = new Sala();
		sala.setName("Sala Secundaria");
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
		pel.setName("Rapido y furiosos");
		pel.setDescription("rapidos y furiosos 9. ");
		
		repoSala.save(sala);		
		
		repoButa.save(buta1);		
		repoButa.save(buta2);
		repoButa.save(buta3);
		repoButa.save(buta4);
		
		repoPelicula.save(pel);
		
		Funcion fun = new Funcion();
		fun.setSala(sala);
		fun.setPelicula(pel);
		
		Funcion newFun = repoFuncion.save(fun);
		Funcion existFun = entityManager.find(Funcion.class, newFun.getId());

		
		assertThat(existFun.getId()).isEqualTo(fun.getId());

		
	}
}