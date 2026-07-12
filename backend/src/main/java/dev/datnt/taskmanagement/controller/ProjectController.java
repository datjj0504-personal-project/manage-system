package dev.datnt.taskmanagement.controller;

import dev.datnt.taskmanagement.common.ApiResponse;
import dev.datnt.taskmanagement.dto.project.CreateProjectRequest;
import dev.datnt.taskmanagement.dto.project.ProjectListResponse;
import dev.datnt.taskmanagement.dto.project.ProjectResponse;
import dev.datnt.taskmanagement.dto.project.UpdateProjectRequest;
import dev.datnt.taskmanagement.service.ProjectService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

	private final ProjectService projectService;

	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}

	@PostMapping
	public ResponseEntity<ApiResponse<ProjectResponse>> createProject(@Valid @RequestBody CreateProjectRequest request) {
		ProjectResponse response = projectService.createProject(request);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success("Project created successfully", response));
	}

	@GetMapping("/{projectId}")
	public ResponseEntity<ApiResponse<ProjectResponse>> getProjectDetail(@PathVariable UUID projectId) {
		ProjectResponse response = projectService.getProjectDetail(projectId);
		return ResponseEntity.ok(ApiResponse.success("Project retrieved successfully", response));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<ProjectListResponse>> listProjects(
			@RequestParam(defaultValue = "0") @Min(value = 0, message = "page must be >= 0") int page,
			@RequestParam(defaultValue = "20") @Min(value = 1, message = "size must be >= 1") @Max(value = 100, message = "size must be <= 100") int size,
			@RequestParam(defaultValue = "createdAt,desc") String sort,
			@RequestParam(required = false) String q
	) {
		ProjectListResponse response = projectService.listProjects(page, size, sort, q);
		return ResponseEntity.ok(ApiResponse.success("Projects retrieved successfully", response));
	}

	@PatchMapping("/{projectId}")
	public ResponseEntity<ApiResponse<ProjectResponse>> updateProject(
			@PathVariable UUID projectId,
			@Valid @RequestBody UpdateProjectRequest request
	) {
		ProjectResponse response = projectService.updateProject(projectId, request);
		return ResponseEntity.ok(ApiResponse.success("Project updated successfully", response));
	}

	@DeleteMapping("/{projectId}")
	public ResponseEntity<Void> deleteProject(@PathVariable UUID projectId) {
		projectService.deleteProject(projectId);
		return ResponseEntity.noContent().build();
	}
}
