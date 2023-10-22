package me.dio.controller.exception;

import java.util.NoSuchElementException;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ch.qos.logback.classic.Logger;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	//private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	/*
	 * é equivalente, desde que haja uma importação adequada da classe Logger da biblioteca SLF4J:
	 * -- private final org.slf4j.Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	 * --private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class); (exemplo da aula)
	 */
	
	/*
	 * basicamente a mesma coisa que as versões anteriores. No entanto, a expressão (Logger) é uma 
	 * conversão de tipo (casting) que força o resultado de LoggerFactory.getLogger(GlobalExceptionHandler.class)
	 * a ser interpretado como um objeto do tipo Logger. 
	 */
	private final Logger logger = (Logger) LoggerFactory.getLogger(GlobalExceptionHandler.class);


	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleBusinessException(IllegalArgumentException businessException){
		return new ResponseEntity<>(businessException.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<String> handleNotFoundException(NoSuchElementException notFoundException){
		return new ResponseEntity<>("Resource ID not found", HttpStatus.NOT_FOUND);
		//return new ResponseEntity<>(body: "Resource ID not found", HttpStatus.NOTFOUND);
	}
	
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<String> handleUnexpectedException(Throwable  unexpectedException){
		var message = "Unexpected server error, see the logs.";
		logger.error("", unexpectedException);
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		//return new ResponseEntity<>(body "Unexpected server error, see the logs.", HttpStatus.INTERNAL_SERVER_ERROR);
	}


}
