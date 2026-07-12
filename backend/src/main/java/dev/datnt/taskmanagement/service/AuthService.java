package dev.datnt.taskmanagement.service;

import dev.datnt.taskmanagement.dto.auth.LoginRequest;
import dev.datnt.taskmanagement.dto.auth.RefreshTokenRequest;
import dev.datnt.taskmanagement.dto.auth.RegisterRequest;
import dev.datnt.taskmanagement.dto.auth.RegisterResponse;
import dev.datnt.taskmanagement.dto.auth.TokenResponse;

public interface AuthService {

RegisterResponse register(RegisterRequest request);

TokenResponse login(LoginRequest request);

TokenResponse refresh(RefreshTokenRequest request);
}
