import { api } from '@/shared/api/http'
import type { ApiResponse } from '@/shared/types/api'

export type UserProfile = {
  id: string
  email: string
  fullName: string
  avatarUrl: string | null
  createdAt: string
  updatedAt: string
}

export type UpdateProfilePayload = {
  fullName?: string
  avatarUrl?: string
}

export type ChangePasswordPayload = {
  currentPassword: string
  newPassword: string
}

export async function getMyProfile() {
  const response = await api.get<ApiResponse<UserProfile>>('/api/v1/users/me')
  return response.data.data
}

export async function updateMyProfile(payload: UpdateProfilePayload) {
  const response = await api.patch<ApiResponse<UserProfile>>('/api/v1/users/me', payload)
  return response.data.data
}

export async function changePassword(payload: ChangePasswordPayload) {
  await api.put<ApiResponse<null>>('/api/v1/users/me/password', payload)
}
