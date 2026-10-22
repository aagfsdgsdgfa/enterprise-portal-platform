import { createApp } from 'vue'
import './style.css' // 或者 './assets/main.css'，这是你的全局样式
import App from './App.vue'

// 1. 引入刚刚写的路由配置
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

const app = createApp(App)

// 2. 使用路由
app.use(router)
app.use(ElementPlus)

app.mount('#app')