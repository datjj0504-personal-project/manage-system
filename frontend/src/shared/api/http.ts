import axios, { type AxiosError, type InternalAxiosRequestConfig } from 'axios'

import { clearTokens, getAccessToken, getRefreshToken, setTokens } from '@/features/auth/auth-storage'
import { env } from '@/shared/config/env'
import type { ApiResponse } from '@/shared/types/api'

type RefreshTokenPayload = {
  accessToken: string
  refreshToken: string
  tokenType: 'Bearer'
  expiresIn: number
}

type RetriableRequestConfig = InternalAxiosRequestConfig & {
  _retry?: boolean
}

const api = axios.create({
  baseURL: env.apiBaseUrl,
  headers: {
    'Content-Type': 'application/json',
  },
})

const refreshClient = axios.create({
  baseURL: env.apiBaseUrl,
  headers: {
    'Content-Type': 'application/json',
  },
})

let refreshingPromise: Promise<string | null> | null = null

api.interceptors.request.use((config) => {
  const token = getAccessToken()
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

api.interceptors.response.use(
  (response) => response,
  async (error: AxiosError) => {
    const request = error.config as RetriableRequestConfig | undefined
    if (!request || error.response?.status !== 401 || request._retry) {
      return Promise.reject(error)
    }

    request._retry = true

    const refreshToken = getRefreshToken()
    if (!refreshToken) {
      clearTokens()
      return Promise.reject(error)
    }

    if (!refreshingPromise) {
      refreshingPromise = refreshClient
        .post<ApiResponse<RefreshTokenPayload>>('/api/v1/auth/refresh', { refreshToken })
        .then((res) => {
          setTokens({
            accessToken: res.data.data.accessToken,
            refreshToken: res.data.data.refreshToken,
          })
          return res.data.data.accessToken
        })
        .catch(() => {
          clearTokens()
          return null
        })
        .finally(() => {
          refreshingPromise = null
        })
    }

    const newToken = await refreshingPromise
    if (!newToken) {
      return Promise.reject(error)
    }

    request.headers.Authorization = `Bearer ${newToken}`
    return api(request)
  },
)

export { api }
