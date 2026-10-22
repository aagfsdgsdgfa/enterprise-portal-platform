// src/utils/common.js

export function getImageUrl(path) {
  // 1. 空值检查
  if (!path) return 'https://via.placeholder.com/300x200?text=No+Image'

  // 2. 如果已经是网络图片，直接返回
  if (path.startsWith('http') || path.startsWith('https')) {
    return path
  }

  // 3. 【核心修改】智能获取 BaseURL
  // 开发环境(npm run dev) -> 走本地 8080
  // 生产环境(npm run build) -> 走 /prod-api 代理
  let baseURL = ''
  if (import.meta.env.MODE === 'development') {
    baseURL = 'http://localhost:8080' 
  } else {
    baseURL = import.meta.env.VITE_APP_BASE_API // 即 '/prod-api'
  }

  // 4. 补全斜杠
  const cleanPath = path.startsWith('/') ? path : '/' + path

  // 5. 返回
  return `${baseURL}${cleanPath}`
}