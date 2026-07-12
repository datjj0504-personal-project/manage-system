import { api } from '@/shared/api/http'
import type { ApiResponse } from '@/shared/types/api'

export type LoginRequest = {
  email: string
  password: string
}

export type RegisterRequest = {
  fullName: string
  email: string
  password: string
}

export type TokenResponse = {
  accessToken: string
  refreshToken: string
  tokenType: 'Bearer'
  expiresIn: number
}

export type RegisterResponse = {
  id: string
  email: string
  fullName: string
  createdAt: string
}

export async function login(payload: LoginRequest) {
  const response = await api.post<ApiResponse<TokenResponse>>('/api/v1/auth/login', payload)
  return response.data.data
}

export async function register(payload: RegisterRequest) {
  const response = await api.post<ApiResponse<RegisterResponse>>('/api/v1/auth/register', payload)
  return response.data.data
}
