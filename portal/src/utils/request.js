import axios from 'axios'

// // 1. 创建 axios 实例
// const service = axios.create({
//   // 注意：这里用 /dev-api 触发 vite 的代理
//   baseURL: '/dev-api', 
//   timeout: 10000
// })

const service = axios.create({
  baseURL: import.meta.env.VITE_APP_BASE_API, 
  timeout: 10000
})

// ==========================================================
// 2. 新增：请求拦截器 (核心修改！)
// ==========================================================
service.interceptors.request.use(
  config => {
    // 【关键一步】
    // 这是一个门户网站，面向普通访客，不需要携带 Token。
    // 如果浏览器里缓存了旧的 Token，后端会尝试解析并报错。
    // 所以这里我们要强制删除 Authorization 头，确保是“纯净”的请求。
    if (config.headers) {
      delete config.headers['Authorization']
      config.headers['Authorization'] = '' // 双重保险
    }
    return config
  },
  error => {
    console.error(error)
    return Promise.reject(error)
  }
)

// ==========================================================
// 3. 响应拦截器 (保持逻辑，增加了二进制文件下载的兼容)
// ==========================================================
service.interceptors.response.use(
  res => {
    // 兼容处理：如果是下载文件(blob)，没有 code，直接返回
    if (res.request.responseType ===  'blob' || res.request.responseType ===  'arraybuffer') {
      return res.data
    }

    // 未设置状态码则默认成功状态
    const code = res.data.code || 200;
    // 获取错误信息
    const msg = res.data.msg || '系统未知错误';

    // 若依的标准成功代码是 200
    if (code === 200) {
      return res.data; 
    } else {
      console.error('Err:', msg);
      return Promise.reject(new Error(msg));
    }
  },
  error => {
    console.error('NetErr:', error);
    return Promise.reject(error);
  }
)

export default service