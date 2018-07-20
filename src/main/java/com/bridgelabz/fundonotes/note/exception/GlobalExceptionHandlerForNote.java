package com.bridgelabz.fundonotes.note.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bridgelabz.fundonotes.note.controller.NoteController;
import com.bridgelabz.fundonotes.note.model.Response;

@ControllerAdvice
public class GlobalExceptionHandlerForNote {

	public static final Logger logger = LoggerFactory.getLogger(NoteController.class);

	@ExceptionHandler(NoteCreationException.class)
	public ResponseEntity<Response> noteExceptionHandler(NoteCreationException e) {
		logger.error("note entry error");
		Response response = new Response();
		response.setMessage("note entry error, " + e.getMessage());
		response.setStatus(101);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoteNotFoundException.class)
	public ResponseEntity<Response> noteNotFoundExceptionHandler(NoteNotFoundException e) {
		logger.error("note not found error");
		Response response = new Response();
		response.setMessage("note not found exception, " + e.getMessage());
		response.setStatus(201);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Response> userNotFoundExceptionHandler(NoteNotFoundException e) {
		logger.error("note not found error");
		Response response = new Response();
		response.setMessage("note not found exception, " + e.getMessage());
		response.setStatus(301);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NullEntryException.class)
	public ResponseEntity<Response> nullEntryExceptionHandler(NullEntryException e) {
		logger.error("null value error");
		Response response = new Response();
		response.setMessage("null value exception, " + e.getMessage());
		response.setStatus(401);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UntrashedException.class)
	public ResponseEntity<Response> untrashedExceptionHandler(NullEntryException e) {
		logger.error("note untrashed error");
		Response response = new Response();
		response.setMessage("note untrashed exception, " + e.getMessage());
		response.setStatus(401);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	/*@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseDTO> controller(Exception e) {
		logger.error("other exceptions");
		ResponseDTO response=new ResponseDTO();
		response.setMessage("Some exceptions occured, "+e.getMessage());
		response.setStatus(-1);
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}*/
}
