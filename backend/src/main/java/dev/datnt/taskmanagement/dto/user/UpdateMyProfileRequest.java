package dev.datnt.taskmanagement.dto.user;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateMyProfileRequest(
		@Size(min = 2, max = 100, message = "fullName must be between 2 and 100 characters")
		String fullName,
		@Pattern(regexp = "^(https?://).+", message = "avatarUrl must be a valid URL")
		String avatarUrl
) {
}
