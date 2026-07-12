import { zodResolver } from '@hookform/resolvers/zod'
import { useMutation } from '@tanstack/react-query'
import axios from 'axios'
import { Loader2 } from 'lucide-react'
import { useForm } from 'react-hook-form'
import { Link, useLocation, useNavigate } from 'react-router-dom'
import { z } from 'zod'

import { login } from '@/features/auth/api'
import { useAuth } from '@/features/auth/auth-context'
import { AuthShell } from '@/features/auth/components/auth-shell'
import { AlertError } from '@/shared/ui/alert'
import { Button } from '@/shared/ui/button'
import { Input } from '@/shared/ui/input'
import { Label } from '@/shared/ui/label'

const schema = z.object({
  email: z.string().email('Invalid email address'),
  password: z.string().min(1, 'Password is required'),
})

type FormValues = z.infer<typeof schema>

export function LoginPage() {
  const navigate = useNavigate()
  const location = useLocation()
  const { login: loginContext } = useAuth()
  const from = ((location.state as { from?: string } | undefined)?.from ?? '/projects') as string

  const form = useForm<FormValues>({
    resolver: zodResolver(schema),
    defaultValues: {
      email: '',
      password: '',
    },
  })

  const mutation = useMutation({
    mutationFn: login,
    onSuccess: (tokens) => {
      loginContext({ accessToken: tokens.accessToken, refreshToken: tokens.refreshToken })
      navigate(from, { replace: true })
    },
  })

  const errorMessage =
    (axios.isAxiosError(mutation.error) && (mutation.error.response?.data as { message?: string } | undefined)?.message) ||
    mutation.error?.message ||
    null

  return (
    <AuthShell title="Welcome back" description="Sign in to manage users and projects.">
      <form className="space-y-4" onSubmit={form.handleSubmit((values) => mutation.mutate(values))}>
        <div className="space-y-1.5">
          <Label htmlFor="email">Email</Label>
          <Input id="email" type="email" placeholder="owner@example.com" {...form.register('email')} />
          {form.formState.errors.email && <p className="text-xs text-danger">{form.formState.errors.email.message}</p>}
        </div>

        <div className="space-y-1.5">
          <Label htmlFor="password">Password</Label>
          <Input id="password" type="password" placeholder="••••••••" {...form.register('password')} />
          {form.formState.errors.password && <p className="text-xs text-danger">{form.formState.errors.password.message}</p>}
        </div>

        {errorMessage && <AlertError message={errorMessage} />}

        <Button type="submit" className="w-full" disabled={mutation.isPending}>
          {mutation.isPending && <Loader2 className="mr-2 h-4 w-4 animate-spin" />}
          Sign in
        </Button>

        <p className="text-center text-sm text-muted-foreground">
          No account?{' '}
          <Link className="font-medium text-primary hover:underline" to="/register">
            Create one
          </Link>
        </p>
      </form>
    </AuthShell>
  )
}
