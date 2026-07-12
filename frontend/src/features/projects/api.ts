import { api } from '@/shared/api/http'
import type { ApiResponse } from '@/shared/types/api'

export type Project = {
  id: string
  name: string
  description: string | null
  ownerId: string
  createdAt: string
  updatedAt: string
}

export type ProjectListData = {
  items: Project[]
  page: number
  size: number
  totalElements: number
  totalPages: number
}

export type ListProjectsParams = {
  page?: number
  size?: number
  sort?: string
  q?: string
}

export type CreateProjectPayload = {
  name: string
  description?: string
}

export type UpdateProjectPayload = {
  name?: string
  description?: string
}

export async function listProjects(params: ListProjectsParams) {
  const response = await api.get<ApiResponse<ProjectListData>>('/api/v1/projects', { params })
  return response.data.data
}

export async function getProjectDetail(projectId: string) {
  const response = await api.get<ApiResponse<Project>>(`/api/v1/projects/${projectId}`)
  return response.data.data
}

export async function createProject(payload: CreateProjectPayload) {
  const response = await api.post<ApiResponse<Project>>('/api/v1/projects', payload)
  return response.data.data
}

export async function updateProject(projectId: string, payload: UpdateProjectPayload) {
  const response = await api.patch<ApiResponse<Project>>(`/api/v1/projects/${projectId}`, payload)
  return response.data.data
}

export async function deleteProject(projectId: string) {
  await api.delete(`/api/v1/projects/${projectId}`)
}
