package br.com.allessandro.inventario.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.allessandro.inventario.services.exceptions.EntityNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(EntityNotFoundException e, HttpServletRequest http){
		StandardError error = new StandardError();
		
		Integer status = HttpStatus.NOT_FOUND.value();
		
		error.setTimestamp(Instant.now());
		error.setMessage(null);
		error.setStatus(status);
		error.setError("Resource not found");
		error.setMessage(e.getMessage());
		error.setPath(http.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
}
