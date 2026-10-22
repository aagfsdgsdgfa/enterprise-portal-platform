<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { listJobs } from '../../api/website'
import { Briefcase, Location, Search, ArrowRight } from '@element-plus/icons-vue'

const router = useRouter()
const jobList = ref([])
const loading = ref(true)
const activeTab = ref('全部') // 筛选状态
const searchQuery = ref('')   // 搜索框

// 职位类型映射 (根据你的 BizJob 定义：1研发 2市场 3职能)
// 也可以根据 department 来筛选，这里假设用 department 筛选更直观
const departments = computed(() => {
  const depts = new Set(jobList.value.map(j => j.department).filter(d => d))
  return ['全部', ...Array.from(depts)]
})

// 获取数据
const fetchData = async () => {
  loading.value = true
  try {
    const res = await listJobs({ status: '0' }) // 只查招聘中的
    if (res.rows) {
      jobList.value = res.rows
    }
  } finally {
    loading.value = false
  }
}

// 过滤逻辑
const filteredJobs = computed(() => {
  return jobList.value.filter(job => {
    const matchDept = activeTab.value === '全部' || job.department === activeTab.value
    const matchSearch = !searchQuery.value || 
      job.jobTitle.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
      job.workLocation.includes(searchQuery.value)
    return matchDept && matchSearch
  })
})

const goDetail = (id) => router.push(`/job/${id}`)

onMounted(() => {
  fetchData()
})
</script>

<template>
  <div class="bg-slate-50 min-h-screen font-sans pb-20">
    
    <div class="bg-slate-900 py-24 relative overflow-hidden">
      <div class="absolute inset-0 bg-[url('https://grainy-gradients.vercel.app/noise.svg')] opacity-20"></div>
      <div class="absolute top-0 right-0 w-[600px] h-[600px] bg-blue-600/20 blur-[120px] rounded-full translate-x-1/2 -translate-y-1/2"></div>
      
      <div class="container mx-auto px-6 relative z-10 text-center">
        <h1 class="text-4xl md:text-6xl font-bold text-white mb-6">
          加入 <span class="text-blue-500">NovaTech AI</span>
        </h1>
        <p class="text-slate-400 text-xl max-w-2xl mx-auto">
          与一群有梦想的人，做一件改变未来的事。
        </p>
      </div>
    </div>

    <div class="container mx-auto px-6 -mt-8 relative z-20">
      
      <div class="bg-white rounded-2xl shadow-xl p-4 md:p-6 mb-12 flex flex-col md:flex-row gap-6 items-center justify-between">
        
        <div class="flex flex-wrap gap-2 justify-center md:justify-start">
          <button 
             v-for="dept in departments" 
             :key="dept"
             @click="activeTab = dept"
             class="px-5 py-2 rounded-full text-sm font-bold transition-all"
             :class="activeTab === dept ? 'bg-slate-900 text-white' : 'bg-slate-100 text-slate-600 hover:bg-slate-200'"
          >
            {{ dept }}
          </button>
        </div>

        <div class="relative w-full md:w-80">
           <input 
             v-model="searchQuery" 
             type="text" 
             placeholder="搜索职位名称或地点..."
             class="w-full bg-slate-50 border-none rounded-full py-3 pl-10 pr-4 outline-none focus:ring-2 focus:ring-blue-500/20 transition-all placeholder:text-slate-400"
           >
        </div>
      </div>

      <div v-if="loading" class="space-y-4">
        <div v-for="i in 4" :key="i" class="h-32 bg-white rounded-2xl animate-pulse"></div>
      </div>

      <div v-else class="space-y-4">
        <div 
          v-for="job in filteredJobs" 
          :key="job.jobId"
          @click="goDetail(job.jobId)"
          class="group bg-white p-6 md:p-8 rounded-3xl border border-slate-100 hover:border-blue-200 hover:shadow-xl hover:-translate-y-1 transition-all cursor-pointer flex flex-col md:flex-row items-start md:items-center justify-between gap-6"
        >
          <div class="flex-grow">
            <div class="flex items-center gap-3 mb-2">
              <h3 class="text-xl font-bold text-slate-900 group-hover:text-blue-600 transition-colors">{{ job.jobTitle }}</h3>
              <span v-if="job.isUrgent === '1'" class="px-2 py-0.5 bg-red-50 text-red-600 text-xs font-bold rounded">急聘</span>
            </div>
            
            <div class="flex flex-wrap gap-4 text-sm text-slate-500">
               <span class="flex items-center gap-1"><el-icon><Briefcase /></el-icon> {{ job.department }}</span>
               <span class="flex items-center gap-1"><el-icon><Location /></el-icon> {{ job.workLocation }}</span>
               <span class="px-2 py-0.5 bg-slate-100 rounded text-xs">{{ job.experience }}</span>
               <span class="px-2 py-0.5 bg-slate-100 rounded text-xs">{{ job.education }}</span>
            </div>
          </div>

          <div class="flex items-center justify-between w-full md:w-auto md:gap-8">
            <div class="text-2xl font-black text-blue-600">{{ job.salaryRange }}</div>
            <div class="w-10 h-10 rounded-full bg-slate-50 flex items-center justify-center text-slate-400 group-hover:bg-blue-600 group-hover:text-white transition-all">
               <el-icon><ArrowRight /></el-icon>
            </div>
          </div>
        </div>

        <div v-if="filteredJobs.length === 0" class="text-center py-20 text-slate-400">
          没有找到匹配的职位
        </div>
      </div>

    </div>
  </div>
</template>