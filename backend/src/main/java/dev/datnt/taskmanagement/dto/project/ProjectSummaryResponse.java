package dev.datnt.taskmanagement.dto.project;

import java.time.Instant;
import java.util.UUID;

public record ProjectSummaryResponse(
		UUID id,
		String name,
		String description,
		UUID ownerId,
		Instant createdAt,
		Instant updatedAt
) {
}
