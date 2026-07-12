import { AlertCircle } from 'lucide-react'

import { cn } from '@/shared/lib/utils'

type AlertProps = {
  message: string
  className?: string
}

export function AlertError({ message, className }: AlertProps) {
  return (
    <div className={cn('flex items-start gap-2 rounded-md border border-red-200 bg-red-50 px-3 py-2 text-sm text-red-700', className)}>
      <AlertCircle className="mt-0.5 h-4 w-4" />
      <p>{message}</p>
    </div>
  )
}
