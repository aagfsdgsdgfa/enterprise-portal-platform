<script setup>
import { ref, onMounted } from 'vue'
import { listArticles } from '../../api/website' // 确保路径正确
import { getImageUrl } from '../../utils/common' // 确保路径正确
import { Search, ArrowRight, Calendar, Reading } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// --- 状态管理 ---
const newsList = ref([])
const loading = ref(true)
const total = ref(0)

// --- 核心配置 (对应你的数据结构) ---
const queryParams = ref({
  pageNum: 1,
  pageSize: 9, 
  title: '',   
  status: '0', // 只查询发布状态
})

// --- 获取数据 ---
const fetchNews = async () => {
  loading.value = true
  try {
    // 发起请求，若依后端会自动处理 queryParams 中的 pageNum 和 pageSize
    const res = await listArticles(queryParams.value)
    
    if (res.rows) {
      newsList.value = res.rows
      total.value = res.total
    } else {
      newsList.value = []
      total.value = 0
    }
  } catch (e) {
    console.error("加载新闻失败", e)
    newsList.value = []
  } finally {
    // 稍微延迟关闭Loading，让骨架屏不闪烁
    setTimeout(() => { loading.value = false }, 300)
  }
}

// --- 跳转详情 ---
const goDetail = (id) => {
  if(id) router.push(`/news/${id}`)
}

// --- 搜索 ---
const handleSearch = () => {
  queryParams.value.pageNum = 1 // 搜索时重置回第一页
  fetchNews()
}

// --- 【关键操作 2】修复分页逻辑 ---
const handlePageChange = (newPage) => {
  // 1. 修改参数中的页码
  queryParams.value.pageNum = newPage
  // 2. 滚动回顶部，提升体验
  window.scrollTo({ top: 0, behavior: 'smooth' })
  // 3. 重新请求数据
  fetchNews()
}

// --- 工具：日期格式化 ---
// 优先显示 publishTime，如果没有则显示 createTime
const formatDate = (item) => {
  // 取值逻辑：publishTime > createTime
  const dateStr = item.publishTime || item.createTime || ''
  
  if (!dateStr) return { day: '01', month: 'JAN', year: '...' }
  
  // 兼容性处理
  const safeDateStr = dateStr.replace(/-/g, '/')
  const date = new Date(safeDateStr)
  
  if (isNaN(date.getTime())) return { day: '01', month: 'JAN', year: '2025' }

  const months = ['JAN', 'FEB', 'MAR', 'APR', 'MAY', 'JUN', 'JUL', 'AUG', 'SEP', 'OCT', 'NOV', 'DEC']
  return {
    day: date.getDate().toString().padStart(2, '0'),
    month: months[date.getMonth()],
    year: date.getFullYear()
  }
}

// 图片加载失败处理
const handleImgError = (e) => {
  e.target.style.display = 'none'
  const fallback = e.target.nextElementSibling
  if (fallback) fallback.classList.remove('hidden')
}

onMounted(() => {
  fetchNews()
})
</script>

<template>
  <div class="bg-[#F8FAFC] min-h-screen font-sans pb-20">
    
    <!-- 头部搜索区 -->
    <div class="relative bg-white pt-32 pb-16 px-6 shadow-sm border-b border-slate-100">
      <div class="container mx-auto max-w-6xl">
        <div class="flex flex-col md:flex-row justify-between items-end gap-8">
          <div>
            <span class="text-blue-600 font-bold tracking-wider text-sm uppercase mb-2 block">News Center</span>
            <h1 class="text-4xl md:text-5xl font-bold text-slate-900 tracking-tight">
              洞察 & <span class="italic text-slate-400 font-serif">资讯</span>
            </h1>
          </div>
          
          <div class="w-full md:w-80 group">
            <div class="flex items-center bg-slate-50 border border-slate-200 rounded-full px-5 py-3 transition-all duration-300 focus-within:bg-white focus-within:ring-2 focus-within:ring-blue-500/10 focus-within:border-blue-500 hover:border-slate-300">
              <input 
                v-model="queryParams.title"
                @keyup.enter="handleSearch"
                type="text" 
                placeholder="搜索资讯标题..." 
                class="bg-transparent border-none outline-none text-sm text-slate-700 placeholder-slate-400 w-full"
              >
              <button @click="handleSearch" class="ml-2 text-slate-400 hover:text-blue-600 transition-colors">
                <el-icon :size="18"><Search /></el-icon>
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 内容列表区 -->
    <div class="container mx-auto max-w-6xl px-4 md:px-6 py-12">
      
      <!-- Loading 骨架屏 -->
      <div v-if="loading" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
        <div v-for="i in 6" :key="i" class="bg-white rounded-2xl p-4 shadow-sm border border-slate-100">
          <div class="h-56 bg-slate-100 rounded-xl animate-pulse mb-6"></div>
          <div class="h-4 w-1/4 bg-slate-100 rounded animate-pulse mb-4"></div>
          <div class="h-6 w-3/4 bg-slate-100 rounded animate-pulse mb-3"></div>
          <div class="h-16 w-full bg-slate-100 rounded animate-pulse"></div>
        </div>
      </div>

      <!-- 新闻列表 -->
      <div v-else-if="newsList.length > 0" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
        <article 
          v-for="(item, index) in newsList" 
          :key="item.articleId || index"
          @click="goDetail(item.articleId)"
          class="group bg-white rounded-2xl overflow-hidden border border-slate-100 shadow-[0_2px_10px_-4px_rgba(0,0,0,0.05)] hover:shadow-[0_20px_40px_-10px_rgba(0,0,0,0.1)] transition-all duration-500 cursor-pointer flex flex-col hover:-translate-y-1 relative"
        >
          <!-- 【关键操作 3】置顶标签：判断 isTop === '1' -->
          <div v-if="item.isTop === '1'" class="absolute top-4 right-4 z-20 bg-blue-600 text-white text-[10px] font-bold px-2 py-1 rounded shadow-lg uppercase tracking-wider">
            Top Story
          </div>

          <div class="h-60 overflow-hidden relative bg-slate-50">
            <!-- 图片处理：使用 coverImg -->
            <img 
              :src="getImageUrl(item.coverImg)" 
              @error="handleImgError"
              class="w-full h-full object-cover transition-transform duration-700 group-hover:scale-110"
            />
            
            <!-- 图片加载失败时的占位 -->
            <div class="hidden absolute inset-0 bg-slate-100 items-center justify-center flex-col text-slate-300">
              <el-icon :size="32" class="mb-2"><Reading /></el-icon>
              <span class="text-xs font-bold tracking-widest">NOVATECH AI</span>
            </div>
            
            <!-- 日期显示 -->
            <div class="absolute top-4 left-4 bg-white/95 backdrop-blur shadow-sm rounded-lg p-2 text-center min-w-[3.5rem] flex flex-col justify-center z-10">
              <span class="text-xs font-bold text-slate-400 uppercase tracking-wider">{{ formatDate(item).month }}</span>
              <span class="text-xl font-bold text-slate-800 font-serif leading-none mt-0.5">{{ formatDate(item).day }}</span>
            </div>
          </div>

          <div class="p-8 flex flex-col flex-grow">
            <div class="flex items-center gap-2 mb-3">
              <span class="w-1.5 h-1.5 rounded-full bg-blue-500"></span>
              <span class="text-xs font-medium text-slate-400 uppercase tracking-wider">{{ item.author || 'Official' }}</span>
            </div>

            <h3 class="text-xl font-bold text-slate-900 mb-3 leading-snug group-hover:text-blue-600 transition-colors line-clamp-2">
              {{ item.title }}
            </h3>
            
            <p class="text-slate-500 text-sm leading-relaxed mb-6 line-clamp-3 flex-grow">
              {{ item.summary || '暂无简介...' }}
            </p>
            
            <div class="pt-6 border-t border-slate-50 flex items-center justify-between text-sm font-medium">
              <span class="text-slate-400 group-hover:text-slate-600 transition-colors">{{ formatDate(item).year }}</span>
              <div class="flex items-center text-blue-600 opacity-0 -translate-x-2 group-hover:opacity-100 group-hover:translate-x-0 transition-all duration-300">
                阅读全文 <el-icon class="ml-1"><ArrowRight /></el-icon>
              </div>
            </div>
          </div>
        </article>
      </div>

      <!-- 空状态 -->
      <div v-else class="text-center py-32 bg-white rounded-3xl border border-dashed border-slate-200">
        <div class="inline-flex items-center justify-center w-20 h-20 bg-slate-50 rounded-full text-slate-300 mb-4">
          <el-icon :size="40"><Calendar /></el-icon>
        </div>
        <h3 class="text-lg font-medium text-slate-900">暂无相关资讯</h3>
        <p class="text-slate-400 text-sm mt-1">请稍后再来看看吧</p>
      </div>

      <!-- 【关键操作 4】分页组件 -->
      <div v-if="total > 0" class="mt-16 flex justify-center">
        <!-- 必须同时绑定 current-page 和 total -->
        <el-pagination
          background
          layout="prev, pager, next"
          :total="total"
          :page-size="queryParams.pageSize"
          :current-page="queryParams.pageNum"
          @current-change="handlePageChange"
          class="custom-pagination"
        />
      </div>

    </div>
  </div>
</template>

<style scoped>
:deep(.el-pagination.is-background .el-pager li:not(.is-disabled).is-active) {
  background-color: #2563eb; 
}
:deep(.el-pagination.is-background .el-pager li) {
  border-radius: 8px;
  background-color: white;
  border: 1px solid #e2e8f0;
  color: #64748b;
  font-weight: 500;
}
:deep(.el-pagination.is-background .btn-prev),
:deep(.el-pagination.is-background .btn-next) {
  border-radius: 8px;
  background-color: white;
  border: 1px solid #e2e8f0;
}
</style>