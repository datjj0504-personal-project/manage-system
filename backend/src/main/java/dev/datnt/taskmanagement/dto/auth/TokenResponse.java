package dev.datnt.taskmanagement.dto.auth;

public record TokenResponse(
		String accessToken,
		String refreshToken,
		String tokenType,
		long expiresIn
) {
}
