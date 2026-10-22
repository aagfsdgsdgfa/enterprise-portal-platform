<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getJob } from '../../api/website'
import { ArrowLeft, Location, Briefcase, User, Message } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const job = ref({})
const loading = ref(true)

onMounted(async () => {
  const id = route.params.id
  if (!id) return
  loading.value = true
  try {
    const res = await getJob(id)
    if (res.data) job.value = res.data
  } finally {
    loading.value = false
  }
})

const goBack = () => router.push('/jobs')
</script>

<template>
  <div class="bg-white min-h-screen font-sans pb-20">
    
    <div v-if="loading" class="h-screen flex items-center justify-center">
      <div class="w-10 h-10 border-4 border-blue-600 border-t-transparent rounded-full animate-spin"></div>
    </div>

    <div v-else>
      <div class="bg-slate-900 text-white pt-32 pb-16 px-6">
        <div class="container mx-auto max-w-4xl">
          <button @click="goBack" class="flex items-center gap-2 text-slate-400 hover:text-white mb-8 transition-colors text-sm font-bold">
            <el-icon><ArrowLeft /></el-icon> 返回职位列表
          </button>
          
          <div class="flex flex-col md:flex-row justify-between items-start md:items-center gap-6">
            <div>
              <div class="flex items-center gap-3 mb-4">
                 <span class="px-3 py-1 bg-blue-600 rounded-lg text-xs font-bold">{{ job.department }}</span>
                 <span v-if="job.isUrgent === '1'" class="px-3 py-1 bg-red-500 rounded-lg text-xs font-bold">急聘</span>
              </div>
              <h1 class="text-4xl font-bold mb-4">{{ job.jobTitle }}</h1>
              <div class="flex flex-wrap gap-6 text-slate-300 text-sm">
                 <span class="flex items-center gap-2"><el-icon><Location /></el-icon> {{ job.workLocation }}</span>
                 <span class="flex items-center gap-2"><el-icon><Briefcase /></el-icon> {{ job.experience }}</span>
                 <span class="flex items-center gap-2"><el-icon><User /></el-icon> {{ job.education }}</span>
              </div>
            </div>
            <div class="text-right">
               <div class="text-3xl font-black text-blue-400 mb-2">{{ job.salaryRange }}</div>
               <div class="text-slate-400 text-sm">13-15薪</div>
            </div>
          </div>
        </div>
      </div>

      <div class="container mx-auto max-w-4xl px-6 py-12 flex flex-col md:flex-row gap-12">
        
        <div class="md:w-2/3">
          <div class="mb-12">
            <h3 class="text-xl font-bold text-slate-900 mb-6 border-l-4 border-blue-600 pl-4">岗位职责</h3>
            <div class="text-slate-600 leading-8 whitespace-pre-wrap">{{ job.jobDesc }}</div>
          </div>

          <div class="mb-12">
            <h3 class="text-xl font-bold text-slate-900 mb-6 border-l-4 border-blue-600 pl-4">任职要求</h3>
            <div class="text-slate-600 leading-8 whitespace-pre-wrap">{{ job.jobRequire }}</div>
          </div>
        </div>

        <div class="md:w-1/3">
           <div class="bg-slate-50 rounded-2xl p-8 border border-slate-100 sticky top-24">
              <h3 class="font-bold text-slate-900 mb-6">申请该职位</h3>
              <p class="text-sm text-slate-500 mb-6">
                请将简历发送至我们的招聘邮箱，邮件标题格式：<br/>
                <span class="text-slate-900 font-bold">应聘-{{ job.jobTitle }}-姓名</span>
              </p>
              
              <a href="mailto:hr@novatech.example.com" class="flex items-center justify-center gap-2 w-full py-3 bg-blue-600 hover:bg-blue-500 text-white rounded-xl font-bold transition-all shadow-lg shadow-blue-200">
                <el-icon><Message /></el-icon> 发送简历
              </a>
              
              <div class="mt-6 pt-6 border-t border-slate-200 text-center">
                 <div class="text-xs text-slate-400 mb-1">HR 联系电话</div>
                 <div class="text-lg font-bold text-slate-700">13800138000</div>
              </div>
           </div>
        </div>

      </div>

    </div>
  </div>
</template>