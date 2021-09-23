package com.cineApp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.annotation.Rollback;

import com.cineApp.model.User;
import com.cineApp.repository.UserRepository;




@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserTests {
	
	@Autowired
	private UserRepository repo;
	
	
	@Autowired
	private TestEntityManager entityManager;
@Test
public void testCreateUser() {
	User user = new User();
	user.setEmail("dami@gmail.com");
	user.setPassword("123");
	user.setLastName("Barzo");
	user.setFirstName("Dami");
	
	User newUser = repo.save(user);
	User existUser = entityManager.find(User.class, newUser.getId());
			
	assertThat(existUser.getEmail()).isEqualTo(user.getEmail());
		}
@Test
public void testFindUserByEmail() {
	String email = "dami@gmail.com";
	User user = repo.findByEmail(email);
	
	assertThat(user).isNotNull();		
}
@Test
public void testFindUserByEmailandPass() {
	String email = "dami@gmail.com";
	String password = "123";
	User user = repo.findByEmailAndPassword(email,password);
	
	assertThat(user).isNotNull();		
}
	
	

	
	
	
}
