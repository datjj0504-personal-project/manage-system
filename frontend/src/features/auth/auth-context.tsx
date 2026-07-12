import { createContext, useContext, useMemo, useState, type ReactNode } from 'react'

import { clearTokens, getAccessToken, setTokens, type AuthTokens } from '@/features/auth/auth-storage'

type AuthContextValue = {
  isAuthenticated: boolean
  login: (tokens: AuthTokens) => void
  logout: () => void
}

const AuthContext = createContext<AuthContextValue | undefined>(undefined)

type AuthProviderProps = {
  children: ReactNode
}

export function AuthProvider({ children }: AuthProviderProps) {
  const [isAuthenticated, setIsAuthenticated] = useState(Boolean(getAccessToken()))

  const value = useMemo<AuthContextValue>(
    () => ({
      isAuthenticated,
      login: (tokens) => {
        setTokens(tokens)
        setIsAuthenticated(true)
      },
      logout: () => {
        clearTokens()
        setIsAuthenticated(false)
      },
    }),
    [isAuthenticated],
  )

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}

export function useAuth() {
  const context = useContext(AuthContext)
  if (!context) {
    throw new Error('useAuth must be used within AuthProvider')
  }

  return context
}
