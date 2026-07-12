package dev.datnt.taskmanagement.repository;

import dev.datnt.taskmanagement.entity.ProjectMemberEntity;
import dev.datnt.taskmanagement.entity.ProjectMemberRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProjectMemberRepository extends JpaRepository<ProjectMemberEntity, UUID> {

	boolean existsByProjectIdAndUserId(UUID projectId, UUID userId);

	boolean existsByProjectIdAndUserIdAndRole(UUID projectId, UUID userId, ProjectMemberRole role);
}
