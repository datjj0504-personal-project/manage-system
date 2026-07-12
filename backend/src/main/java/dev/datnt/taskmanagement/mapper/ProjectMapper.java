package dev.datnt.taskmanagement.mapper;

import dev.datnt.taskmanagement.dto.project.ProjectResponse;
import dev.datnt.taskmanagement.dto.project.ProjectSummaryResponse;
import dev.datnt.taskmanagement.entity.ProjectEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

	ProjectResponse toProjectResponse(ProjectEntity project);

	ProjectSummaryResponse toProjectSummaryResponse(ProjectEntity project);
}
