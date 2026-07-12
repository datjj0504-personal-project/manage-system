package dev.datnt.taskmanagement.service;

import dev.datnt.taskmanagement.dto.project.CreateProjectRequest;
import dev.datnt.taskmanagement.dto.project.ProjectListResponse;
import dev.datnt.taskmanagement.dto.project.ProjectResponse;
import dev.datnt.taskmanagement.dto.project.ProjectSummaryResponse;
import dev.datnt.taskmanagement.dto.project.UpdateProjectRequest;
import dev.datnt.taskmanagement.entity.ProjectEntity;
import dev.datnt.taskmanagement.entity.UserEntity;
import dev.datnt.taskmanagement.exception.AuthorizationException;
import dev.datnt.taskmanagement.mapper.ProjectMapper;
import dev.datnt.taskmanagement.repository.ProjectMemberRepository;
import dev.datnt.taskmanagement.repository.ProjectRepository;
import dev.datnt.taskmanagement.repository.UserRepository;
import dev.datnt.taskmanagement.service.impl.ProjectServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

	@Mock
	private ProjectRepository projectRepository;
	@Mock
	private ProjectMemberRepository projectMemberRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	private ProjectMapper projectMapper;

	@InjectMocks
	private ProjectServiceImpl projectService;

	private UserEntity currentUser;

	@BeforeEach
	void setUp() {
		SecurityContextHolder.clearContext();
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("owner@example.com", "N/A", Collections.emptyList())
		);

		currentUser = new UserEntity();
		currentUser.setId(UUID.randomUUID());
		currentUser.setEmail("owner@example.com");
		when(userRepository.findByEmail("owner@example.com")).thenReturn(Optional.of(currentUser));
	}

	@Test
	void createProject_shouldCreateProjectAndOwnerMembership() {
		CreateProjectRequest request = new CreateProjectRequest("Project Alpha", "Desc");
		UUID projectId = UUID.randomUUID();
		Instant now = Instant.now();

		when(projectRepository.save(any(ProjectEntity.class))).thenAnswer(invocation -> {
			ProjectEntity project = invocation.getArgument(0);
			project.setId(projectId);
			project.setCreatedAt(now);
			project.setUpdatedAt(now);
			return project;
		});

		ProjectResponse mapped = new ProjectResponse(projectId, "Project Alpha", "Desc", currentUser.getId(), now, now);
		when(projectMapper.toProjectResponse(any(ProjectEntity.class))).thenReturn(mapped);

		ProjectResponse result = projectService.createProject(request);

		assertThat(result).isEqualTo(mapped);
		verify(projectMemberRepository).save(any());

		ArgumentCaptor<ProjectEntity> projectCaptor = ArgumentCaptor.forClass(ProjectEntity.class);
		verify(projectRepository).save(projectCaptor.capture());
		assertThat(projectCaptor.getValue().getOwnerId()).isEqualTo(currentUser.getId());
	}

	@Test
	void getProjectDetail_whenNotMember_shouldThrowAuthorizationException() {
		UUID projectId = UUID.randomUUID();
		ProjectEntity project = new ProjectEntity();
		project.setId(projectId);
		project.setOwnerId(currentUser.getId());
		project.setName("Project A");

		when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
		when(projectMemberRepository.existsByProjectIdAndUserId(projectId, currentUser.getId())).thenReturn(false);

		assertThatThrownBy(() -> projectService.getProjectDetail(projectId))
				.isInstanceOf(AuthorizationException.class)
				.hasMessage("You are not a member of this project");
	}

	@Test
	void listProjects_shouldReturnPagedResponse() {
		UUID projectId = UUID.randomUUID();
		ProjectEntity project = new ProjectEntity();
		project.setId(projectId);
		project.setName("Project A");
		project.setOwnerId(currentUser.getId());

		Page<ProjectEntity> page = new PageImpl<>(List.of(project), Pageable.ofSize(20), 1);
		when(projectRepository.findAccessibleProjects(eq(currentUser.getId()), eq("Project"), any(Pageable.class))).thenReturn(page);
		when(projectMapper.toProjectSummaryResponse(project)).thenReturn(
				new ProjectSummaryResponse(projectId, "Project A", null, currentUser.getId(), null, null)
		);

		ProjectListResponse result = projectService.listProjects(0, 20, "createdAt,desc", "Project");

		assertThat(result.items()).hasSize(1);
		assertThat(result.totalElements()).isEqualTo(1);
	}

	@Test
	void deleteProject_whenNotOwner_shouldThrowAuthorizationException() {
		UUID projectId = UUID.randomUUID();
		ProjectEntity project = new ProjectEntity();
		project.setId(projectId);
		project.setOwnerId(UUID.randomUUID());

		when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
		when(projectMemberRepository.existsByProjectIdAndUserIdAndRole(projectId, currentUser.getId(), dev.datnt.taskmanagement.entity.ProjectMemberRole.OWNER))
				.thenReturn(false);

		assertThatThrownBy(() -> projectService.deleteProject(projectId))
				.isInstanceOf(AuthorizationException.class)
				.hasMessage("Only project owner can perform this action");
	}

	@Test
	void updateProject_whenOwner_shouldUpdateFields() {
		UUID projectId = UUID.randomUUID();
		ProjectEntity project = new ProjectEntity();
		project.setId(projectId);
		project.setOwnerId(currentUser.getId());
		project.setName("Old Name");
		project.setDescription("Old Desc");

		UpdateProjectRequest request = new UpdateProjectRequest("New Name", "New Desc");
		ProjectResponse mapped = new ProjectResponse(projectId, "New Name", "New Desc", currentUser.getId(), null, null);

		when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
		when(projectMemberRepository.existsByProjectIdAndUserIdAndRole(projectId, currentUser.getId(), dev.datnt.taskmanagement.entity.ProjectMemberRole.OWNER))
				.thenReturn(true);
		when(projectRepository.save(project)).thenReturn(project);
		when(projectMapper.toProjectResponse(project)).thenReturn(mapped);

		ProjectResponse result = projectService.updateProject(projectId, request);

		assertThat(result.name()).isEqualTo("New Name");
		assertThat(project.getName()).isEqualTo("New Name");
		assertThat(project.getDescription()).isEqualTo("New Desc");
	}
}
