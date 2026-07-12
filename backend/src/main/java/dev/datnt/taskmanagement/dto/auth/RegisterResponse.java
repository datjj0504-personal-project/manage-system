package dev.datnt.taskmanagement.dto.auth;

import java.time.Instant;
import java.util.UUID;

public record RegisterResponse(
		UUID id,
		String email,
		String fullName,
		Instant createdAt
) {
}
