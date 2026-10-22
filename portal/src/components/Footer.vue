<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { listCompanyInfo, listSolutions } from '../api/website' // 引入API
import { getImageUrl } from '../utils/common'
import { Location, Phone, Message } from '@element-plus/icons-vue'

const router = useRouter()

// --- 状态管理 ---
const companyInfo = ref({}) // 公司信息
const solutionList = ref([]) // 解决方案列表

// --- 获取数据 ---
onMounted(async () => {
  try {
    // 并行请求，提高速度
    const [infoRes, solRes] = await Promise.all([
      listCompanyInfo(),
      listSolutions({ pageNum: 1, pageSize: 5 }) // 只取前5个方案
    ])

    // 处理公司信息
    if (infoRes.rows && infoRes.rows.length > 0) {
      companyInfo.value = infoRes.rows[0]
    }

    // 处理解决方案
    if (solRes.rows) {
      solutionList.value = solRes.rows
    }
  } catch (e) {
    console.error('Footer数据加载失败', e)
  }
})

// 跳转方案详情
const goSolution = (id) => {
  router.push(`/solution/${id}`)
}
</script>

<template>
  <footer class="bg-slate-900 text-slate-300 pt-20 pb-10 font-light border-t border-slate-800">
    <div class="container mx-auto px-6 md:px-12">
      <div class="grid grid-cols-1 md:grid-cols-4 gap-12 mb-16">
        
        <div class="space-y-6">
          <div class="text-2xl font-bold tracking-tighter text-white flex items-center gap-2 select-none">
            NOVATECH <span class="text-blue-500">AI</span>
          </div>
          <p class="text-slate-400 text-sm leading-relaxed">
            {{ companyInfo.remark || '星曜智能科技有限公司致力于将前沿 AI 算法转化为实际生产力。扎根春城，服务全球。' }}
          </p>
          <div class="flex gap-4">
            <div class="w-8 h-8 rounded-full bg-slate-800 flex items-center justify-center hover:bg-blue-600 transition-colors cursor-pointer text-white">
              <span class="text-xs font-bold">WX</span>
            </div>
            <div class="w-8 h-8 rounded-full bg-slate-800 flex items-center justify-center hover:bg-blue-600 transition-colors cursor-pointer text-white">
              <span class="text-xs font-bold">IN</span>
            </div>
          </div>
        </div>

        <div>
          <h3 class="text-white font-bold mb-6 text-lg">解决方案</h3>
          <ul class="space-y-4 text-sm">
            <li v-for="item in solutionList" :key="item.solutionId">
              <a 
                href="javascript:;" 
                @click="goSolution(item.solutionId)"
                class="hover:text-blue-400 transition-colors flex items-center gap-2 group"
              >
                <span class="w-1 h-1 bg-slate-600 rounded-full group-hover:bg-blue-400 transition-colors"></span>
                {{ item.title }}
              </a>
            </li>
            <li v-if="solutionList.length === 0">暂无方案数据</li>
          </ul>
        </div>

        <div>
          <h3 class="text-white font-bold mb-6 text-lg">联系我们</h3>
          <ul class="space-y-4 text-sm">
            <li class="flex items-start gap-3">
              <el-icon class="mt-1 text-blue-500 shrink-0"><Location /></el-icon>
              <span class="break-words w-48">{{ companyInfo.companyAddress || companyInfo.address || '云南省昆明市高新区...' }}</span>
            </li>
            <li class="flex items-center gap-3">
              <el-icon class="text-blue-500 shrink-0"><Phone /></el-icon>
              <span>{{ companyInfo.phoneHotline || companyInfo.contactPhone || '400-800-8888' }}</span>
            </li>
            <li class="flex items-center gap-3">
              <el-icon class="text-blue-500 shrink-0"><Message /></el-icon>
              <span>{{ companyInfo.email || 'contact@novatech.example.com' }}</span>
            </li>
          </ul>
        </div>

        <div>
          <h3 class="text-white font-bold mb-6 text-lg">关注我们</h3>
          <div class="bg-slate-800 p-4 rounded-xl inline-block border border-slate-700 shadow-lg">
            <img 
              :src="companyInfo.qrCode ? getImageUrl(companyInfo.qrCode) : 'https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=NovaTechAI'" 
              alt="QR Code" 
              class="w-24 h-24 opacity-90 bg-white"
            />
            <div class="text-center text-xs mt-3 text-slate-400 font-medium">扫码获取更多资讯</div>
          </div>
        </div>
      </div>

      <div class="border-t border-slate-800 pt-8 flex flex-col md:flex-row justify-between items-center text-xs text-slate-500">
        <div>
          {{ companyInfo.copyright || 'Copyright © 2025 NovaTech AI Technology Co., Ltd. All rights reserved.' }}
        </div>
        <div class="flex gap-6 mt-4 md:mt-0">
          <a href="#" class="hover:text-white transition-colors">隐私政策</a>
          <a href="#" class="hover:text-white transition-colors">服务条款</a>
          <a href="https://beian.miit.gov.cn/" target="_blank" class="hover:text-white transition-colors">
            {{ companyInfo.icpBeian || '滇ICP备20260001号' }}
          </a>
        </div>
      </div>
    </div>
  </footer>
</template>