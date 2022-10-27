package com.rodrigo.infra.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.rodrigo.infra.exceptions.ExceptionResponse;
import com.rodrigo.infra.exceptions.RequiredObjectIsNullException;
import com.rodrigo.infra.exceptions.ResourceNotFoundException;

@RestControllerAdvice
public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> handleResourceNotFoundExceptions(Exception ex, WebRequest request){
		
		 ExceptionResponse exceptionResponse = new ExceptionResponse(
				 new Date(),
				 ex.getMessage(),
				 request.getDescription(false)
				 );
		 
		 return new ResponseEntity<>(exceptionResponse,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(RequiredObjectIsNullException.class)
	public final ResponseEntity<ExceptionResponse> handleBadRequestException(Exception ex, WebRequest request){
		
		 ExceptionResponse exceptionResponse = new ExceptionResponse(
				 new Date(),
				 ex.getMessage(),
				 request.getDescription(false)
				 );
		 
		 return new ResponseEntity<>(exceptionResponse,HttpStatus.BAD_REQUEST);
	}

}
