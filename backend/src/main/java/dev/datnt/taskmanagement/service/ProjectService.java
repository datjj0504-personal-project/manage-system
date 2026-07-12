package dev.datnt.taskmanagement.service;

import dev.datnt.taskmanagement.dto.project.CreateProjectRequest;
import dev.datnt.taskmanagement.dto.project.ProjectListResponse;
import dev.datnt.taskmanagement.dto.project.ProjectResponse;
import dev.datnt.taskmanagement.dto.project.UpdateProjectRequest;

import java.util.UUID;

public interface ProjectService {

	ProjectResponse createProject(CreateProjectRequest request);

	ProjectResponse getProjectDetail(UUID projectId);

	ProjectListResponse listProjects(int page, int size, String sort, String keyword);

	ProjectResponse updateProject(UUID projectId, UpdateProjectRequest request);

	void deleteProject(UUID projectId);
}
