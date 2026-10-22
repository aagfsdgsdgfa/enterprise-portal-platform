import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  server: {
    port: 80, 
    open: true,
    proxy: {
      // 只要是 /dev-api 开头的请求，都转给若依后端
      '/dev-api': {
        target: 'http://localhost:8080', // 你的若依后端地址
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/dev-api/, '')
      }
    }
  }
})