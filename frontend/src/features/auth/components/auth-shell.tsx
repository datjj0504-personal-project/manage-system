import type { ReactNode } from 'react'

import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/shared/ui/card'

type AuthShellProps = {
  title: string
  description: string
  children: ReactNode
}

export function AuthShell({ title, description, children }: AuthShellProps) {
  return (
    <div className="relative grid min-h-screen place-items-center overflow-hidden bg-[#f4f7ff] px-4 py-10">
      <div className="pointer-events-none absolute -left-16 -top-16 h-72 w-72 rounded-full bg-[#8ec5ff]/30 blur-3xl" />
      <div className="pointer-events-none absolute -right-20 bottom-0 h-80 w-80 rounded-full bg-[#8de4c3]/30 blur-3xl" />
      <Card className="w-full max-w-md animate-fade-in">
        <CardHeader>
          <p className="text-xs uppercase tracking-[0.18em] text-muted-foreground">Task Management</p>
          <CardTitle>{title}</CardTitle>
          <CardDescription>{description}</CardDescription>
        </CardHeader>
        <CardContent>{children}</CardContent>
      </Card>
    </div>
  )
}
