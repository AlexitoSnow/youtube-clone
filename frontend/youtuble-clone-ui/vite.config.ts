import { defineConfig } from 'vite';
import { config } from 'dotenv';

// Load environment variables
config();

export default defineConfig({
  define: {
    'process.env': {
      AUTHORITY: process.env['AUTHORITY'],
      AUTH_CLIENT_ID: process.env['AUTH_CLIENT_ID'],
    },
  },
});
