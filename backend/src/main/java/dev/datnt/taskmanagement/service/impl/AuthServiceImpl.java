package dev.datnt.taskmanagement.service.impl;

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
import dev.datnt.taskmanagement.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
public class AuthServiceImpl implements AuthService {

private static final String TOKEN_TYPE = "Bearer";

private final UserRepository userRepository;
private final UserMapper userMapper;
private final PasswordEncoder passwordEncoder;
private final AuthenticationManager authenticationManager;
private final JwtTokenProvider jwtTokenProvider;

public AuthServiceImpl(
		UserRepository userRepository,
		UserMapper userMapper,
		PasswordEncoder passwordEncoder,
		AuthenticationManager authenticationManager,
		JwtTokenProvider jwtTokenProvider
) {
	this.userRepository = userRepository;
	this.userMapper = userMapper;
	this.passwordEncoder = passwordEncoder;
	this.authenticationManager = authenticationManager;
	this.jwtTokenProvider = jwtTokenProvider;
}

@Override
@Transactional
public RegisterResponse register(RegisterRequest request) {
	String email = normalizeEmail(request.email());
	if (userRepository.existsByEmail(email)) {
		throw new BusinessException("Email already exists");
	}

	UserEntity user = new UserEntity();
	user.setEmail(email);
	user.setFullName(request.fullName().trim());
	user.setPasswordHash(passwordEncoder.encode(request.password()));

	UserEntity savedUser = userRepository.save(user);
	return userMapper.toRegisterResponse(savedUser);
}

@Override
@Transactional(readOnly = true)
public TokenResponse login(LoginRequest request) {
	String email = normalizeEmail(request.email());
	authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, request.password()));

	UserEntity user = userRepository.findByEmail(email)
			.orElseThrow(() -> new AuthenticationFailureException("Invalid credentials"));

	return issueTokens(user.getEmail());
}

@Override
@Transactional(readOnly = true)
public TokenResponse refresh(RefreshTokenRequest request) {
	String refreshToken = request.refreshToken();
	if (!jwtTokenProvider.validateToken(refreshToken) || !jwtTokenProvider.isRefreshToken(refreshToken)) {
		throw new AuthenticationFailureException("Invalid or expired refresh token");
	}

	String email = normalizeEmail(jwtTokenProvider.extractSubject(refreshToken));
	if (userRepository.findByEmail(email).isEmpty()) {
		throw new AuthenticationFailureException("Invalid or expired refresh token");
	}

	return issueTokens(email);
}

private TokenResponse issueTokens(String email) {
	String accessToken = jwtTokenProvider.generateAccessToken(email);
	String refreshToken = jwtTokenProvider.generateRefreshToken(email);
	return new TokenResponse(accessToken, refreshToken, TOKEN_TYPE, jwtTokenProvider.getAccessTokenExpirationSeconds());
}

private String normalizeEmail(String email) {
	return email.trim().toLowerCase(Locale.ROOT);
}
}
