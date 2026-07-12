package dev.datnt.taskmanagement.controller;

import dev.datnt.taskmanagement.common.ApiResponse;
import dev.datnt.taskmanagement.dto.auth.LoginRequest;
import dev.datnt.taskmanagement.dto.auth.RefreshTokenRequest;
import dev.datnt.taskmanagement.dto.auth.RegisterRequest;
import dev.datnt.taskmanagement.dto.auth.RegisterResponse;
import dev.datnt.taskmanagement.dto.auth.TokenResponse;
import dev.datnt.taskmanagement.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

private final AuthService authService;

public AuthController(AuthService authService) {
	this.authService = authService;
}

@PostMapping("/register")
public ResponseEntity<ApiResponse<RegisterResponse>> register(@Valid @RequestBody RegisterRequest request) {
	RegisterResponse response = authService.register(request);
	return ResponseEntity.status(HttpStatus.CREATED)
			.body(ApiResponse.success("User registered successfully", response));
}

@PostMapping("/login")
public ResponseEntity<ApiResponse<TokenResponse>> login(@Valid @RequestBody LoginRequest request) {
	TokenResponse response = authService.login(request);
	return ResponseEntity.ok(ApiResponse.success("Login successful", response));
}

@PostMapping("/refresh")
public ResponseEntity<ApiResponse<TokenResponse>> refresh(@Valid @RequestBody RefreshTokenRequest request) {
	TokenResponse response = authService.refresh(request);
	return ResponseEntity.ok(ApiResponse.success("Token refreshed successfully", response));
}
}
