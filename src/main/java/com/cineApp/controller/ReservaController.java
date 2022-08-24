package com.cineApp.controller;
import java.util.UUID;
import java.time.LocalDateTime;
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

import com.cineApp.model.Funcion;
import com.cineApp.model.Reserva;
import com.cineApp.model.User;
import com.cineApp.model.Butaca;
import com.cineApp.repository.FuncionRepository;
import com.cineApp.repository.ReservaRepository;
import com.cineApp.repository.TarjetaRepository;
import com.cineApp.repository.UserRepository;
import com.cineApp.repository.ButacaRepository;
import com.cineApp.schema.CancelReservaSchema;
import com.cineApp.schema.ListaVentaPeliculas;
import com.cineApp.schema.ReservaSchema;
import com.cineApp.service.EmailSenderService;
import com.cineApp.service.PaymentService;

@RestController
@RequestMapping(value="/reservas")
@CrossOrigin
public class ReservaController {
	
	@Autowired
	private ReservaRepository reservaRepository;
	@Autowired
	private FuncionRepository funcionRepository;
	@Autowired
	private ButacaRepository butacaRepository;
	@Autowired
	private EmailSenderService emailSenderService;
	@Autowired
	private PaymentService paymentService;
	
	
	/** Add   **/
	@PostMapping(value = "/")
	public ResponseEntity<?> addReserva(@RequestBody ReservaSchema details) {		
		
		Optional<Funcion> funcion = funcionRepository.findById(details.funcion_id);
		Optional<Butaca> butaca = butacaRepository.findById(details.butaca_id);
		if(!paymentService.searchCreditCard(details.number, details.cvc, details.name, details.expiry)) {
			Map<String,Object> msg= new HashMap<String, Object>();
			msg.put("paymentMsg", "Datos de pago no encontrados.");
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(msg);
		}

		if(!funcion.isPresent()||!butaca.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		
		Reserva reserva = new Reserva();
		reserva.setCode(UUID.randomUUID().toString().replaceAll("-", ""));
		reserva.setFuncion(funcion.get());
		reserva.setButaca(butaca.get());
		reserva.setEmail(details.email);
		reserva.setFechaCompra(java.time.LocalDate.now());
		
		emailSenderService.sendEmailNewReservation(details.email,reserva);

		return ResponseEntity.status(HttpStatus.CREATED).body(reservaRepository.save(reserva));
	} 
	

	
	/** Get One   **/
	@GetMapping(value = "/{id}")
	public ResponseEntity<?>  getByIdReserva(@PathVariable  Integer id) {

		Optional<Reserva> reserva = reservaRepository.findById(id);
		
		if(!reserva.isPresent()) {
			return ResponseEntity.notFound().build();
					}

		return ResponseEntity.ok(reserva);
	}
	
	
	/** Get All   **/
	@GetMapping(value = "/")
	public ResponseEntity<?> getAllReservas( ) {

		List<Reserva> reserva = reservaRepository.findAll();

		return ResponseEntity.ok(reserva);	
		}
	
	
	/** Get All by Funcion **/
	@GetMapping(value = "/all/{id}")
	public ResponseEntity<?> getAllReservasbyFunction(@PathVariable  Integer id ) {

		List<Object[]> reservas = reservaRepository.getallbyFunction(id);

		return ResponseEntity.ok(reservas);	
		}
	
	/** Update   **/
	@PutMapping(value="/{id}")
	public ResponseEntity<?>  updateFuncion(@RequestBody ReservaSchema details,@PathVariable Integer id) {

		Optional<Reserva> reserva = reservaRepository.findById(id);
		Optional<Funcion> funcion = funcionRepository.findById(details.funcion_id);

		if((!reserva.isPresent()) ||  (!funcion.isPresent())) {
			return ResponseEntity.notFound().build();
		}
		
		reserva.get().setFuncion(funcion.get());
		reserva.get().setFechaCompra(java.time.LocalDate.now());
		return ResponseEntity.status(HttpStatus.CREATED).body(reservaRepository.save(reserva.get()));
	}
	
	
	/** Delete   **/
	@DeleteMapping(value = "/")
	public ResponseEntity<?> deleteReserva(@RequestBody CancelReservaSchema details) {		
		
		Optional<Reserva> reserva = reservaRepository.getReservabyCodeandEmail(details.code,details.email);
		if(!reserva.isPresent()) {
			Map<String,Object> msg= new HashMap<String, Object>();
			msg.put("message", "Entrada no encontrada");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
		}		
		reservaRepository.deleteReserva(details.code,details.email);		
		Map<String,Object> msg= new HashMap<String, Object>();
		msg.put("message", "Entrada borrada con exito");
		return ResponseEntity.status(HttpStatus.OK).body(msg);
	} 
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<?>  deleteReservabyid(@PathVariable Integer id) {
		Optional<Reserva> reserva=reservaRepository.findById(id);
		if(!reserva.isPresent()) {
			return ResponseEntity.notFound().build();
					}
		reservaRepository.deletebyID(id);
		emailSenderService.sendCancelReserva(reserva.get());
		Map<String,Object> msg= new HashMap<String, Object>();
		msg.put("message", "Entrada borrada con exito");
		return ResponseEntity.status(HttpStatus.OK).body(msg);
	}
	
	/** Listado ventas por pelicula   **/
	@GetMapping(value = "/listados/pelicula")
	public ResponseEntity<?>  getListadoVentasPelicula() {
		List<Object[]> listado = reservaRepository.listadoVentasPelicula();
		return ResponseEntity.ok(listado);
	}
	
	/** Listado ventas por pelicula   **/
	@GetMapping(value = "/listados/funcion")
	public ResponseEntity<?>  getListadoVentasFuncion() {
		List<Object[]> listado = reservaRepository.listadoVentasFuncion();
		return ResponseEntity.ok(listado);
	}
	
	
}