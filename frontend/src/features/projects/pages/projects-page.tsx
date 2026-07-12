import { zodResolver } from '@hookform/resolvers/zod'
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query'
import axios from 'axios'
import { Loader2, PencilLine, Plus, Search, Trash2 } from 'lucide-react'
import { useEffect, useMemo, useState } from 'react'
import { useForm } from 'react-hook-form'
import { useNavigate, useParams } from 'react-router-dom'
import { z } from 'zod'

import {
  createProject,
  deleteProject,
  getProjectDetail,
  listProjects,
  updateProject,
  type Project,
} from '@/features/projects/api'
import { AlertError } from '@/shared/ui/alert'
import { Button } from '@/shared/ui/button'
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/shared/ui/card'
import { Input } from '@/shared/ui/input'
import { Label } from '@/shared/ui/label'
import { Spinner } from '@/shared/ui/spinner'
import { Textarea } from '@/shared/ui/textarea'

const createSchema = z.object({
  name: z.string().trim().min(3, 'Name must be at least 3 characters').max(120, 'Name is too long'),
  description: z.string().trim().max(2000, 'Description is too long').optional().or(z.literal('')),
})

const updateSchema = z.object({
  name: z.string().trim().min(3, 'Name must be at least 3 characters').max(120, 'Name is too long').optional(),
  description: z.string().trim().max(2000, 'Description is too long').optional().or(z.literal('')),
})

type CreateValues = z.infer<typeof createSchema>
type UpdateValues = z.infer<typeof updateSchema>

function getErrorMessage(error: unknown, fallback: string) {
  if (axios.isAxiosError(error)) {
    return (error.response?.data as { message?: string } | undefined)?.message ?? fallback
  }
  if (error instanceof Error) {
    return error.message
  }
  return fallback
}

export function ProjectsPage() {
  const navigate = useNavigate()
  const params = useParams<{ projectId: string }>()
  const queryClient = useQueryClient()
  const [page, setPage] = useState(0)
  const [search, setSearch] = useState('')
  const [keyword, setKeyword] = useState('')
  const [selectedProjectId, setSelectedProjectId] = useState<string | null>(null)

  useEffect(() => {
    if (params.projectId) {
      setSelectedProjectId(params.projectId)
    }
  }, [params.projectId])

  const createForm = useForm<CreateValues>({
    resolver: zodResolver(createSchema),
    defaultValues: { name: '', description: '' },
  })

  const updateForm = useForm<UpdateValues>({
    resolver: zodResolver(updateSchema),
    defaultValues: { name: '', description: '' },
  })

  const projectsQuery = useQuery({
    queryKey: ['projects', page, keyword],
    queryFn: () => listProjects({ page, size: 10, sort: 'createdAt,desc', q: keyword || undefined }),
  })

  const projectDetailQuery = useQuery({
    queryKey: ['project-detail', selectedProjectId],
    queryFn: () => getProjectDetail(selectedProjectId as string),
    enabled: Boolean(selectedProjectId),
  })

  useEffect(() => {
    if (!projectsQuery.data?.items.length) {
      setSelectedProjectId(null)
      return
    }

    if (!selectedProjectId || !projectsQuery.data.items.some((item) => item.id === selectedProjectId)) {
      const fallbackId = projectsQuery.data.items[0].id
      setSelectedProjectId(fallbackId)
      navigate(`/projects/${fallbackId}`, { replace: true })
    }
  }, [navigate, projectsQuery.data, selectedProjectId])

  useEffect(() => {
    const detail = projectDetailQuery.data
    if (detail) {
      updateForm.reset({
        name: detail.name,
        description: detail.description ?? '',
      })
    }
  }, [projectDetailQuery.data, updateForm])

  const createMutation = useMutation({
    mutationFn: createProject,
    onSuccess: (created) => {
      createForm.reset({ name: '', description: '' })
      queryClient.invalidateQueries({ queryKey: ['projects'] })
      setSelectedProjectId(created.id)
      navigate(`/projects/${created.id}`)
    },
  })

  const updateMutation = useMutation({
    mutationFn: ({ projectId, values }: { projectId: string; values: UpdateValues }) =>
      updateProject(projectId, {
        name: values.name?.trim() || undefined,
        description: values.description?.trim() || undefined,
      }),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['projects'] })
      queryClient.invalidateQueries({ queryKey: ['project-detail', selectedProjectId] })
    },
  })

  const deleteMutation = useMutation({
    mutationFn: deleteProject,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['projects'] })
      setSelectedProjectId(null)
      navigate('/projects')
    },
  })

  const projects = projectsQuery.data?.items ?? []
  const selectedProject = projectDetailQuery.data as Project | undefined

  const totalPages = projectsQuery.data?.totalPages ?? 0
  const canPrevious = page > 0
  const canNext = totalPages > 0 && page + 1 < totalPages

  const listError = projectsQuery.error ? getErrorMessage(projectsQuery.error, 'Failed to load projects.') : null
  const createError = createMutation.error ? getErrorMessage(createMutation.error, 'Failed to create project.') : null
  const detailError = projectDetailQuery.error ? getErrorMessage(projectDetailQuery.error, 'Failed to load project detail.') : null
  const updateError = updateMutation.error ? getErrorMessage(updateMutation.error, 'Failed to update project.') : null
  const deleteError = deleteMutation.error ? getErrorMessage(deleteMutation.error, 'Failed to delete project.') : null

  const selectionTitle = useMemo(() => {
    if (!selectedProjectId) return 'Select a project'
    if (projectDetailQuery.isLoading) return 'Loading project detail...'
    return selectedProject?.name ?? 'Project detail'
  }, [projectDetailQuery.isLoading, selectedProject?.name, selectedProjectId])

  return (
    <div className="grid gap-6 lg:grid-cols-[1.2fr_1fr]">
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>Create Project</CardTitle>
            <CardDescription>Add a workspace visible to your memberships.</CardDescription>
          </CardHeader>
          <CardContent>
            <form
              className="space-y-4"
              onSubmit={createForm.handleSubmit((values) =>
                createMutation.mutate({
                  name: values.name.trim(),
                  description: values.description?.trim() || undefined,
                }),
              )}
            >
              <div className="space-y-1.5">
                <Label htmlFor="createName">Project name</Label>
                <Input id="createName" placeholder="Sprint Planning" {...createForm.register('name')} />
                {createForm.formState.errors.name && <p className="text-xs text-danger">{createForm.formState.errors.name.message}</p>}
              </div>

              <div className="space-y-1.5">
                <Label htmlFor="createDescription">Description</Label>
                <Textarea id="createDescription" placeholder="Optional project context" {...createForm.register('description')} />
                {createForm.formState.errors.description && (
                  <p className="text-xs text-danger">{createForm.formState.errors.description.message}</p>
                )}
              </div>

              {createError && <AlertError message={createError} />}

              <Button type="submit" disabled={createMutation.isPending}>
                {createMutation.isPending ? <Loader2 className="mr-2 h-4 w-4 animate-spin" /> : <Plus className="mr-2 h-4 w-4" />}
                Create project
              </Button>
            </form>
          </CardContent>
        </Card>

        <Card>
          <CardHeader className="space-y-4">
            <div>
              <CardTitle>Projects</CardTitle>
              <CardDescription>Manage existing projects from your accessible list.</CardDescription>
            </div>
            <form
              className="flex gap-2"
              onSubmit={(event) => {
                event.preventDefault()
                setPage(0)
                setKeyword(search.trim())
              }}
            >
              <Input
                value={search}
                onChange={(event) => setSearch(event.target.value)}
                placeholder="Search project by name"
                aria-label="Search project"
              />
              <Button variant="secondary" type="submit">
                <Search className="mr-2 h-4 w-4" />
                Search
              </Button>
            </form>
          </CardHeader>
          <CardContent>
            {projectsQuery.isLoading && (
              <div className="grid min-h-24 place-items-center">
                <Spinner />
              </div>
            )}

            {listError && <AlertError message={listError} />}

            {!projectsQuery.isLoading && !listError && projects.length === 0 && (
              <p className="rounded-md border border-dashed border-border px-4 py-6 text-center text-sm text-muted-foreground">
                No projects found. Create your first project above.
              </p>
            )}

            {projects.length > 0 && (
              <div className="space-y-2">
                {projects.map((project) => (
                  <button
                    key={project.id}
                    type="button"
                    className={[
                      'w-full rounded-md border p-3 text-left transition',
                      project.id === selectedProjectId ? 'border-primary bg-[#f1f6ff]' : 'border-border bg-white hover:bg-muted',
                    ].join(' ')}
                    onClick={() => {
                      setSelectedProjectId(project.id)
                      navigate(`/projects/${project.id}`)
                    }}
                  >
                    <p className="font-medium">{project.name}</p>
                    <p className="mt-1 text-xs text-muted-foreground">{project.description || 'No description'}</p>
                  </button>
                ))}

                <div className="flex items-center justify-between pt-2 text-sm">
                  <p className="text-muted-foreground">
                    Page {(projectsQuery.data?.page ?? 0) + 1} of {Math.max(totalPages, 1)}
                  </p>
                  <div className="space-x-2">
                    <Button variant="outline" size="sm" disabled={!canPrevious} onClick={() => setPage((prev) => prev - 1)}>
                      Previous
                    </Button>
                    <Button variant="outline" size="sm" disabled={!canNext} onClick={() => setPage((prev) => prev + 1)}>
                      Next
                    </Button>
                  </div>
                </div>
              </div>
            )}
          </CardContent>
        </Card>
      </div>

      <Card>
        <CardHeader>
          <CardTitle>{selectionTitle}</CardTitle>
          <CardDescription>Edit selected project or remove it.</CardDescription>
        </CardHeader>
        <CardContent>
          {!selectedProjectId && (
            <p className="rounded-md border border-dashed border-border px-4 py-6 text-sm text-muted-foreground">
              Select a project from the list to view and edit details.
            </p>
          )}

          {selectedProjectId && projectDetailQuery.isLoading && (
            <div className="grid min-h-24 place-items-center">
              <Spinner />
            </div>
          )}

          {detailError && <AlertError message={detailError} />}

          {selectedProject && !detailError && (
            <form
              className="space-y-4"
              onSubmit={updateForm.handleSubmit((values) => {
                updateMutation.mutate({ projectId: selectedProject.id, values })
              })}
            >
              <div className="space-y-1.5">
                <Label htmlFor="updateName">Project name</Label>
                <Input id="updateName" {...updateForm.register('name')} />
                {updateForm.formState.errors.name && <p className="text-xs text-danger">{updateForm.formState.errors.name.message}</p>}
              </div>

              <div className="space-y-1.5">
                <Label htmlFor="updateDescription">Description</Label>
                <Textarea id="updateDescription" {...updateForm.register('description')} />
                {updateForm.formState.errors.description && (
                  <p className="text-xs text-danger">{updateForm.formState.errors.description.message}</p>
                )}
              </div>

              {updateError && <AlertError message={updateError} />}
              {deleteError && <AlertError message={deleteError} />}

              <div className="flex flex-wrap gap-2">
                <Button type="submit" disabled={updateMutation.isPending}>
                  {updateMutation.isPending ? (
                    <Loader2 className="mr-2 h-4 w-4 animate-spin" />
                  ) : (
                    <PencilLine className="mr-2 h-4 w-4" />
                  )}
                  Save changes
                </Button>

                <Button
                  type="button"
                  variant="danger"
                  disabled={deleteMutation.isPending}
                  onClick={() => {
                    const confirmed = window.confirm('Delete this project permanently?')
                    if (confirmed) {
                      deleteMutation.mutate(selectedProject.id)
                    }
                  }}
                >
                  {deleteMutation.isPending ? (
                    <Loader2 className="mr-2 h-4 w-4 animate-spin" />
                  ) : (
                    <Trash2 className="mr-2 h-4 w-4" />
                  )}
                  Delete
                </Button>
              </div>
            </form>
          )}
        </CardContent>
      </Card>
    </div>
  )
}
