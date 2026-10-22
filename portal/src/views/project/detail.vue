<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getProject, listProducts } from '../../api/website'
import { getImageUrl } from '../../utils/common'
import { 
  ArrowLeft, TrendCharts, MagicStick, Trophy, 
  Calendar, UserFilled, Right 
} from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const loading = ref(true)
const project = ref({})
const relatedProducts = ref([])

// 格式化日期
const formatDate = (dateStr) => {
  if(!dateStr) return ''
  return dateStr.split(' ')[0]
}

onMounted(async () => {
  const id = route.params.id
  if (!id) return

  try {
    loading.value = true
    // 1. 获取项目详情
    const res = await getProject(id)
    const data = res.data || res
    if (data) {
      project.value = data
      
      // 2. 获取关联产品 (解析 "1,2,3" 字符串)
      if (project.value.relatedProductIds) {
        const pRes = await listProducts()
        if (pRes.rows) {
          const ids = project.value.relatedProductIds.split(/[,，]/).map(s => Number(s.trim()))
          relatedProducts.value = pRes.rows.filter(p => ids.includes(p.productId))
        }
      }
    }
  } catch (e) {
    console.error(e)
  } finally {
    loading.value = false
  }
})

const goBack = () => router.push('/projects')
const goProduct = (id) => router.push(`/product/${id}`)
</script>

<template>
  <div class="bg-white min-h-screen font-sans pb-24">
    
    <div v-if="loading" class="h-screen flex items-center justify-center">
      <div class="w-10 h-10 border-4 border-blue-600 border-t-transparent rounded-full animate-spin"></div>
    </div>

    <div v-else class="animate-fade-in">
      
      <div class="relative h-[60vh] min-h-[500px] w-full bg-slate-900 overflow-hidden">
        <img 
          :src="getImageUrl(project.detailBanner || project.coverImg)" 
          class="absolute inset-0 w-full h-full object-cover opacity-50 scale-105"
        />
        <div class="absolute inset-0 bg-gradient-to-t from-slate-900 via-slate-900/40 to-slate-900/30"></div>
        
        <div class="container mx-auto px-6 h-full flex flex-col justify-end pb-20 relative z-10">
          <div class="absolute top-10 left-6">
            <button 
              @click="goBack" 
              class="group flex items-center gap-2 px-5 py-2.5 bg-white/10 hover:bg-white/20 border border-white/10 backdrop-blur-md rounded-full text-white text-sm font-medium transition-all duration-300"
            >
              <el-icon class="group-hover:-translate-x-1 transition-transform"><ArrowLeft /></el-icon>
              返回案例列表
            </button>
          </div>

          <div class="max-w-4xl">
             <div class="flex items-center gap-3 mb-6">
                <span class="px-3 py-1 bg-blue-600 text-white text-xs font-bold uppercase tracking-wider rounded-md">
                  {{ project.industryType }}
                </span>
                <span class="text-slate-300 text-sm border-l border-slate-500 pl-3 flex items-center gap-2">
                   <el-icon><Calendar /></el-icon> 完工: {{ formatDate(project.completionDate) }}
                </span>
             </div>
             
             <h1 class="text-4xl md:text-6xl font-bold text-white mb-6 leading-tight">
               {{ project.projectName }}
             </h1>
             
             <div class="flex items-center gap-2 text-xl text-slate-300">
               <el-icon><UserFilled /></el-icon> 客户：<span class="font-bold text-white">{{ project.clientName }}</span>
             </div>
          </div>
        </div>
      </div>

      <div v-if="project.keyDataVal" class="bg-blue-600 text-white py-12 relative overflow-hidden">
        <div class="absolute inset-0 bg-[url('https://grainy-gradients.vercel.app/noise.svg')] opacity-20"></div>
        <div class="container mx-auto px-6 relative z-10 flex flex-col md:flex-row items-center justify-between gap-8">
           <div class="md:w-1/2">
             <h3 class="text-xl font-medium opacity-90 mb-2">PROJECT IMPACT</h3>
             <p class="text-3xl font-bold">项目核心价值与成果数据</p>
           </div>
           <div class="md:w-1/2 flex items-center gap-6 bg-white/10 p-6 rounded-2xl border border-white/10 backdrop-blur-sm">
              <div class="w-16 h-16 bg-white text-blue-600 rounded-full flex items-center justify-center text-3xl font-bold shrink-0">
                <el-icon><TrendCharts /></el-icon>
              </div>
              <div>
                <div class="text-5xl font-bold font-mono tracking-tight">{{ project.keyDataVal }}</div>
                <div class="text-blue-100 font-medium text-lg">{{ project.keyDataDesc }}</div>
              </div>
           </div>
        </div>
      </div>

      <div class="container mx-auto px-6 py-20 max-w-5xl">
        
        <div class="flex flex-col md:flex-row gap-12 mb-20 group">
          <div class="md:w-1/4 shrink-0">
            <div class="sticky top-24">
              <div class="w-12 h-12 bg-slate-100 text-slate-900 rounded-xl flex items-center justify-center text-2xl mb-4 group-hover:bg-slate-900 group-hover:text-white transition-colors">
                01
              </div>
              <h3 class="text-2xl font-bold text-slate-900">项目背景</h3>
              <div class="h-1 w-10 bg-slate-900 mt-4"></div>
            </div>
          </div>
          <div class="md:w-3/4">
             <p class="text-lg text-slate-600 leading-8 whitespace-pre-line">
               {{ project.background || '暂无背景描述...' }}
             </p>
          </div>
        </div>

        <div class="flex flex-col md:flex-row gap-12 mb-20 group">
          <div class="md:w-1/4 shrink-0">
            <div class="sticky top-24">
              <div class="w-12 h-12 bg-blue-50 text-blue-600 rounded-xl flex items-center justify-center text-2xl mb-4 group-hover:bg-blue-600 group-hover:text-white transition-colors">
                <el-icon><MagicStick /></el-icon>
              </div>
              <h3 class="text-2xl font-bold text-slate-900">解决方案</h3>
              <div class="h-1 w-10 bg-blue-600 mt-4"></div>
            </div>
          </div>
          <div class="md:w-3/4 bg-slate-50 p-8 rounded-3xl border border-slate-100">
             <p class="text-lg text-slate-700 leading-8 font-medium whitespace-pre-line">
               {{ project.solutionDesc || '暂无方案描述...' }}
             </p>
          </div>
        </div>

        <div class="flex flex-col md:flex-row gap-12 mb-20 group">
          <div class="md:w-1/4 shrink-0">
            <div class="sticky top-24">
              <div class="w-12 h-12 bg-teal-50 text-teal-600 rounded-xl flex items-center justify-center text-2xl mb-4 group-hover:bg-teal-600 group-hover:text-white transition-colors">
                <el-icon><Trophy /></el-icon>
              </div>
              <h3 class="text-2xl font-bold text-slate-900">建设成果</h3>
              <div class="h-1 w-10 bg-teal-600 mt-4"></div>
            </div>
          </div>
          <div class="md:w-3/4">
             <p class="text-lg text-slate-600 leading-8 whitespace-pre-line">
               {{ project.achievement || '暂无成果描述...' }}
             </p>
          </div>
        </div>

      </div>

      <div v-if="relatedProducts.length > 0" class="bg-slate-50 py-20 border-t border-slate-200">
        <div class="container mx-auto px-6 max-w-6xl">
          <div class="text-center mb-12">
            <h2 class="text-3xl font-bold text-slate-900">本项目应用产品</h2>
            <div class="w-12 h-1 bg-blue-600 mx-auto mt-4 rounded-full"></div>
          </div>

          <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
            <div 
              v-for="prod in relatedProducts" 
              :key="prod.productId"
              @click="goProduct(prod.productId)"
              class="bg-white p-6 rounded-2xl border border-slate-100 shadow-sm hover:shadow-xl hover:-translate-y-1 transition-all cursor-pointer flex flex-col items-center text-center group"
            >
              <div class="w-24 h-24 mb-6 rounded-full bg-slate-50 border border-slate-100 p-2 group-hover:scale-110 transition-transform duration-500">
                 <img :src="getImageUrl(prod.mainImg)" class="w-full h-full object-cover rounded-full" />
              </div>
              <h3 class="text-xl font-bold text-slate-900 mb-2 group-hover:text-blue-600 transition-colors">{{ prod.productName }}</h3>
              <p class="text-sm text-slate-500 line-clamp-2 mb-4">{{ prod.summary }}</p>
              <div class="mt-auto text-blue-600 font-bold text-sm flex items-center gap-1 opacity-0 group-hover:opacity-100 transition-opacity">
                查看详情 <el-icon><Right /></el-icon>
              </div>
            </div>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>

<style scoped>
.animate-fade-in {
  animation: fadeIn 0.8s ease-out;
}
@keyframes fadeIn {
  from { opacity: 0; transform: translateY(20px); }
  to { opacity: 1; transform: translateY(0); }
}
</style>