package com.cineApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cineApp.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	// @Query(value = "SELEC * FROM USERS WHERE email = ?1 and password=?2", nativeQuery = true)
	@Query("SELECT u from User u where u.email = ?1 and u.password= ?2")
	User findByEmailAndPassword(String email, String password);
	
	@Query("SELECT u from User u where u.email = ?1")
	User findByEmail(String email);
	

}
