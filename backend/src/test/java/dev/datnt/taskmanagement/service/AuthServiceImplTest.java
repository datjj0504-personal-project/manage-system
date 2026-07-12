package dev.datnt.taskmanagement.service;

import dev.datnt.taskmanagement.dto.auth.LoginRequest;
import dev.datnt.taskmanagement.dto.auth.RefreshTokenRequest;
import dev.datnt.taskmanagement.dto.auth.RegisterRequest;
import dev.datnt.taskmanagement.dto.auth.RegisterResponse;
import dev.datnt.taskmanagement.dto.auth.TokenResponse;
import dev.datnt.taskmanagement.entity.UserEntity;
import dev.datnt.taskmanagement.exception.AuthenticationFailureException;
import dev.datnt.taskmanagement.exception.BusinessException;
import dev.datnt.taskmanagement.mapper.UserMapper;
import dev.datnt.taskmanagement.repository.UserRepository;
import dev.datnt.taskmanagement.security.JwtTokenProvider;
import dev.datnt.taskmanagement.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

@Mock
private UserRepository userRepository;
@Mock
private UserMapper userMapper;
@Mock
private PasswordEncoder passwordEncoder;
@Mock
private AuthenticationManager authenticationManager;
@Mock
private JwtTokenProvider jwtTokenProvider;

@InjectMocks
private AuthServiceImpl authService;

private UserEntity user;

@BeforeEach
void setUp() {
	user = new UserEntity();
	user.setId(UUID.randomUUID());
	user.setEmail("john@example.com");
	user.setFullName("John Doe");
	user.setPasswordHash("encoded-password");
	user.setCreatedAt(Instant.now());
}

@Test
void register_whenEmailExists_shouldThrowBusinessException() {
	RegisterRequest request = new RegisterRequest("John Doe", "john@example.com", "password123");
	when(userRepository.existsByEmail("john@example.com")).thenReturn(true);

	assertThatThrownBy(() -> authService.register(request))
			.isInstanceOf(BusinessException.class)
			.hasMessage("Email already exists");
}

@Test
void register_whenValidRequest_shouldCreateUserAndReturnResponse() {
	RegisterRequest request = new RegisterRequest("John Doe", "john@example.com", "password123");
	RegisterResponse response = new RegisterResponse(user.getId(), user.getEmail(), user.getFullName(), user.getCreatedAt());

	when(userRepository.existsByEmail("john@example.com")).thenReturn(false);
	when(passwordEncoder.encode("password123")).thenReturn("encoded-password");
	when(userRepository.save(any(UserEntity.class))).thenReturn(user);
	when(userMapper.toRegisterResponse(user)).thenReturn(response);

	RegisterResponse result = authService.register(request);

	assertThat(result).isEqualTo(response);
	verify(userRepository).save(any(UserEntity.class));
}

@Test
void login_whenValidCredentials_shouldReturnTokens() {
	LoginRequest request = new LoginRequest("john@example.com", "password123");
	Authentication authentication = new UsernamePasswordAuthenticationToken("john@example.com", "password123");

	when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
	when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));
	when(jwtTokenProvider.generateAccessToken("john@example.com")).thenReturn("access-token");
	when(jwtTokenProvider.generateRefreshToken("john@example.com")).thenReturn("refresh-token");
	when(jwtTokenProvider.getAccessTokenExpirationSeconds()).thenReturn(900L);

	TokenResponse result = authService.login(request);

	assertThat(result.accessToken()).isEqualTo("access-token");
	assertThat(result.refreshToken()).isEqualTo("refresh-token");
	assertThat(result.tokenType()).isEqualTo("Bearer");
	assertThat(result.expiresIn()).isEqualTo(900L);
}

@Test
void refresh_whenTokenInvalid_shouldThrowAuthenticationFailureException() {
	RefreshTokenRequest request = new RefreshTokenRequest("invalid-token");
	when(jwtTokenProvider.validateToken("invalid-token")).thenReturn(false);

	assertThatThrownBy(() -> authService.refresh(request))
			.isInstanceOf(AuthenticationFailureException.class)
			.hasMessage("Invalid or expired refresh token");
}
}
