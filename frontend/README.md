# Frontend Run Guide

This frontend is a React + TypeScript + Vite single-page application for the task management backend.

## Prerequisites

- Node.js 20.19.0 or newer is recommended for this toolchain.
- npm is the detected package manager for this project.
- Git is required to clone and update the repository.

The frontend dependencies and build tooling are aligned with Node 20.19+ or Node 22.12+.

## Project Overview

- Framework: React 19
- Language: TypeScript
- Build tool: Vite
- UI styling: Tailwind CSS
- API client: Axios

## Installation

1. Clone the repository.
2. Move into the frontend directory.
3. Install dependencies with npm:

```bash
npm install
```

If you use a different package manager, install with the matching lockfile workflow only. This project currently ships with `package-lock.json`, so npm is the default choice.

## Environment Variables

The frontend currently reads one environment variable:

- `VITE_API_BASE_URL`: Base URL for the backend API.

If this variable is not set, the app falls back to `http://localhost:8080`.

Example `.env` file:

```env
VITE_API_BASE_URL=http://localhost:8080
```

If your backend runs on a different host or port, update this value to match the backend server.

## Available Scripts

The detected scripts from `package.json` are:

- `npm run dev`: start the Vite development server.
- `npm run build`: type-check and build the production bundle.
- `npm run preview`: preview the production build locally.
- `npm run lint`: run Oxlint.

## Start Development Server

Run the development server from the `frontend` directory:

```bash
npm run dev
```

By default, Vite starts on port `5173`, so the app is usually available at:

```text
http://localhost:5173
```

Vite provides hot reload, so changes you save in the frontend source code update the browser automatically.

The frontend expects the backend to be available at the configured API base URL. If the backend is not running, login, profile, and project requests will fail.

## Production Build

Create a production build with:

```bash
npm run build
```

After the build completes, preview the generated output with:

```bash
npm run preview
```

The preview server uses Vite's default preview port, which is usually `4173`.

## Troubleshooting

### Missing package

If the app fails because a dependency is missing, run `npm install` again from the `frontend` directory.

### Wrong Node version

If you see syntax or install errors, check your Node.js version. Use Node.js 20.19.0+ or 22.12.0+ for the smoothest setup.

### Backend unavailable

If the backend is stopped or the API URL is incorrect, authentication, profile, and project pages will show request errors.

Make sure the backend is running and `VITE_API_BASE_URL` points to the correct server.

### CORS

If browser requests fail even though the backend is running, the backend may not allow requests from the frontend origin.

The typical local frontend origin is `http://localhost:5173`.

### Port already in use

If port `5173` is already taken, Vite may choose another port or prompt you to use a different one.

Stop the conflicting process or run the dev server on the suggested port.

## Notes

- This frontend is designed to consume the backend APIs already implemented in the repository.
- Task pages are not included yet because the backend task module is not implemented in code.
