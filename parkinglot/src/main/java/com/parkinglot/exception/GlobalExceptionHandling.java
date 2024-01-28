package com.parkinglot.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


@RestControllerAdvice
public class GlobalExceptionHandling {
	
	@ExceptionHandler(NotFoundException.class)
	public ErrorResponse userHandlerException(NotFoundException exception,WebRequest webRequest) {
			
			ErrorResponse errorResponse =new ErrorResponse();
	       	
			errorResponse.setStatus(HttpStatus.BAD_REQUEST.toString());
	       	errorResponse.setMessage(exception.getMessage());
	       	errorResponse.setTimeStamp(System.currentTimeMillis());
	       
	       	return errorResponse;
	}
	
	
	@ExceptionHandler(AlreadyExistsException.class)
	public ErrorResponse userHandlerException(AlreadyExistsException exception,WebRequest webRequest ) {
			
			ErrorResponse errorResponse =new ErrorResponse();
	       
			errorResponse.setStatus(HttpStatus.BAD_REQUEST.toString());
	       	errorResponse.setMessage(exception.getMessage());
	       	errorResponse.setTimeStamp(System.currentTimeMillis());
	       
	       return errorResponse;
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
	    List<String> errors = ex.getBindingResult().getFieldErrors()
	            .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
	    return new ResponseEntity<>(getErrorsMap (errors), HttpStatus.BAD_REQUEST);
	}
	
	private Map<String, List<String>> getErrorsMap(List<String> errors) {
	    Map<String, List<String>> errorResponse = new HashMap<>();
	    errorResponse.put("errors", errors);
	    return errorResponse;
	}
	
}
