package com.tyss.layover.handler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyss.layover.exception.AccountNotApprovedException;
import com.tyss.layover.exception.EmailInterruptionException;
import com.tyss.layover.exception.GlobalException;
import com.tyss.layover.exception.IncorrectOtpException;
import com.tyss.layover.exception.UserNotFoundException;
import com.tyss.layover.response.LayoverResponseBody;

@RestControllerAdvice
public class LayoverExceptionHandler extends ResponseEntityExceptionHandler {

	

	@ExceptionHandler(value = { AccessDeniedException.class })
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException {
		HashMap<String, Object> error = new HashMap<>();
		error.put("error", true);
		error.put("message", "Access Denied");
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		new ObjectMapper().writeValue(response.getOutputStream(), error);
	}

	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<LayoverResponseBody> fileNotFoundException(FileNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(LayoverResponseBody.builder().isError(true).message(exception.getMessage()).build());
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<LayoverResponseBody> constraintViolationException(ConstraintViolationException exception, WebRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(LayoverResponseBody.builder().isError(true).message(exception.getMessage()+"hII").build());
	}
	
	@ExceptionHandler(GlobalException.class)
	public ResponseEntity<LayoverResponseBody> globalException(GlobalException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(LayoverResponseBody.builder().isError(true).message(exception.getMessage()).build());
	}
	@ExceptionHandler(EmailInterruptionException.class)
	public ResponseEntity<LayoverResponseBody> fileNotFoundException(EmailInterruptionException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(LayoverResponseBody.builder().isError(true).message(exception.getMessage()).build());
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<LayoverResponseBody> notFoundException(UserNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(LayoverResponseBody.builder().isError(true).message(exception.getMessage()).build());
	}

	@ExceptionHandler(IncorrectOtpException.class)
	public ResponseEntity<LayoverResponseBody> incorrectOtp(IncorrectOtpException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(LayoverResponseBody.builder().isError(true).message(exception.getMessage()).build());
	}

	@ExceptionHandler(AccountNotApprovedException.class)
	public ResponseEntity<LayoverResponseBody> accountNotApprovedException(AccountNotApprovedException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(LayoverResponseBody.builder().isError(true).message(exception.getMessage()).build());

	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<String> errorList = exception.getAllErrors().stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
		return new ResponseEntity<>(
				LayoverResponseBody.builder().isError(true).message("Validation Errors").data(errorList).build(),
				HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<LayoverResponseBody> internalServerError(RuntimeException exception, WebRequest request) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(LayoverResponseBody.builder().isError(true).message(exception.getMessage()).build());
	}
}
