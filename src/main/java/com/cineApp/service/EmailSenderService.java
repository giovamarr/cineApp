package com.cineApp.service;

import java.lang.String;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cineApp.model.Reserva;
import com.cineApp.repository.ReservaRepository;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class EmailSenderService {
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private ReservaRepository reservaRepository;
	
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
	public void sendCancelReserva(Reserva reserva) {
		SimpleMailMessage message =new SimpleMailMessage();
		message.setFrom("cinejava99@gmail.com");		
		message.setTo(reserva.getEmail());		
		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String date = reserva.getFuncion().getFechaFuncion().format(formatters);
		String msg="Se ha cancelado la reserva para la función del dia "+date+ " a las "+reserva.getFuncion().getHoraFuncion()+
				" de la película "+reserva.getFuncion().getPelicula().getName()+".\n"
				+"Saludos";
		message.setText(msg);		
		mailSender.send(message);		
		
	}
	
	public void sendCancelFunction(Integer id) {
		List<Reserva> reservas =reservaRepository.getallReservabyFunction(id);
		
		for(Reserva reserva:reservas) {
			SimpleMailMessage message =new SimpleMailMessage();
			message.setFrom("cinejava99@gmail.com");		
			message.setTo(reserva.getEmail());		
			DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			String date = reserva.getFuncion().getFechaFuncion().format(formatters);
			String msg="Disculpe las molestias, pero se ha cancelado la función del dia "+date+ " a las "+reserva.getFuncion().getHoraFuncion()+
					" de la película "+reserva.getFuncion().getPelicula().getName()+".\n"
					+"Disculpe las molestias";
			message.setText(msg);		
			mailSender.send(message);				
		}	
		
		
	}
	
	public void sendDataModifiedFunction(Integer id) {
		
		List<Reserva> reservas =reservaRepository.getallReservabyFunction(id);
		
		for(Reserva reserva:reservas) {
			SimpleMailMessage message =new SimpleMailMessage();
			message.setFrom("cinejava99@gmail.com");			
			message.setTo(reserva.getEmail());		
			DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			String date = reserva.getFuncion().getFechaFuncion().format(formatters);
			String msg="Disculpe las molestias, pero parece que se ha modificado los datos la función a la cual ha comprado tickets.\n"+"Los datos son los siguientes:\n"+
			"Pelicula: "+reserva.getFuncion().getPelicula().getName()+"\n"+
			"Fecha: "+date+ " a las "+reserva.getFuncion().getHoraFuncion()+"\n"+
			"Sala: "+reserva.getFuncion().getSala().getName()+"\n"+
			"Butaca: "+"Fila "+reserva.getButaca().getPosition_y()+" Columna "+reserva.getButaca().getPosition_x()+"\n"
			+"Disculpe las molestias, si quiere cancelar la reserva ingrese a la página con el código de la compra.";
			message.setText(msg);		
			mailSender.send(message);					
		}		
		
	}
	
}
