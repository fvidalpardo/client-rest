package com.globallogic.clientrest.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class EntityExceptionHandler extends ResponseEntityExceptionHandler{
	@ExceptionHandler(value = { UserServiceException.class })
	public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {
		ErrorMessage errorObj = new ErrorMessage(new Date(), 500,
				ex.getMessage());
	        return new ResponseEntity<>(
	        		errorObj, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
}
