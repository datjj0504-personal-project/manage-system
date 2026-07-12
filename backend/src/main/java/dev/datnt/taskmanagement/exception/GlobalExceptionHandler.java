package dev.datnt.taskmanagement.exception;

import dev.datnt.taskmanagement.common.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
	String message = exception.getBindingResult()
			.getFieldErrors()
			.stream()
			.map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
			.collect(Collectors.joining(", "));

	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(ApiResponse.failure(message.isBlank() ? "Validation failed" : message));
}

@ExceptionHandler(ConstraintViolationException.class)
public ResponseEntity<ApiResponse<Void>> handleConstraintViolation(ConstraintViolationException exception) {
	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(ApiResponse.failure("Validation failed"));
}

@ExceptionHandler(ResourceNotFoundException.class)
public ResponseEntity<ApiResponse<Void>> handleResourceNotFound(ResourceNotFoundException exception) {
	return ResponseEntity.status(HttpStatus.NOT_FOUND)
			.body(ApiResponse.failure(exception.getMessage()));
}

@ExceptionHandler(BusinessException.class)
public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException exception) {
	return ResponseEntity.status(HttpStatus.CONFLICT)
			.body(ApiResponse.failure(exception.getMessage()));
}

@ExceptionHandler(AuthorizationException.class)
public ResponseEntity<ApiResponse<Void>> handleAuthorizationException(AuthorizationException exception) {
	return ResponseEntity.status(HttpStatus.FORBIDDEN)
			.body(ApiResponse.failure(exception.getMessage()));
}

@ExceptionHandler({AuthenticationFailureException.class, AuthenticationException.class, BadCredentialsException.class})
public ResponseEntity<ApiResponse<Void>> handleAuthenticationException(Exception exception) {
	return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
			.body(ApiResponse.failure("Authentication failed"));
}

@ExceptionHandler(Exception.class)
public ResponseEntity<ApiResponse<Void>> handleException(Exception exception) {
	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(ApiResponse.failure("Unexpected error occurred"));
}
}
