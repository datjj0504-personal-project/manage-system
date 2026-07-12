package dev.datnt.taskmanagement.dto.user;

import java.time.Instant;
import java.util.UUID;

public record UserProfileResponse(
		UUID id,
		String email,
		String fullName,
		String avatarUrl,
		Instant createdAt,
		Instant updatedAt
) {
}
