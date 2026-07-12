package dev.datnt.taskmanagement.service.impl;

import dev.datnt.taskmanagement.dto.project.CreateProjectRequest;
import dev.datnt.taskmanagement.dto.project.ProjectListResponse;
import dev.datnt.taskmanagement.dto.project.ProjectResponse;
import dev.datnt.taskmanagement.dto.project.ProjectSummaryResponse;
import dev.datnt.taskmanagement.dto.project.UpdateProjectRequest;
import dev.datnt.taskmanagement.entity.ProjectEntity;
import dev.datnt.taskmanagement.entity.ProjectMemberEntity;
import dev.datnt.taskmanagement.entity.ProjectMemberRole;
import dev.datnt.taskmanagement.entity.UserEntity;
import dev.datnt.taskmanagement.exception.AuthorizationException;
import dev.datnt.taskmanagement.exception.ResourceNotFoundException;
import dev.datnt.taskmanagement.mapper.ProjectMapper;
import dev.datnt.taskmanagement.repository.ProjectMemberRepository;
import dev.datnt.taskmanagement.repository.ProjectRepository;
import dev.datnt.taskmanagement.repository.UserRepository;
import dev.datnt.taskmanagement.service.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@Service
public class ProjectServiceImpl implements ProjectService {

	private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("createdAt", "updatedAt", "name");

	private final ProjectRepository projectRepository;
	private final ProjectMemberRepository projectMemberRepository;
	private final UserRepository userRepository;
	private final ProjectMapper projectMapper;

	public ProjectServiceImpl(
			ProjectRepository projectRepository,
			ProjectMemberRepository projectMemberRepository,
			UserRepository userRepository,
			ProjectMapper projectMapper
	) {
		this.projectRepository = projectRepository;
		this.projectMemberRepository = projectMemberRepository;
		this.userRepository = userRepository;
		this.projectMapper = projectMapper;
	}

	@Override
	@Transactional
	public ProjectResponse createProject(CreateProjectRequest request) {
		UserEntity currentUser = getCurrentUser();

		ProjectEntity project = new ProjectEntity();
		project.setOwnerId(currentUser.getId());
		project.setName(request.name().trim());
		project.setDescription(normalizeNullableText(request.description()));

		ProjectEntity savedProject = projectRepository.save(project);

		ProjectMemberEntity ownerMember = new ProjectMemberEntity();
		ownerMember.setProjectId(savedProject.getId());
		ownerMember.setUserId(currentUser.getId());
		ownerMember.setRole(ProjectMemberRole.OWNER);
		projectMemberRepository.save(ownerMember);

		return projectMapper.toProjectResponse(savedProject);
	}

	@Override
	@Transactional(readOnly = true)
	public ProjectResponse getProjectDetail(UUID projectId) {
		UUID currentUserId = getCurrentUser().getId();
		ProjectEntity project = getProjectOrThrow(projectId);
		requireMembership(projectId, currentUserId);
		return projectMapper.toProjectResponse(project);
	}

	@Override
	@Transactional(readOnly = true)
	public ProjectListResponse listProjects(int page, int size, String sort, String keyword) {
		if (page < 0) {
			throw new IllegalArgumentException("page must be >= 0");
		}
		if (size < 1 || size > 100) {
			throw new IllegalArgumentException("size must be between 1 and 100");
		}

		Sort sortSpec = parseSort(sort);
		Pageable pageable = PageRequest.of(page, size, sortSpec);
		UUID currentUserId = getCurrentUser().getId();

		Page<ProjectEntity> projectPage = projectRepository.findAccessibleProjects(
				currentUserId,
				normalizeKeyword(keyword),
				pageable
		);

		List<ProjectSummaryResponse> items = projectPage.getContent()
				.stream()
				.map(projectMapper::toProjectSummaryResponse)
				.toList();

		return new ProjectListResponse(
				items,
				projectPage.getNumber(),
				projectPage.getSize(),
				projectPage.getTotalElements(),
				projectPage.getTotalPages()
		);
	}

	@Override
	@Transactional
	public ProjectResponse updateProject(UUID projectId, UpdateProjectRequest request) {
		UUID currentUserId = getCurrentUser().getId();
		ProjectEntity project = getProjectOrThrow(projectId);
		requireOwner(projectId, currentUserId);

		if (request.name() != null) {
			project.setName(request.name().trim());
		}
		if (request.description() != null) {
			project.setDescription(normalizeNullableText(request.description()));
		}

		ProjectEntity savedProject = projectRepository.save(project);
		return projectMapper.toProjectResponse(savedProject);
	}

	@Override
	@Transactional
	public void deleteProject(UUID projectId) {
		UUID currentUserId = getCurrentUser().getId();
		ProjectEntity project = getProjectOrThrow(projectId);
		requireOwner(projectId, currentUserId);
		projectRepository.delete(project);
	}

	private ProjectEntity getProjectOrThrow(UUID projectId) {
		return projectRepository.findById(projectId)
				.filter(project -> project.getDeletedAt() == null)
				.orElseThrow(() -> new ResourceNotFoundException("Project not found"));
	}

	private void requireMembership(UUID projectId, UUID userId) {
		if (!projectMemberRepository.existsByProjectIdAndUserId(projectId, userId)) {
			throw new AuthorizationException("You are not a member of this project");
		}
	}

	private void requireOwner(UUID projectId, UUID userId) {
		if (!projectMemberRepository.existsByProjectIdAndUserIdAndRole(projectId, userId, ProjectMemberRole.OWNER)) {
			throw new AuthorizationException("Only project owner can perform this action");
		}
	}

	private UserEntity getCurrentUser() {
		String email = getCurrentUserEmail();
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
	}

	private String getCurrentUserEmail() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getName() == null) {
			throw new AuthorizationException("Authenticated user not found");
		}
		return authentication.getName().trim().toLowerCase(Locale.ROOT);
	}

	private String normalizeKeyword(String keyword) {
		if (keyword == null || keyword.isBlank()) {
			return "";
		}
		return keyword.trim();
	}

	private String normalizeNullableText(String value) {
		if (value == null) {
			return null;
		}
		String trimmed = value.trim();
		return trimmed.isEmpty() ? null : trimmed;
	}

	private Sort parseSort(String sort) {
		if (sort == null || sort.isBlank()) {
			return Sort.by(Sort.Direction.DESC, "createdAt");
		}

		String[] parts = sort.split(",", 2);
		String property = parts[0].trim();
		if (!ALLOWED_SORT_FIELDS.contains(property)) {
			throw new IllegalArgumentException("Invalid sort field");
		}

		Sort.Direction direction = Sort.Direction.DESC;
		if (parts.length == 2 && !parts[1].isBlank()) {
			try {
				direction = Sort.Direction.fromString(parts[1].trim());
			} catch (IllegalArgumentException exception) {
				throw new IllegalArgumentException("Invalid sort direction");
			}
		}

		return Sort.by(direction, property);
	}
}
