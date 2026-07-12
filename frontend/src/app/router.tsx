import { Navigate, Route, Routes } from 'react-router-dom'

import { DashboardLayout } from '@/app/ui/dashboard-layout'
import { LoginPage } from '@/features/auth/pages/login-page'
import { RegisterPage } from '@/features/auth/pages/register-page'
import { ProtectedRoute } from '@/features/auth/protected-route'
import { ProfilePage } from '@/features/profile/pages/profile-page'
import { ProjectsPage } from '@/features/projects/pages/projects-page'
import { NotFoundPage } from '@/shared/pages/not-found-page'

export function AppRouter() {
  return (
    <Routes>
      <Route path="/login" element={<LoginPage />} />
      <Route path="/register" element={<RegisterPage />} />
      <Route
        path="/"
        element={
          <ProtectedRoute>
            <DashboardLayout />
          </ProtectedRoute>
        }
      >
        <Route index element={<Navigate to="/projects" replace />} />
        <Route path="projects" element={<ProjectsPage />} />
        <Route path="projects/:projectId" element={<ProjectsPage />} />
        <Route path="profile" element={<ProfilePage />} />
      </Route>
      <Route path="*" element={<NotFoundPage />} />
    </Routes>
  )
}
