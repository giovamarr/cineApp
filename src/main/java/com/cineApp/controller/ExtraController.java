package com.cineApp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cineApp.schema.ContactSchema;
import com.cineApp.service.EmailSenderService;

@RestController
@RequestMapping(value="/extra")
@CrossOrigin
public class ExtraController {
	@Autowired
	private EmailSenderService emailSenderService;
	@PostMapping(value = "/contact")
	public ResponseEntity<?> sendContactMsg(@RequestBody ContactSchema details) {	
		emailSenderService.sendEmailContactMsg(details.name,details.email,details.subject,details.message);
		
	       Map<String, Object> map = new HashMap<String, Object>();
           map.put("message", "Mensaje de contacto enviado con exito");
		return ResponseEntity.status(HttpStatus.CREATED).body(map);
	} 

}
