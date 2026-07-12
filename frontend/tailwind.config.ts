import type { Config } from 'tailwindcss'

const config: Config = {
  darkMode: ['class'],
  content: ['./index.html', './src/**/*.{ts,tsx}'],
  theme: {
    extend: {
      colors: {
        background: '#f6f8fc',
        foreground: '#0f172a',
        card: '#ffffff',
        'card-foreground': '#0f172a',
        primary: '#0b5fff',
        'primary-foreground': '#ffffff',
        muted: '#eef2ff',
        'muted-foreground': '#475569',
        border: '#dbe3f3',
        ring: '#0b5fff',
        danger: '#dc2626',
      },
      borderRadius: {
        lg: '0.85rem',
        md: '0.65rem',
        sm: '0.45rem',
      },
      boxShadow: {
        soft: '0 10px 30px rgba(2, 6, 23, 0.08)',
      },
      keyframes: {
        'fade-in': {
          from: { opacity: '0', transform: 'translateY(8px)' },
          to: { opacity: '1', transform: 'translateY(0)' },
        },
      },
      animation: {
        'fade-in': 'fade-in 320ms ease-out',
      },
    },
  },
  plugins: [],
}

export default config
