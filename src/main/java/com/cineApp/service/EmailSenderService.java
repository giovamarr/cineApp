package com.cineApp.service;

import java.lang.String;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cineApp.model.Reserva;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class EmailSenderService {
	@Autowired
	private JavaMailSender mailSender;
	public void sendEmailNewReservation(String toEmail,Reserva reserva) {
		SimpleMailMessage message =new SimpleMailMessage();
		message.setFrom("cinejava99@gmail.com");		
		message.setTo(toEmail);
		
		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String date = reserva.getFuncion().getFechaFuncion().format(formatters);


		String msg="Se ha realizado con exito la compra para la función del dia "+date+ " a las "+reserva.getFuncion().getHoraFuncion()+
				" de la película "+reserva.getFuncion().getPelicula().getName()+".\n"+"La butaca seleccionada es la correspondiente a la fila "+reserva.getButaca().getPosition_y()+" columna "+reserva.getButaca().getPosition_x()+
				"\n"+"El código de la compra es "+reserva.getCode()+". Dicho codigo puede ser utilizado en caso de querer cancelar la compra. \n \n"+"Muchas Gracias por la Compra \r"+"Cine";
		message.setSubject("Compra de Boletos");
		message.setText(msg);
		
		mailSender.send(message);
		
		
	}
	
	public void sendEmailContactMsg(String name,String email,String subject,String msg) {
		SimpleMailMessage message =new SimpleMailMessage();
		message.setFrom("cinejava99@gmail.com");		
		message.setTo("cinejava99@gmail.com");		
		String body="Nombre: "+name+"\n"+"Correo Electrónico: "+email+"\n"+"Asunto del Mensaje: "+subject+"\n"+"Mensaje: "+msg+"\n";
		message.setSubject("Mensaje de Contacto");
		message.setText(body);
		
		mailSender.send(message);
		
		
	}
}
