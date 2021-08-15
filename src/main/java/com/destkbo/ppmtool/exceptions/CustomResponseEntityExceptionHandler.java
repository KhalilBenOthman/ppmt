package com.destkbo.ppmtool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler
	public final ResponseEntity<Object> handelProjectIdException(ProjectIdException ex,WebRequest request){
		ProjectIdExceptionResponse exceptionresponse = new ProjectIdExceptionResponse(ex.getMessage());
		return new ResponseEntity(exceptionresponse,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public final ResponseEntity<Object> handelProjectNotFoundException(ProjectNotFoundException ex,WebRequest request){
		ProjectNotFoundExceptionResponse exceptionresponse = new ProjectNotFoundExceptionResponse(ex.getMessage());
		return new ResponseEntity(exceptionresponse,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public final ResponseEntity<Object> handelUsernameAlreadyExistException(UsernameAlreadyExistsException ex,WebRequest request){
		UsernameAlreadyExistsResponse exceptionresponse = new UsernameAlreadyExistsResponse(ex.getMessage());
		return new ResponseEntity(exceptionresponse,HttpStatus.BAD_REQUEST);
	}
	
}
