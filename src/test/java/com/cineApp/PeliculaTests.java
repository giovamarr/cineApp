package com.cineApp;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.cineApp.model.Pelicula;
import com.cineApp.repository.PeliculaRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class PeliculaTests {
	@Autowired
	private PeliculaRepository repoPelicula;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreatePelicula() {
		Pelicula pel = new Pelicula();
		pel.setName("Rapido y furiosos 9.");
		pel.setDescription("Pelado mata a todos.");
		pel.setDuration(143);
		pel.setPoster("/aIyqWTuf6NouKphVlxzbv9pblxQ.jpg");

		
		Pelicula newPelicula = repoPelicula.save(pel);
		Pelicula existPelicula = entityManager.find(Pelicula.class, newPelicula.getId());
		
		assertThat(existPelicula.getName()).isEqualTo(pel.getName());
	}
	
	
	
	@Test
	public void testFindPeliculaByName() {
		String name = "Rapido y furiosos 9.";
		Optional<Pelicula> pel = repoPelicula.findByName(name);
		
		assertThat(pel).isNotNull();		
	}
	
}
