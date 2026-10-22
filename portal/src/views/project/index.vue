<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { listProjects } from '../../api/website'
import { getImageUrl } from '../../utils/common'
import { Right, TrendCharts, OfficeBuilding, School, Monitor } from '@element-plus/icons-vue'

const router = useRouter()
const projectList = ref([])
const loading = ref(true)
const activeTab = ref('全部')

// 获取数据
onMounted(async () => {
  loading.value = true
  try {
    const res = await listProjects()
    if (res.rows) {
      // 按 sortOrder 排序
      projectList.value = res.rows.sort((a, b) => a.sortOrder - b.sortOrder)
    }
  } finally {
    loading.value = false
  }
})

// 提取所有行业类型用于筛选
const industries = computed(() => {
  const types = new Set(projectList.value.map(p => p.industryType).filter(t => t))
  return ['全部', ...Array.from(types)]
})

// 过滤列表
const filteredList = computed(() => {
  if (activeTab.value === '全部') return projectList.value
  return projectList.value.filter(item => item.industryType === activeTab.value)
})

const goDetail = (id) => {
  router.push(`/project/${id}`)
}
</script>

<template>
  <div class="bg-white min-h-screen font-sans">
    
    <div class="bg-slate-900 py-24 px-6 relative overflow-hidden">
      <div class="absolute inset-0 bg-[url('https://grainy-gradients.vercel.app/noise.svg')] opacity-20"></div>
      <div class="absolute top-0 right-0 w-[500px] h-[500px] bg-blue-600/20 blur-[120px] rounded-full translate-x-1/2 -translate-y-1/2"></div>
      
      <div class="container mx-auto relative z-10 text-center">
        <span class="text-blue-400 font-bold tracking-widest uppercase text-xs mb-4 block">Success Stories</span>
        <h1 class="text-4xl md:text-6xl font-bold text-white mb-6">
          赋能行业 <span class="text-transparent bg-clip-text bg-gradient-to-r from-blue-400 to-teal-400">数字化转型</span>
        </h1>
        <p class="text-slate-400 max-w-2xl mx-auto text-lg font-light">
          探索我们如何利用 AI 技术帮助客户解决复杂挑战，创造可衡量的商业价值。
        </p>
      </div>
    </div>

    <div class="container mx-auto px-6 py-16">
      
      <div class="flex flex-wrap justify-center gap-4 mb-16">
        <button 
          v-for="tab in industries" 
          :key="tab"
          @click="activeTab = tab"
          class="px-6 py-2 rounded-full text-sm font-bold transition-all duration-300"
          :class="activeTab === tab 
            ? 'bg-slate-900 text-white shadow-lg shadow-slate-900/20' 
            : 'bg-slate-100 text-slate-600 hover:bg-slate-200'"
        >
          {{ tab }}
        </button>
      </div>

      <div v-if="loading" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
        <div v-for="i in 3" :key="i" class="h-[400px] bg-slate-50 rounded-2xl animate-pulse"></div>
      </div>

      <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
        <div 
          v-for="item in filteredList" 
          :key="item.projectId"
          @click="goDetail(item.projectId)"
          class="group cursor-pointer flex flex-col gap-4"
        >
          <div class="relative h-[320px] rounded-[2rem] overflow-hidden shadow-sm group-hover:shadow-2xl transition-all duration-500">
            <img 
              :src="getImageUrl(item.coverImg)" 
              class="w-full h-full object-cover transition-transform duration-700 group-hover:scale-110"
            />
            <div class="absolute inset-0 bg-gradient-to-t from-slate-900/80 via-transparent to-transparent opacity-80 group-hover:opacity-60 transition-opacity"></div>
            
            <div v-if="item.keyDataVal" class="absolute top-6 right-6 bg-white/90 backdrop-blur px-4 py-3 rounded-xl text-center shadow-lg transform group-hover:scale-110 transition-transform duration-300">
              <div class="text-2xl font-bold text-blue-600 leading-none">{{ item.keyDataVal }}</div>
              <div class="text-[10px] font-bold text-slate-500 uppercase mt-1">{{ item.keyDataDesc || '提升' }}</div>
            </div>

            <div class="absolute bottom-0 left-0 w-full p-8 text-white transform translate-y-2 group-hover:translate-y-0 transition-transform duration-300">
              <div class="text-xs font-bold text-blue-300 uppercase tracking-wider mb-2 flex items-center gap-2">
                <span class="w-2 h-2 rounded-full bg-blue-400"></span>
                {{ item.industryType }}
              </div>
              <h3 class="text-2xl font-bold leading-tight mb-2 group-hover:text-blue-200 transition-colors">
                {{ item.projectName }}
              </h3>
              <p class="text-slate-300 text-sm line-clamp-2 opacity-0 group-hover:opacity-100 transition-opacity duration-500 delay-100">
                {{ item.background }}
              </p>
            </div>
          </div>
        </div>
      </div>
      
      <div v-if="!loading && filteredList.length === 0" class="text-center py-20 text-slate-400">
        暂无该分类下的案例数据
      </div>

    </div>
  </div>
</template>