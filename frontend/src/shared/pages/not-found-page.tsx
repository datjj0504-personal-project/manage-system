import { Link } from 'react-router-dom'

import { Button } from '@/shared/ui/button'

export function NotFoundPage() {
  return (
    <div className="grid min-h-screen place-items-center bg-background px-6">
      <div className="space-y-4 text-center">
        <p className="text-xs uppercase tracking-[0.18em] text-muted-foreground">404</p>
        <h1 className="text-3xl font-semibold">Page not found</h1>
        <p className="text-muted-foreground">The requested page does not exist in this sprint frontend.</p>
        <Link to="/">
          <Button>Back to dashboard</Button>
        </Link>
      </div>
    </div>
  )
}
