package com.cineApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cineApp.model.User;
import com.cineApp.repository.UserRepository;

@RestController
@RequestMapping(value="/users")
public class UserController {

	@Autowired
	private UserRepository repo;
	
	@PostMapping(value = "/register")
	public String register(@RequestBody  User user) {
		System.out.print(user);

		repo.save(user);
		return "StatusCode: 200";
	}
	
	@PostMapping(value ="/login")
	public String login(@RequestBody  String email, String password) {
		System.out.print(email);

		return "StatusCode: 200";
	}
	
	@GetMapping(value = "/list")
	public String listUsers() {
	    List<User> listUsers = repo.findAll();
	    System.out.print(listUsers);
	    return "users";
	}
	
	
}
