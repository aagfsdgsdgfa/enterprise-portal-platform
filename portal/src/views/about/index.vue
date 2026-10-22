<script setup>
import { ref, onMounted, computed } from 'vue'
import { listCompanyInfo, listCultures } from '../../api/website'
import { getImageUrl } from '../../utils/common'
import { 
  Monitor, Trophy, Star, MapLocation, Phone, Message, 
  Cpu, DataBoard, Connection, Odometer 
} from '@element-plus/icons-vue'

// --- 1. 状态管理 ---
const companyInfo = ref({}) 
const cultures = ref([])    
const loading = ref(true)

// --- 2. 图标映射 ---
const iconMap = {
  'el-icon-cpu': Cpu,
  'el-icon-monitor': Monitor,
  'el-icon-data-board': DataBoard,
  'el-icon-connection': Connection,
  'el-icon-trophy': Trophy,
  'el-icon-star': Star,
  'default': Star 
}

const getIconComponent = (iconName) => {
  if (!iconName) return iconMap['default']
  return iconMap[iconName] || iconMap['default']
}

// --- 3. 获取数据 ---
onMounted(async () => {
  loading.value = true
  try {
    const [infoRes, cultureRes] = await Promise.all([
      listCompanyInfo(),
      listCultures()
    ])

    if (infoRes.rows && infoRes.rows.length > 0) {
      companyInfo.value = infoRes.rows[0]
    }

    if (cultureRes.rows) {
      cultures.value = cultureRes.rows.sort((a, b) => a.sortOrder - b.sortOrder)
    }

  } catch (e) {
    console.error('加载数据失败', e)
  } finally {
    loading.value = false
  }
})

// --- 4. 地图跳转链接 ---
const mapLink = computed(() => {
  const lat = companyInfo.value.latitude
  const lng = companyInfo.value.longitude
  const name = companyInfo.value.companyName
  return `http://api.map.baidu.com/marker?location=${lat},${lng}&title=${name}&content=${companyInfo.value.address}&output=html`
})
</script>

<template>
  <div class="bg-white min-h-screen font-sans">
    
    <div class="relative bg-slate-900 h-[550px] flex items-center justify-center overflow-hidden">
      <div class="absolute top-0 right-0 w-[600px] h-[600px] bg-blue-700/20 blur-[100px] rounded-full translate-x-1/3 -translate-y-1/3"></div>
      <div class="absolute bottom-0 left-0 w-[500px] h-[500px] bg-teal-600/10 blur-[80px] rounded-full -translate-x-1/3 translate-y-1/3"></div>
      
      <div class="relative z-10 text-center px-6 max-w-4xl mx-auto">
        <span class="inline-block py-1.5 px-4 rounded-full bg-black/30 border border-white/20 text-blue-200 text-sm font-bold tracking-widest uppercase mb-8 backdrop-blur-sm">
          About Us
        </span>
        
        <h1 class="text-5xl md:text-7xl font-bold text-white mb-8 tracking-tight leading-tight drop-shadow-lg">
          构建 <span class="text-blue-400">万物互联</span><br>
          的智能新世界
        </h1>
        
        <p class="text-slate-200 text-lg md:text-xl max-w-2xl mx-auto leading-relaxed font-normal antialiased">
          {{ companyInfo.remark && companyInfo.remark.length > 5 ? companyInfo.remark : '致力于通过前沿AI技术为行业赋能，让智能触手可及。' }}
        </p>
      </div>
    </div>

    <div class="py-24 bg-white relative">
      <div class="container mx-auto px-6 max-w-6xl">
        <div class="flex flex-col lg:flex-row items-center gap-20">
          
          <div class="lg:w-1/2 relative">
            <div class="absolute -inset-2 bg-gradient-to-tr from-blue-100 to-teal-50 rounded-[2.2rem] -z-10 transform rotate-2"></div>
            
            <img 
              :src="getImageUrl(companyInfo.logoDark || companyInfo.logoLight)" 
              class="w-full rounded-[2rem] shadow-xl border-2 border-slate-50 bg-white object-contain p-12 h-[380px]"
              alt="Company Logo"
            />
            
            <div class="absolute -bottom-6 -right-6 bg-white p-6 rounded-xl shadow-[0_10px_40px_-10px_rgba(0,0,0,0.15)] border border-slate-100 hidden md:flex items-center gap-4">
               <div class="w-12 h-12 bg-blue-600 rounded-lg flex items-center justify-center text-white shadow-lg shadow-blue-600/30">
                  <span class="font-bold text-lg">25</span>
               </div>
               <div>
                 <div class="text-2xl font-bold text-slate-900">2025</div>
                 <div class="text-xs text-slate-500 font-bold uppercase tracking-wider">Founded Year</div>
               </div>
            </div>
          </div>

          <div class="lg:w-1/2">
            <h2 class="text-4xl font-bold text-slate-900 mb-6">
              {{ companyInfo.companyName }}
            </h2>
            <div class="h-1.5 w-16 bg-blue-600 rounded-full mb-8"></div>
            
            <div 
               class="text-slate-700 text-lg leading-8 mb-8 text-justify"
               v-html="companyInfo.companyProfile || companyInfo.introduction || '<p>作为人工智能领域的先行者，我们专注于计算机视觉、自然语言处理及边缘计算硬件的研发。</p>'"
            ></div>

            <div class="grid grid-cols-2 gap-6 mt-8">
              <div class="bg-slate-50 p-4 rounded-xl border border-slate-100">
                <div class="text-sm text-slate-500 mb-1">ICP备案号</div>
                <div class="text-lg font-bold text-slate-900">{{ companyInfo.icpBeian || '滇ICP备...' }}</div>
              </div>
              <div class="bg-slate-50 p-4 rounded-xl border border-slate-100">
                <div class="text-sm text-slate-500 mb-1">工作时间</div>
                <div class="text-lg font-bold text-slate-900">{{ companyInfo.workTime || '09:00 - 18:00' }}</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="bg-slate-50 py-24 border-t border-slate-200">
      <div class="container mx-auto px-6 max-w-7xl">
        <div class="text-center mb-16">
          <span class="text-blue-700 font-bold tracking-widest uppercase text-sm mb-3 block">Our Values</span>
          <h2 class="text-3xl md:text-4xl font-bold text-slate-900">企业文化与核心价值</h2>
        </div>

        <div v-if="loading" class="flex justify-center">
           <div class="w-10 h-10 border-4 border-blue-600 border-t-transparent rounded-full animate-spin"></div>
        </div>

        <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
          <div 
            v-for="(item, index) in cultures" 
            :key="item.cultureId"
            class="group bg-white rounded-2xl p-8 border border-slate-200 shadow-sm hover:shadow-xl hover:border-blue-200 transition-all duration-300 hover:-translate-y-1"
          >
            <div class="w-14 h-14 bg-blue-50 rounded-xl flex items-center justify-center text-blue-600 mb-6 group-hover:bg-blue-600 group-hover:text-white transition-colors">
              <el-icon :size="28">
                <component :is="getIconComponent(item.icon)" />
              </el-icon>
            </div>
            
            <div class="text-xs font-bold text-blue-600 tracking-wider uppercase mb-2">
              {{ item.subTitle || 'Culture' }}
            </div>
            
            <h3 class="text-xl font-bold text-slate-900 mb-4">
              {{ item.title }}
            </h3>
            
            <p class="text-slate-600 leading-relaxed text-sm h-20 overflow-hidden text-ellipsis line-clamp-3">
              {{ item.description }}
            </p>
          </div>
        </div>
      </div>
    </div>

    <div class="bg-slate-900 text-white py-20">
      <div class="container mx-auto px-6 max-w-6xl">
        <div class="bg-slate-800 rounded-3xl p-8 md:p-12 flex flex-col md:flex-row gap-12 border border-slate-700 shadow-2xl">
          
          <div class="md:w-5/12 space-y-8">
            <div>
              <h2 class="text-3xl font-bold text-white mb-2">联系我们</h2>
              <p class="text-slate-300">期待与您共创未来</p>
            </div>
            
            <div class="space-y-6 pt-2">
              <div class="flex items-start gap-5">
                <div class="w-12 h-12 rounded-lg bg-blue-600 flex items-center justify-center text-white shadow-lg shrink-0">
                  <el-icon :size="22"><Phone /></el-icon>
                </div>
                <div>
                  <div class="text-xs text-slate-400 uppercase tracking-wider mb-1">Hotline</div>
                  <div class="text-xl font-bold text-white tracking-wide">
                    {{ companyInfo.phoneHotline }}
                  </div>
                  <div class="text-sm text-slate-300 mt-1">Sales: {{ companyInfo.phoneSales }}</div>
                </div>
              </div>

              <div class="flex items-start gap-5">
                <div class="w-12 h-12 rounded-lg bg-teal-600 flex items-center justify-center text-white shadow-lg shrink-0">
                  <el-icon :size="22"><Message /></el-icon>
                </div>
                <div>
                  <div class="text-xs text-slate-400 uppercase tracking-wider mb-1">Email</div>
                  <div class="text-lg font-medium text-white">
                    {{ companyInfo.email }}
                  </div>
                </div>
              </div>

              <div class="flex items-start gap-5">
                <div class="w-12 h-12 rounded-lg bg-purple-600 flex items-center justify-center text-white shadow-lg shrink-0">
                  <el-icon :size="22"><MapLocation /></el-icon>
                </div>
                <div>
                  <div class="text-xs text-slate-400 uppercase tracking-wider mb-1">Address</div>
                  <div class="text-base text-white leading-relaxed">
                    {{ companyInfo.address }}
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div class="md:w-7/12 relative h-full min-h-[300px] rounded-2xl overflow-hidden border border-slate-600 group bg-slate-900">
             <div class="absolute inset-0 opacity-20 bg-[radial-gradient(#ffffff33_1px,transparent_1px)] [background-size:20px_20px]"></div>
             
             <div class="absolute inset-0 flex flex-col items-center justify-center z-10">
                <el-icon :size="48" class="text-slate-500 mb-4"><MapLocation /></el-icon>
                <p class="text-slate-400 font-mono text-sm mb-6">{{ companyInfo.longitude }}, {{ companyInfo.latitude }}</p>
                
                <a :href="mapLink" target="_blank" class="px-6 py-3 bg-blue-600 hover:bg-blue-500 text-white rounded-full font-bold transition-colors shadow-lg flex items-center gap-2">
                   <el-icon><Connection /></el-icon> 打开百度地图
                </a>
             </div>
          </div>

        </div>
        
        <div class="text-center text-slate-400 text-sm mt-12">
          {{ companyInfo.copyright || 'Copyright © 2025 NovaTech AI Technology Co., Ltd.' }}
        </div>
      </div>
    </div>

  </div>
</template>

<style scoped>
/* 保证 v-html 里的文字也是黑色的，看得清 */
:deep(p) {
  margin-bottom: 1em;
  color: #334155; /* slate-700 */
}
</style>