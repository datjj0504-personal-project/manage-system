package dev.datnt.taskmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.datnt.taskmanagement.dto.auth.LoginRequest;
import dev.datnt.taskmanagement.dto.auth.RefreshTokenRequest;
import dev.datnt.taskmanagement.dto.auth.RegisterRequest;
import dev.datnt.taskmanagement.dto.auth.RegisterResponse;
import dev.datnt.taskmanagement.dto.auth.TokenResponse;
import dev.datnt.taskmanagement.exception.GlobalExceptionHandler;
import dev.datnt.taskmanagement.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthControllerApiTest {

@Mock
private AuthService authService;

private MockMvc mockMvc;
private ObjectMapper objectMapper;

@BeforeEach
void setUp() {
	AuthController controller = new AuthController(authService);
	this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
			.setControllerAdvice(new GlobalExceptionHandler())
			.build();
	this.objectMapper = new ObjectMapper();
}

@Test
void register_whenValidRequest_shouldReturnCreated() throws Exception {
	RegisterRequest request = new RegisterRequest("John Doe", "john@example.com", "password123");
	RegisterResponse response = new RegisterResponse(UUID.randomUUID(), "john@example.com", "John Doe", Instant.now());
	when(authService.register(request)).thenReturn(response);

	mockMvc.perform(post("/api/v1/auth/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.success").value(true))
			.andExpect(jsonPath("$.data.email").value("john@example.com"));
}

@Test
void login_whenValidRequest_shouldReturnTokens() throws Exception {
	LoginRequest request = new LoginRequest("john@example.com", "password123");
	TokenResponse response = new TokenResponse("access", "refresh", "Bearer", 900L);
	when(authService.login(request)).thenReturn(response);

	mockMvc.perform(post("/api/v1/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value(true))
			.andExpect(jsonPath("$.data.accessToken").value("access"));
}

@Test
void refresh_whenValidRequest_shouldReturnTokens() throws Exception {
	RefreshTokenRequest request = new RefreshTokenRequest("refresh-token");
	TokenResponse response = new TokenResponse("new-access", "new-refresh", "Bearer", 900L);
	when(authService.refresh(request)).thenReturn(response);

	mockMvc.perform(post("/api/v1/auth/refresh")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value(true))
			.andExpect(jsonPath("$.data.refreshToken").value("new-refresh"));
}

@Test
void register_whenValidationFails_shouldReturnBadRequest() throws Exception {
	RegisterRequest request = new RegisterRequest("", "invalid-email", "123");

	mockMvc.perform(post("/api/v1/auth/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.success").value(false));
}
}
