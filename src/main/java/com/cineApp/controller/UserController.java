package com.cineApp.controller;

import java.util.List;

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

import com.cineApp.exception.ApiRequestException;
import com.cineApp.model.User;
import com.cineApp.repository.UserRepository;

@RestController
@RequestMapping(value="/users")
@CrossOrigin
public class UserController {

	@Autowired
	private UserRepository repo;
	
	@PostMapping(value = "/register")
	public String register(@RequestBody  User user) {
		try {
			System.out.print(user);

			repo.save(user);
			return "StatusCode: 200";
		}
		catch(Exception e){
			throw new ApiRequestException("Ha ocurrido un error", e);
		}
	
	}
	
	/*@PostMapping(value ="/login")
	public String login(@RequestBody  String email, String password) {
		System.out.print(email);

		return "StatusCode: 200";
	}*/
	
	@PostMapping(value ="/login")
	public ResponseEntity<?> findByEmailAndPassword(@RequestBody  User us) {
		try {
		User user = repo.findByEmailAndPassword(us.getEmail(),us.getPassword());
	
		if(user==null) {
			return ResponseEntity.notFound().build();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fullname", user.getFirstName()+" "+user.getLastName()) ;;
		map.put("email", user.getEmail());;
		System.out.print(map);
        return new ResponseEntity<Object>(map,HttpStatus.OK);
	}catch(Exception e){
		throw new ApiRequestException("Ha ocurrido un error", e);
	}

}
	
	
	@GetMapping(value = "/list")
	public List<User> listUsers() {
		try {
			List<User> listUsers = repo.findAll();
			return listUsers;
		}catch(Exception e){
		throw new ApiRequestException("Ha ocurrido un error", e);
	}
	}
	
	@GetMapping(value = "/all")
	public ResponseEntity<?> getAllPelicula( ) {
		try {
		List<User> pelis = repo.findAll();

		return ResponseEntity.ok(pelis);	
	}catch(Exception e){
		throw new ApiRequestException("Ha ocurrido un error", e);
	}
		}
	
	
}
