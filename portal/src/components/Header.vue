<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { Menu, Close } from '@element-plus/icons-vue'

const router = useRouter()

// --- 状态管理 ---
const isMobileMenuOpen = ref(false)

const navItems = [
  { name: '首页', path: '/' },
  { name: '解决方案', path: '/solutions' },
  { name: '产品中心', path: '/products' },
  { name: '成功案例', path: '/project' }, 
  { name: '新闻资讯', path: '/news' },
  { name: '加入我们', path: '/jobs' },
  { name: '关于我们', path: '/about' }
]

// 切换移动端菜单
const toggleMobileMenu = () => {
  isMobileMenuOpen.value = !isMobileMenuOpen.value
  // 控制背景滚动：菜单打开时禁止body滚动
  if (isMobileMenuOpen.value) {
    document.body.style.overflow = 'hidden'
  } else {
    document.body.style.overflow = ''
  }
}

// 跳转联系我们
const goContact = () => {
  isMobileMenuOpen.value = false
  document.body.style.overflow = ''
  router.push('/contact')
}

// 移动端点击菜单项后关闭菜单
const handleMobileNavClick = () => {
  isMobileMenuOpen.value = false
  document.body.style.overflow = ''
}

// 清理工作
onUnmounted(() => {
  document.body.style.overflow = ''
})
</script>

<template>
  <header 
    class="fixed top-0 left-0 w-full z-50 transition-all duration-300 bg-white border-b border-slate-100 shadow-sm"
  >
    <div class="container mx-auto px-4 md:px-8 lg:px-12 h-16 md:h-20 flex justify-between items-center">
      
      <div class="relative z-50 flex items-center cursor-pointer select-none">
        <router-link to="/" class="flex items-center gap-1.5 text-2xl font-black tracking-tight" @click="isMobileMenuOpen = false">
          <span class="text-slate-900">NOVATECH</span>
          <span class="text-blue-600">AI</span>
        </router-link>
      </div>

      <nav class="hidden lg:flex items-center gap-8 font-medium">
        <router-link 
          v-for="item in navItems" 
          :key="item.name" 
          :to="item.path" 
          class="relative py-2 text-[15px] text-slate-600 transition-colors duration-300 hover:text-blue-600 group"
        >
          {{ item.name }}
          <span class="absolute bottom-0 left-1/2 w-0 h-0.5 bg-blue-600 transition-all duration-300 group-hover:w-full group-hover:left-0 rounded-full"></span>
        </router-link>
      </nav>

      <div class="hidden lg:flex items-center gap-6">
        <button class="text-sm font-bold text-slate-500 hover:text-blue-600 transition-colors uppercase tracking-widest">
          EN
        </button>
        <button 
          @click="goContact"
          class="px-6 py-2.5 rounded-full text-sm font-bold bg-blue-600 text-white shadow-lg shadow-blue-600/20 hover:bg-blue-700 hover:shadow-blue-600/40 hover:-translate-y-0.5 transition-all duration-300 active:scale-95"
        >
          联系合作
        </button>
      </div>

      <button 
        class="lg:hidden relative z-50 p-2 -mr-2 text-slate-900 transition-transform active:scale-90"
        @click="toggleMobileMenu"
      >
        <el-icon :size="26">
          <Close v-if="isMobileMenuOpen" />
          <Menu v-else />
        </el-icon>
      </button>
    </div>

    <div 
      class="fixed inset-0 bg-white z-40 flex flex-col pt-24 pb-10 px-6 overflow-y-auto transition-all duration-300 lg:hidden"
      :class="isMobileMenuOpen ? 'opacity-100 translate-x-0' : 'opacity-0 translate-x-full pointer-events-none'"
    >
      <div class="absolute top-0 right-0 w-64 h-64 bg-blue-50 rounded-full blur-3xl opacity-60 -z-10"></div>
      
      <nav class="flex flex-col gap-6 text-lg font-bold text-slate-800">
        <router-link 
          v-for="(item, index) in navItems" 
          :key="item.name" 
          :to="item.path" 
          @click="handleMobileNavClick"
          class="flex items-center justify-between border-b border-slate-50 pb-4 transition-colors hover:text-blue-600"
          :style="{ transitionDelay: `${index * 50}ms` }"
        >
          <span>{{ item.name }}</span>
          <span class="text-slate-300 text-sm">➜</span>
        </router-link>
      </nav>

      <div class="mt-auto flex flex-col gap-4">
        <button 
          @click="goContact"
          class="w-full py-3.5 rounded-xl bg-blue-600 text-white font-bold shadow-lg shadow-blue-600/20 active:scale-95 transition-transform"
        >
          立即咨询
        </button>
        <button class="w-full py-3.5 rounded-xl bg-slate-50 text-slate-600 font-bold hover:bg-slate-100 transition-colors">
          English / EN
        </button>
      </div>
    </div>
  </header>
  <div class="h-16 md:h-20"></div>
</template>

<style scoped>
/* 路由激活状态：PC端显示蓝色，且底部线条展开 */
.router-link-active {
  color: #2563eb; /* blue-600 */
}

.router-link-active span {
  width: 100%;
  left: 0;
}

/* 移动端菜单激活状态 */
.lg\:hidden .router-link-active {
  color: #2563eb;
  border-bottom-color: #eff6ff; /* blue-50 */
}
</style>