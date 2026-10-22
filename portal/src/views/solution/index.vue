<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { listSolutions } from '../../api/website'
import { 
  Monitor, DataAnalysis, OfficeBuilding, Box, 
  Cpu, Connection, Van, School
} from '@element-plus/icons-vue'

const router = useRouter()
const list = ref([])
const loading = ref(true)

// 图标映射字典 (根据你数据库存的字符串匹配)
const iconMap = {
  'el-icon-monitor': Monitor,
  'el-icon-data-analysis': DataAnalysis,
  'el-icon-office-building': OfficeBuilding,
  'el-icon-box': Box,
  'el-icon-cpu': Cpu,
  'el-icon-connection': Connection,
  'el-icon-van': Van,
  'el-icon-school': School,
  'default': Box
}

const getIcon = (name) => iconMap[name] || iconMap['default']

const fetchData = async () => {
  loading.value = true
  try {
    const res = await listSolutions()
    if (res.rows) {
      // 按照 sortOrder 排序
      list.value = res.rows.sort((a, b) => a.sortOrder - b.sortOrder)
    }
  } finally {
    loading.value = false
  }
}

const goDetail = (id) => {
  router.push(`/solution/${id}`)
}

onMounted(() => {
  fetchData()
})
</script>

<template>
  <div class="bg-slate-50 min-h-screen font-sans">
    
    <div class="relative bg-slate-900 pt-32 pb-24 px-6 overflow-hidden">
      <div class="absolute top-0 right-0 w-[600px] h-[600px] bg-indigo-600/20 blur-[120px] rounded-full translate-x-1/3 -translate-y-1/3"></div>
      <div class="absolute bottom-0 left-0 w-[500px] h-[500px] bg-teal-500/10 blur-[100px] rounded-full -translate-x-1/3 translate-y-1/3"></div>
      
      <div class="container mx-auto relative z-10 text-center">
        <span class="inline-block py-1 px-3 rounded-full bg-white/10 border border-white/20 text-indigo-300 text-xs font-bold tracking-widest uppercase mb-6 backdrop-blur-md">
          Industries & Scenarios
        </span>
        <h1 class="text-4xl md:text-6xl font-bold text-white mb-6 tracking-tight leading-tight">
          全场景 <span class="text-transparent bg-clip-text bg-gradient-to-r from-indigo-400 to-teal-400">AI 赋能方案</span>
        </h1>
        <p class="text-slate-400 max-w-2xl mx-auto text-lg leading-relaxed">
          深入行业痛点，融合前沿算法与边缘计算硬件，为不同领域提供端到端的智能化升级路径。
        </p>
      </div>
    </div>

    <div class="container mx-auto px-6 -mt-10 relative z-20 pb-20">
      
      <div v-if="loading" class="flex justify-center py-20">
        <div class="w-10 h-10 border-4 border-indigo-600 border-t-transparent rounded-full animate-spin"></div>
      </div>

      <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
        <div 
          v-for="item in list" 
          :key="item.solutionId"
          @click="goDetail(item.solutionId)"
          class="group bg-white rounded-3xl p-8 shadow-sm border border-slate-100 hover:shadow-2xl hover:border-indigo-100 transition-all duration-300 cursor-pointer hover:-translate-y-2 relative overflow-hidden"
        >
          <div class="absolute top-0 left-0 w-full h-1.5 bg-gradient-to-r from-indigo-500 to-teal-400 scale-x-0 group-hover:scale-x-100 transition-transform duration-500 origin-left"></div>
          
          <div class="w-16 h-16 bg-slate-50 rounded-2xl flex items-center justify-center text-slate-700 mb-6 group-hover:bg-indigo-600 group-hover:text-white transition-all duration-300 shadow-sm group-hover:shadow-indigo-500/30">
            <el-icon :size="32">
              <component :is="getIcon(item.icon)" />
            </el-icon>
          </div>
          
          <h3 class="text-2xl font-bold text-slate-900 mb-3 group-hover:text-indigo-600 transition-colors">
            {{ item.title }}
          </h3>
          
          <p class="text-slate-500 leading-relaxed mb-6 line-clamp-3 h-[4.5em]">
            {{ item.summary }}
          </p>
          
          <div class="flex items-center text-sm font-bold text-indigo-600 opacity-0 transform translate-y-2 group-hover:opacity-100 group-hover:translate-y-0 transition-all duration-300">
            查看方案详情 <span class="ml-2">→</span>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>