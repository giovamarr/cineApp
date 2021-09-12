package com.cineApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cineApp.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	@Query("SELECT u from User u where u.email = ?1")
	User findByEmail(String email);

}
