package dev.datnt.taskmanagement.dto.project;

import java.util.List;

public record ProjectListResponse(
		List<ProjectSummaryResponse> items,
		int page,
		int size,
		long totalElements,
		int totalPages
) {
}
