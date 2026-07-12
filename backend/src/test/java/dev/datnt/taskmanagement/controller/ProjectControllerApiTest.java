package dev.datnt.taskmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.datnt.taskmanagement.dto.project.CreateProjectRequest;
import dev.datnt.taskmanagement.dto.project.ProjectListResponse;
import dev.datnt.taskmanagement.dto.project.ProjectResponse;
import dev.datnt.taskmanagement.dto.project.ProjectSummaryResponse;
import dev.datnt.taskmanagement.dto.project.UpdateProjectRequest;
import dev.datnt.taskmanagement.exception.GlobalExceptionHandler;
import dev.datnt.taskmanagement.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ProjectControllerApiTest {

	@Mock
	private ProjectService projectService;

	private MockMvc mockMvc;
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		ProjectController controller = new ProjectController(projectService);
		this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setControllerAdvice(new GlobalExceptionHandler())
				.build();
		this.objectMapper = new ObjectMapper();
	}

	@Test
	void createProject_whenValidRequest_shouldReturnCreated() throws Exception {
		UUID projectId = UUID.randomUUID();
		ProjectResponse response = new ProjectResponse(projectId, "Project A", "Desc", UUID.randomUUID(), Instant.now(), Instant.now());
		CreateProjectRequest request = new CreateProjectRequest("Project A", "Desc");
		when(projectService.createProject(request)).thenReturn(response);

		mockMvc.perform(post("/api/v1/projects")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.data.name").value("Project A"));
	}

	@Test
	void getProjectDetail_whenExists_shouldReturnOk() throws Exception {
		UUID projectId = UUID.randomUUID();
		ProjectResponse response = new ProjectResponse(projectId, "Project A", null, UUID.randomUUID(), Instant.now(), Instant.now());
		when(projectService.getProjectDetail(projectId)).thenReturn(response);

		mockMvc.perform(get("/api/v1/projects/{projectId}", projectId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.data.id").value(projectId.toString()));
	}

	@Test
	void listProjects_shouldReturnPagedData() throws Exception {
		ProjectSummaryResponse item = new ProjectSummaryResponse(UUID.randomUUID(), "Project A", null, UUID.randomUUID(), Instant.now(), Instant.now());
		ProjectListResponse response = new ProjectListResponse(List.of(item), 0, 20, 1, 1);
		when(projectService.listProjects(eq(0), eq(20), eq("createdAt,desc"), eq(null))).thenReturn(response);

		mockMvc.perform(get("/api/v1/projects"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.data.totalElements").value(1));
	}

	@Test
	void updateProject_whenValidRequest_shouldReturnOk() throws Exception {
		UUID projectId = UUID.randomUUID();
		UpdateProjectRequest request = new UpdateProjectRequest("Updated", "Updated Desc");
		ProjectResponse response = new ProjectResponse(projectId, "Updated", "Updated Desc", UUID.randomUUID(), Instant.now(), Instant.now());
		when(projectService.updateProject(projectId, request)).thenReturn(response);

		mockMvc.perform(patch("/api/v1/projects/{projectId}", projectId)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.data.name").value("Updated"));
	}

	@Test
	void deleteProject_whenValidRequest_shouldReturnNoContent() throws Exception {
		UUID projectId = UUID.randomUUID();
		mockMvc.perform(delete("/api/v1/projects/{projectId}", projectId))
				.andExpect(status().isNoContent());
	}

	@Test
	void createProject_whenValidationFails_shouldReturnBadRequest() throws Exception {
		CreateProjectRequest request = new CreateProjectRequest("A", "Desc");

		mockMvc.perform(post("/api/v1/projects")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.success").value(false));
	}
}
