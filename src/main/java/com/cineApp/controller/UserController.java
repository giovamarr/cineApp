package com.cineApp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import com.cineApp.model.Pelicula;
import com.cineApp.model.User;
import com.cineApp.repository.UserRepository;
import com.fasterxml.jackson.databind.util.JSONPObject;

@RestController
@RequestMapping(value="/users")
@CrossOrigin
public class UserController {

	@Autowired
	private UserRepository repo;
	
	@PostMapping(value = "/register")
	public String register(@RequestBody  User user) {
		System.out.print(user);

		repo.save(user);
		return "StatusCode: 200";
	}
	
	/*@PostMapping(value ="/login")
	public String login(@RequestBody  String email, String password) {
		System.out.print(email);

		return "StatusCode: 200";
	}*/
	
	@PostMapping(value ="/login")
	public ResponseEntity<?> findByEmailAndPassword(@RequestBody  User us) {

		User user = repo.findByEmailAndPassword(us.getEmail(),us.getPassword());
	
		if(user==null) {
			return ResponseEntity.notFound().build();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fullname", user.getFirstName()+" "+user.getLastName()) ;;
		map.put("email", user.getEmail());;

        return new ResponseEntity<Object>(map,HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/list")
	public String listUsers() {
	    List<User> listUsers = repo.findAll();
	    System.out.print(listUsers);
	    return "users";
	}
	
	@GetMapping(value = "/all")
	public ResponseEntity<?> getAllPelicula( ) {

		List<User> pelis = repo.findAll();

		return ResponseEntity.ok(pelis);	
		}
	
	
}
