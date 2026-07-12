package dev.datnt.taskmanagement.dto.project;

import jakarta.validation.constraints.Size;

public record UpdateProjectRequest(
		@Size(min = 3, max = 120, message = "name must be between 3 and 120 characters")
		String name,
		@Size(max = 2000, message = "description must be at most 2000 characters")
		String description
) {
}
