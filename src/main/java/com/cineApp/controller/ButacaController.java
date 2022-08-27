package com.cineApp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import com.cineApp.exception.ApiRequestException;
import com.cineApp.model.Butaca;
import com.cineApp.model.Sala;
import com.cineApp.repository.ButacaRepository;
import com.cineApp.repository.SalaRepository;
import com.cineApp.schema.ButacaSchema;

@RestController
@RequestMapping(value="/butacas")
@CrossOrigin
public class ButacaController {
	
	@Autowired
	private ButacaRepository repo;
	@Autowired
	private SalaRepository salaRepository;
	
	@PostMapping(value = "/")
	public ResponseEntity<?> addButaca(@RequestBody  ButacaSchema details) {	
		try {
		Optional<Sala> sala = salaRepository.findById(details.sala_id);
		
		if((!sala.isPresent())) {
			return ResponseEntity.notFound().build();
		}
		
		Butaca butaca = new Butaca();
		butaca.setSala(sala.get());
		butaca.setPosition_x(details.butaca.getPosition_x());
		butaca.setPosition_y(details.butaca.getPosition_y());
		butaca.setState(details.butaca.isState());

		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(butaca));
	}catch(Exception e){
		throw new ApiRequestException("Ha ocurrido un error", e);
	}

}
	
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?>  getByIdButaca(@PathVariable  Integer id) {
		try {
		Optional<Butaca> butaca = repo.findById(id);
		
		if(!butaca.isPresent()) {
			return ResponseEntity.notFound().build();
					}

		return ResponseEntity.ok(butaca);
	}catch(Exception e){
		throw new ApiRequestException("Ha ocurrido un error", e);
	}

}
	@GetMapping(value = "/byFuncion/{id}")
	public ResponseEntity<?>  getByIdFuncion(@PathVariable  Integer id) {
		try {
		List<Butaca> butacas = repo.findByFuncionId(id);

		return ResponseEntity.ok(butacas);
	}catch(Exception e){
		throw new ApiRequestException("Ha ocurrido un error", e);
	}

}
	

	@PutMapping(value="/")
	public ResponseEntity<?>  updateButaca(@RequestBody  Butaca details) {
		try {
		Optional<Butaca> butaca = repo.findById(details.getId());
		
		if(!butaca.isPresent()) {
			return ResponseEntity.notFound().build();
					}
		
		butaca.get().setState(details.isState());
		return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(butaca.get()));
	}catch(Exception e){
		throw new ApiRequestException("Ha ocurrido un error", e);
	}

}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<?>  deleteButaca(@PathVariable Integer id) {
		try {
		if(!repo.findById(id).isPresent()) {
			return ResponseEntity.notFound().build();
					}
		repo.deleteById(id);
		Map<String, String> map = new HashMap<String, String>();
		map.put("msg", "Butaca borrada correctamente");

        return new ResponseEntity<Object>(map,HttpStatus.OK);
	}catch(Exception e){
		throw new ApiRequestException("Ha ocurrido un error", e);
	}

}
	
}
