import { FolderKanban, LogOut, UserRound } from 'lucide-react'
import { NavLink, Outlet, useNavigate } from 'react-router-dom'

import { useAuth } from '@/features/auth/auth-context'
import { Button } from '@/shared/ui/button'

const navItems = [
  { to: '/projects', label: 'Projects', icon: FolderKanban },
  { to: '/profile', label: 'My Profile', icon: UserRound },
]

export function DashboardLayout() {
  const navigate = useNavigate()
  const { logout } = useAuth()

  return (
    <div className="min-h-screen bg-gradient-to-b from-[#f8fbff] via-[#eef4ff] to-[#f6f8fc] text-foreground">
      <div className="mx-auto grid min-h-screen w-full max-w-7xl grid-cols-1 gap-6 px-4 py-6 md:grid-cols-[240px_1fr]">
        <aside className="rounded-lg border border-border bg-card p-4 shadow-soft animate-fade-in">
          <div className="mb-8">
            <p className="text-xs uppercase tracking-[0.18em] text-muted-foreground">Task Management</p>
            <h1 className="mt-2 text-xl font-semibold">Admin Dashboard</h1>
          </div>

          <nav className="space-y-2">
            {navItems.map((item) => {
              const Icon = item.icon
              return (
                <NavLink
                  key={item.to}
                  to={item.to}
                  className={({ isActive }) =>
                    [
                      'flex items-center gap-3 rounded-md px-3 py-2 text-sm transition',
                      isActive
                        ? 'bg-primary text-primary-foreground'
                        : 'text-foreground hover:bg-muted',
                    ].join(' ')
                  }
                >
                  <Icon className="h-4 w-4" />
                  <span>{item.label}</span>
                </NavLink>
              )
            })}
          </nav>

          <div className="mt-8 border-t border-border pt-4">
            <Button
              variant="secondary"
              className="w-full justify-start"
              onClick={() => {
                logout()
                navigate('/login', { replace: true })
              }}
            >
              <LogOut className="mr-2 h-4 w-4" />
              Logout
            </Button>
          </div>
        </aside>

        <main className="animate-fade-in">
          <Outlet />
        </main>
      </div>
    </div>
  )
}
