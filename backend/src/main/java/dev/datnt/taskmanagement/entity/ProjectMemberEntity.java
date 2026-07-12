package dev.datnt.taskmanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
		name = "project_members",
		uniqueConstraints = @UniqueConstraint(name = "uk_project_members_project_user", columnNames = {"project_id", "user_id"})
)
public class ProjectMemberEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(name = "project_id", nullable = false)
	private UUID projectId;

	@Column(name = "user_id", nullable = false)
	private UUID userId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private ProjectMemberRole role;

	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;

	@PrePersist
	void prePersist() {
		this.createdAt = Instant.now();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getProjectId() {
		return projectId;
	}

	public void setProjectId(UUID projectId) {
		this.projectId = projectId;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public ProjectMemberRole getRole() {
		return role;
	}

	public void setRole(ProjectMemberRole role) {
		this.role = role;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}
}
