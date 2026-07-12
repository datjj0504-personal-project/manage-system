import { zodResolver } from '@hookform/resolvers/zod'
import { useMutation, useQuery } from '@tanstack/react-query'
import axios from 'axios'
import { Loader2, UserRound } from 'lucide-react'
import { useEffect, useState } from 'react'
import { useForm } from 'react-hook-form'
import { z } from 'zod'

import { changePassword, getMyProfile, updateMyProfile } from '@/features/profile/api'
import { AlertError } from '@/shared/ui/alert'
import { Button } from '@/shared/ui/button'
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/shared/ui/card'
import { Input } from '@/shared/ui/input'
import { Label } from '@/shared/ui/label'
import { Spinner } from '@/shared/ui/spinner'

const profileSchema = z.object({
  fullName: z.string().trim().min(2, 'Full name must be at least 2 characters').max(100, 'Full name is too long').optional(),
  avatarUrl: z
    .string()
    .trim()
    .url('Avatar URL must be a valid URL')
    .optional()
    .or(z.literal('')),
})

const passwordSchema = z.object({
  currentPassword: z.string().min(1, 'Current password is required'),
  newPassword: z.string().min(8, 'New password must be at least 8 characters'),
})

type ProfileFormValues = z.infer<typeof profileSchema>
type PasswordFormValues = z.infer<typeof passwordSchema>

export function ProfilePage() {
  const [profileSuccess, setProfileSuccess] = useState('')
  const [passwordSuccess, setPasswordSuccess] = useState('')

  const profileQuery = useQuery({
    queryKey: ['my-profile'],
    queryFn: getMyProfile,
  })

  const profileForm = useForm<ProfileFormValues>({
    resolver: zodResolver(profileSchema),
    defaultValues: {
      fullName: '',
      avatarUrl: '',
    },
  })

  const passwordForm = useForm<PasswordFormValues>({
    resolver: zodResolver(passwordSchema),
    defaultValues: {
      currentPassword: '',
      newPassword: '',
    },
  })

  useEffect(() => {
    if (profileQuery.data) {
      profileForm.reset({
        fullName: profileQuery.data.fullName,
        avatarUrl: profileQuery.data.avatarUrl ?? '',
      })
    }
  }, [profileForm, profileQuery.data])

  const updateMutation = useMutation({
    mutationFn: updateMyProfile,
    onSuccess: () => {
      profileQuery.refetch()
      setProfileSuccess('Profile updated successfully.')
    },
  })

  const passwordMutation = useMutation({
    mutationFn: changePassword,
    onSuccess: () => {
      passwordForm.reset({ currentPassword: '', newPassword: '' })
      setPasswordSuccess('Password changed successfully.')
    },
  })

  const profileError =
    (axios.isAxiosError(updateMutation.error) && (updateMutation.error.response?.data as { message?: string } | undefined)?.message) ||
    updateMutation.error?.message

  const passwordError =
    (axios.isAxiosError(passwordMutation.error) &&
      (passwordMutation.error.response?.data as { message?: string } | undefined)?.message) ||
    passwordMutation.error?.message

  if (profileQuery.isLoading) {
    return (
      <div className="grid min-h-[60vh] place-items-center">
        <Spinner />
      </div>
    )
  }

  if (profileQuery.error || !profileQuery.data) {
    return (
      <Card>
        <CardHeader>
          <CardTitle>Profile</CardTitle>
          <CardDescription>Unable to load profile data.</CardDescription>
        </CardHeader>
        <CardContent>
          <AlertError message="Failed to fetch profile. Please try again." />
          <Button className="mt-4" onClick={() => profileQuery.refetch()}>
            Retry
          </Button>
        </CardContent>
      </Card>
    )
  }

  return (
    <div className="grid gap-6 lg:grid-cols-[1.2fr_1fr]">
      <Card>
        <CardHeader>
          <div className="flex items-center gap-3">
            <div className="rounded-full bg-muted p-2">
              <UserRound className="h-5 w-5 text-primary" />
            </div>
            <div>
              <CardTitle>My Profile</CardTitle>
              <CardDescription>Update your personal information.</CardDescription>
            </div>
          </div>
        </CardHeader>
        <CardContent>
          <form
            className="space-y-4"
            onSubmit={profileForm.handleSubmit((values) => {
              setProfileSuccess('')
              updateMutation.mutate({
                fullName: values.fullName?.trim() || undefined,
                avatarUrl: values.avatarUrl?.trim() || undefined,
              })
            })}
          >
            <div className="space-y-1.5">
              <Label htmlFor="email">Email</Label>
              <Input id="email" value={profileQuery.data.email} disabled />
            </div>
            <div className="space-y-1.5">
              <Label htmlFor="fullName">Full name</Label>
              <Input id="fullName" {...profileForm.register('fullName')} />
              {profileForm.formState.errors.fullName && (
                <p className="text-xs text-danger">{profileForm.formState.errors.fullName.message}</p>
              )}
            </div>
            <div className="space-y-1.5">
              <Label htmlFor="avatarUrl">Avatar URL</Label>
              <Input id="avatarUrl" placeholder="https://..." {...profileForm.register('avatarUrl')} />
              {profileForm.formState.errors.avatarUrl && (
                <p className="text-xs text-danger">{profileForm.formState.errors.avatarUrl.message}</p>
              )}
            </div>
            {profileError && <AlertError message={profileError} />}
            {profileSuccess && <p className="text-sm text-emerald-600">{profileSuccess}</p>}
            <Button type="submit" disabled={updateMutation.isPending}>
              {updateMutation.isPending && <Loader2 className="mr-2 h-4 w-4 animate-spin" />}
              Save profile
            </Button>
          </form>
        </CardContent>
      </Card>

      <Card>
        <CardHeader>
          <CardTitle>Change Password</CardTitle>
          <CardDescription>Use a strong password with at least 8 characters.</CardDescription>
        </CardHeader>
        <CardContent>
          <form
            className="space-y-4"
            onSubmit={passwordForm.handleSubmit((values) => {
              setPasswordSuccess('')
              passwordMutation.mutate(values)
            })}
          >
            <div className="space-y-1.5">
              <Label htmlFor="currentPassword">Current password</Label>
              <Input id="currentPassword" type="password" {...passwordForm.register('currentPassword')} />
              {passwordForm.formState.errors.currentPassword && (
                <p className="text-xs text-danger">{passwordForm.formState.errors.currentPassword.message}</p>
              )}
            </div>
            <div className="space-y-1.5">
              <Label htmlFor="newPassword">New password</Label>
              <Input id="newPassword" type="password" {...passwordForm.register('newPassword')} />
              {passwordForm.formState.errors.newPassword && (
                <p className="text-xs text-danger">{passwordForm.formState.errors.newPassword.message}</p>
              )}
            </div>
            {passwordError && <AlertError message={passwordError} />}
            {passwordSuccess && <p className="text-sm text-emerald-600">{passwordSuccess}</p>}
            <Button type="submit" disabled={passwordMutation.isPending}>
              {passwordMutation.isPending && <Loader2 className="mr-2 h-4 w-4 animate-spin" />}
              Update password
            </Button>
          </form>
        </CardContent>
      </Card>
    </div>
  )
}
