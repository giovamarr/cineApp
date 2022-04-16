package com.cineApp.controller;

import java.util.Optional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cineApp.model.Pelicula;
import com.cineApp.repository.PeliculaRepository;




@RestController
@RequestMapping(value="/peliculas")
@CrossOrigin
public class PeliculaController {
	
	@Autowired
	private PeliculaRepository repo;
	
	/** Add   **/
	@PostMapping(value = "/")
	public ResponseEntity<?> addPelicula(@RequestBody  Pelicula pel) {		
		Optional<Pelicula> pelicula = repo.findByName(pel.getName());
		if(pelicula.isPresent()) {
			return ResponseEntity.status(409).body("Ya existe una pelicula con ese nombre");
			}
		return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(pel));
	}
	
	/** Get One   **/
	@GetMapping(value = "/{id}")
	public ResponseEntity<?>  getByIdPelicula(@PathVariable  Integer id) {

		Optional<Pelicula> pel = repo.findById(id);
		
		if(!pel.isPresent()) {
			return ResponseEntity.notFound().build();
					}

		return ResponseEntity.ok(pel);
	}
	
	/** Get One by name  **/
	@GetMapping(value = "/name/{name}")
	public ResponseEntity<?>  getByNamePelicula(@PathVariable  String name) {

		Optional<Pelicula> pel = repo.findByName(name);
		if(!pel.isPresent()) {
			return ResponseEntity.notFound().build();
					}

		return ResponseEntity.ok(pel);
	}
	
	/** Get All   **/
	@GetMapping(value = "/")
	public ResponseEntity<?> getAllPelicula( ) {

		List<Pelicula> pelis = repo.findAll();

		return ResponseEntity.ok(pelis);	
		}
	
	/** Get All   **/
	@GetMapping(value = "/all/{state}")
	public ResponseEntity<?> getAllPelicula(@PathVariable  String state) {

		List<Pelicula> pelis = repo.findAllByState(state);

		return ResponseEntity.ok(pelis);	
		}
	/** Update   **/
	@PutMapping(value="/{id}")
	public ResponseEntity<?>  updatePelicula(@RequestBody  Pelicula peliDetails,@PathVariable Integer id) {

		Optional<Pelicula> peli = repo.findById(id);
		
		if(!peli.isPresent()) {
			return ResponseEntity.notFound().build();
					}
		peli.get().setName(peliDetails.getName());
		peli.get().setDescription(peliDetails.getDescription());
		peli.get().setDuration(peliDetails.getDuration());
		peli.get().setPoster(peliDetails.getPoster());
		peli.get().setState(peliDetails.getState());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(peli.get()));
	}
	
	/** Delete   **/
	@DeleteMapping(value="/{id}")
	public ResponseEntity<?>  deletePelicula(@PathVariable Integer id) {
		Optional<Pelicula> peli = repo.findById(id);
		
		if(!peli.isPresent()) {
			return ResponseEntity.notFound().build();
					}
		repo.deleteById(id);
				
		Map<String, String> map = new HashMap<String, String>();
		map.put("msg", "borrado Correctamente");

        return new ResponseEntity<Object>(map,HttpStatus.OK);
	}

}
