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
import com.cineApp.model.Sala;
import com.cineApp.repository.ButacaRepository;
import com.cineApp.repository.SalaRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class SalaTests {
	@Autowired
	private SalaRepository repoSala;
	
	@Autowired
	private ButacaRepository repoButa;

	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateSala() {
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

		
		Sala newSala = repoSala.save(sala);
		Sala existSala = entityManager.find(Sala.class, newSala.getId());
		
		assertThat(existSala.getName()).isEqualTo(sala.getName());
		
		Butaca newButaca = repoButa.save(buta1);
		Butaca existButa = entityManager.find(Butaca.class, newButaca.getId());
		
		
		assertThat(existButa.getId()).isEqualTo(buta1.getId());
		
		repoButa.save(buta2);
		repoButa.save(buta3);
		repoButa.save(buta4);
	}
	
	
	@Test
	public void testFindSalaName() {
		String name = "Sala Secundaria";
		Sala sala = repoSala.findByName(name);
		
		assertThat(sala).isNotNull();		
	}
		

}
