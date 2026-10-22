<script setup>
import { ref, onMounted, computed } from 'vue'
import { listProducts } from '../../api/website'
import { getImageUrl } from '../../utils/common'
import { Search, ArrowRight, Filter, Connection, Picture } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// --- 1. 数据状态 ---
const allProducts = ref([]) 
const loading = ref(true)
const activeCategory = ref('all') 
const searchQuery = ref('') 

// --- 2. 分类配置 ---
const categories = [
  { label: '全部', value: 'all' },
  { label: '智慧城市', value: '1' },
  { label: '工业视觉', value: '2' },
  { label: '智能硬件', value: '3' },
  { label: '算法中台', value: '4' }
]

// --- 3. 跳转逻辑 ---
const goDetail = (id) => {
  console.log('点击跳转 ID:', id); // 调试日志
  if (!id) {
    console.error('ID为空，无法跳转');
    return;
  }
  router.push(`/product/${id}`)
}

// --- 4. 核心过滤逻辑 (注意字段名已修正为 camelCase) ---
const filteredProducts = computed(() => {
  return allProducts.value.filter(item => {
    // A. 分类筛选 (注意：后端返回的是 categoryId)
    const matchCat = activeCategory.value === 'all' || item.categoryId == activeCategory.value
    
    // B. 搜索筛选 (注意：后端返回的是 productName)
    const name = (item.productName || '').toLowerCase()
    const summary = (item.summary || '').toLowerCase()
    const keyword = searchQuery.value.toLowerCase()
    
    const matchSearch = name.includes(keyword) || summary.includes(keyword)
    
    return matchCat && matchSearch
  })
})

// --- 5. 获取后端数据 ---
onMounted(async () => {
  loading.value = true
  try {
    const res = await listProducts()
    console.log('后端返回数据:', res.rows); // 可以在控制台核对数据
    if (res.rows) {
      allProducts.value = res.rows
    }
  } catch (e) {
    console.error('获取产品失败:', e)
  } finally {
    loading.value = false
  }
})

// 辅助函数：根据ID找分类名
const getCategoryLabel = (id) => {
  const cat = categories.find(c => c.value == id)
  return cat ? cat.label : '解决方案'
}

// 图片加载失败处理
const handleImageError = (e) => {
  e.target.style.display = 'none' 
  // 显示紧跟在 img 后面的 fallback div
  const fallback = e.target.nextElementSibling;
  if (fallback) fallback.classList.remove('hidden');
  if (fallback) fallback.classList.add('flex');
}
</script>

<template>
  <div class="bg-slate-50 min-h-screen pb-20 font-sans">
    
    <div class="relative bg-slate-900 pt-32 pb-24 px-6 overflow-hidden">
      <div class="absolute top-0 right-0 w-[600px] h-[600px] bg-blue-600/10 blur-[120px] rounded-full translate-x-1/3 -translate-y-1/3"></div>
      <div class="absolute bottom-0 left-0 w-[400px] h-[400px] bg-teal-500/10 blur-[100px] rounded-full -translate-x-1/3 translate-y-1/3"></div>
      
      <div class="container mx-auto relative z-10 text-center">
        <h1 class="text-4xl md:text-5xl font-bold text-white mb-6 tracking-tight">
          全栈式 <span class="text-transparent bg-clip-text bg-gradient-to-r from-blue-400 to-teal-400">AI 产品矩阵</span>
        </h1>
        <p class="text-slate-400 max-w-2xl mx-auto text-lg leading-relaxed">
          从底层算力硬件，到中层核心算法，再到上层行业应用。我们为您提供端到端的智能化解决方案。
        </p>
      </div>
    </div>

    <div class="container mx-auto px-4 md:px-6 -mt-10 relative z-20">
      <div class="bg-white rounded-2xl shadow-xl shadow-slate-200/50 p-4 md:p-6 border border-slate-100 flex flex-col lg:flex-row justify-between items-center gap-6">
        
        <div class="flex flex-wrap justify-center gap-2">
          <button 
            v-for="cat in categories" 
            :key="cat.value"
            @click="activeCategory = cat.value"
            class="px-6 py-2.5 rounded-full text-sm font-bold transition-all duration-300 border"
            :class="activeCategory === cat.value 
              ? 'bg-blue-600 text-white border-blue-600 shadow-lg shadow-blue-600/30' 
              : 'bg-white text-slate-600 border-slate-200 hover:border-blue-300 hover:text-blue-600'"
          >
            {{ cat.label }}
          </button>
        </div>

        <div class="w-full md:w-80 group">
          <div class="flex items-center w-full bg-slate-50 border border-slate-200 rounded-full px-4 py-2.5 transition-all duration-300 focus-within:bg-white focus-within:ring-2 focus-within:ring-blue-500/20 focus-within:border-blue-500">
            <el-icon class="text-slate-400 mr-3 text-lg group-focus-within:text-blue-500 transition-colors">
              <Search />
            </el-icon>
            <input 
              v-model="searchQuery"
              type="text" 
              placeholder="搜索产品名称或技术..." 
              class="bg-transparent border-none outline-none text-sm text-slate-700 placeholder-slate-400 w-full"
            >
          </div>
        </div>
      </div>
    </div>

    <div class="container mx-auto px-4 md:px-6 py-16">
      
      <div v-if="loading" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
        <div v-for="i in 3" :key="i" class="h-96 bg-white rounded-3xl border border-slate-100 p-4">
          <div class="h-48 bg-slate-100 rounded-2xl animate-pulse mb-4"></div>
          <div class="h-6 w-2/3 bg-slate-100 rounded animate-pulse mb-3"></div>
          <div class="h-4 w-full bg-slate-100 rounded animate-pulse"></div>
        </div>
      </div>

      <div v-else-if="filteredProducts.length > 0" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
        <div 
          v-for="item in filteredProducts" 
          @click="goDetail(item.productId)"
          :key="item.productId"
          class="group bg-white rounded-[2rem] overflow-hidden border border-slate-100 shadow-sm hover:shadow-2xl hover:shadow-blue-900/10 transition-all duration-500 hover:-translate-y-2 cursor-pointer flex flex-col"
        >
          <div class="h-60 overflow-hidden relative bg-slate-100">
            <img 
              :src="getImageUrl(item.mainImg)" 
              @error="handleImageError"
              class="w-full h-full object-cover transition-transform duration-700 group-hover:scale-110 relative z-10"
            />
            
            <div class="hidden absolute inset-0 bg-slate-100 items-center justify-center z-0">
               <el-icon :size="40" class="text-slate-300"><Picture /></el-icon>
            </div>

            <div class="absolute inset-0 bg-gradient-to-t from-slate-900/60 via-transparent to-transparent opacity-60 group-hover:opacity-40 transition-opacity z-20 pointer-events-none"></div>
            
            <div class="absolute top-4 left-4 z-30 bg-white/90 backdrop-blur px-3 py-1 rounded-lg text-xs font-bold text-slate-800 shadow-sm flex items-center gap-1">
              <el-icon><Connection /></el-icon>
              {{ getCategoryLabel(item.categoryId) }}
            </div>
          </div>

          <div class="p-8 flex flex-col flex-grow">
            <h3 class="text-xl font-bold text-slate-900 mb-3 group-hover:text-blue-600 transition-colors line-clamp-1">
              {{ item.productName }}
            </h3>
            <p class="text-slate-500 text-sm leading-relaxed mb-6 line-clamp-3 flex-grow h-[4.5em]">
              {{ item.summary || '暂无产品简介信息...' }}
            </p>
            
            <div class="pt-6 border-t border-slate-50 flex justify-between items-center mt-auto">
              <span class="text-xs font-bold text-slate-400 uppercase tracking-wider group-hover:text-blue-500 transition-colors">View Details</span>
              <button class="w-10 h-10 rounded-full bg-slate-50 flex items-center justify-center text-slate-400 group-hover:bg-blue-600 group-hover:text-white transition-all transform group-hover:rotate-[-45deg]">
                <el-icon><ArrowRight /></el-icon>
              </button>
            </div>
          </div>
        </div>
      </div>

      <div v-else class="text-center py-24">
        <div class="inline-flex items-center justify-center w-24 h-24 bg-slate-100 rounded-full text-slate-300 mb-6">
          <el-icon :size="48"><Filter /></el-icon>
        </div>
        <h3 class="text-xl font-bold text-slate-700 mb-2">未找到相关产品</h3>
        <p class="text-slate-500">尝试切换分类或搜索其他关键词</p>
      </div>

    </div>
  </div>
</template>