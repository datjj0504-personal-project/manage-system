package dev.datnt.taskmanagement.repository;

import dev.datnt.taskmanagement.entity.ProjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ProjectRepository extends JpaRepository<ProjectEntity, UUID> {

	@Query("""
			select p
			from ProjectEntity p
			join ProjectMemberEntity pm on pm.projectId = p.id
			where p.deletedAt is null
			and pm.userId = :userId
			and (:keyword is null or lower(p.name) like lower(concat('%', :keyword, '%')))
			""")
	Page<ProjectEntity> findAccessibleProjects(
			@Param("userId") UUID userId,
			@Param("keyword") String keyword,
			Pageable pageable
	);
}
