import { zodResolver } from '@hookform/resolvers/zod'
import { useMutation } from '@tanstack/react-query'
import axios from 'axios'
import { Loader2 } from 'lucide-react'
import { useForm } from 'react-hook-form'
import { Link, useNavigate } from 'react-router-dom'
import { z } from 'zod'

import { register } from '@/features/auth/api'
import { AuthShell } from '@/features/auth/components/auth-shell'
import { AlertError } from '@/shared/ui/alert'
import { Button } from '@/shared/ui/button'
import { Input } from '@/shared/ui/input'
import { Label } from '@/shared/ui/label'

const schema = z.object({
  fullName: z.string().trim().min(2, 'Full name must be at least 2 characters').max(100, 'Full name is too long'),
  email: z.string().email('Invalid email address'),
  password: z.string().min(8, 'Password must be at least 8 characters'),
})

type FormValues = z.infer<typeof schema>

export function RegisterPage() {
  const navigate = useNavigate()
  const form = useForm<FormValues>({
    resolver: zodResolver(schema),
    defaultValues: {
      fullName: '',
      email: '',
      password: '',
    },
  })

  const mutation = useMutation({
    mutationFn: register,
    onSuccess: () => {
      navigate('/login', { replace: true })
    },
  })

  const errorMessage =
    (axios.isAxiosError(mutation.error) && (mutation.error.response?.data as { message?: string } | undefined)?.message) ||
    mutation.error?.message ||
    null

  return (
    <AuthShell title="Create account" description="Register a new user before signing in.">
      <form className="space-y-4" onSubmit={form.handleSubmit((values) => mutation.mutate(values))}>
        <div className="space-y-1.5">
          <Label htmlFor="fullName">Full name</Label>
          <Input id="fullName" placeholder="Jane Owner" {...form.register('fullName')} />
          {form.formState.errors.fullName && <p className="text-xs text-danger">{form.formState.errors.fullName.message}</p>}
        </div>

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
          Create account
        </Button>

        <p className="text-center text-sm text-muted-foreground">
          Already have an account?{' '}
          <Link className="font-medium text-primary hover:underline" to="/login">
            Sign in
          </Link>
        </p>
      </form>
    </AuthShell>
  )
}
