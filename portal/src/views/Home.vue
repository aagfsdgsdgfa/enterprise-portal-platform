<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
// 引入 API
import { listBanners, listProducts, listArticles, listJobs, listHomeConfigs } from '../api/website'
import { getImageUrl } from '../utils/common'
import { 
  ArrowRight, Cpu, CircleCheck, Connection, 
  UserFilled, Location, Briefcase
} from '@element-plus/icons-vue'

const router = useRouter()

// --- 数据状态 ---
const bannerList = ref([])     // 统一的轮播图数据 (PC & Mobile)
const products = ref([])       // 核心产品展示
const newsList = ref([])       // 新闻列表
const jobs = ref([])           // 职位列表

// --- 首页配置数据 ---
const homeConfig = ref({
  stats: [],      
  whyUs: [],      
  recruit: {},    
  titles: {}      
})

// --- 辅助：随机打乱数组 ---
const shuffleArray = (array) => {
  for (let i = array.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1));
    [array[i], array[j]] = [array[j], array[i]];
  }
  return array;
}

// --- 图标映射 ---
const iconMap = {
  'CircleCheck': CircleCheck,
  'Connection': Connection,
  'Cpu': Cpu,
}
const getIcon = (name) => iconMap[name] || CircleCheck

// --- 跳转 ---
const goPath = (path) => router.push(path)
const goDetail = (path, id) => router.push(`${path}/${id}`)

onMounted(async () => {
  try {
    // 并行请求数据
    const [configRes, bannerRes, prodRes, newsRes, jobRes] = await Promise.all([
      listHomeConfigs(),
      listBanners(),
      listProducts({ pageNum: 1, pageSize: 10 }), 
      listArticles({ pageNum: 1, pageSize: 10 }), 
      listJobs({ pageNum: 1, pageSize: 10, status: '0' }) 
    ])

    // 1. 处理配置数据
    if (configRes.rows) {
      homeConfig.value.stats = configRes.rows.filter(c => c.configGroup === 'stats').sort((a,b) => a.sortOrder - b.sortOrder)
      homeConfig.value.whyUs = configRes.rows.filter(c => c.configGroup === 'why_us').sort((a,b) => a.sortOrder - b.sortOrder)
      const recruitObj = configRes.rows.find(c => c.configGroup === 'recruit' && c.configKey === 'main_text')
      if (recruitObj) homeConfig.value.recruit = recruitObj
      configRes.rows.filter(c => c.configGroup === 'section_title').forEach(t => {
        homeConfig.value.titles[t.configKey] = t
      })
    }

    // 2. 统一处理 Banner (PC与移动端共用)
    if (bannerRes.rows) {
      bannerList.value = bannerRes.rows
    }

    // 3. 处理产品展示 (随机取 3 个作为核心展示)
    if (prodRes.rows && prodRes.rows.length > 0) {
      const shuffled = shuffleArray([...prodRes.rows])
      products.value = shuffled.slice(0, 3) 
    }

    // 4. 处理新闻 (置顶优先 -> 时间倒序 -> 取前4)
    if (newsRes.rows) {
      newsList.value = newsRes.rows.sort((a, b) => {
        const topA = a.isTop === '1' ? 1 : 0
        const topB = b.isTop === '1' ? 1 : 0
        if (topA !== topB) return topB - topA
        
        const timeA = new Date(a.publishTime || a.createTime).getTime()
        const timeB = new Date(b.publishTime || b.createTime).getTime()
        return timeB - timeA
      }).slice(0, 4) 
    }

    // 5. 处理职位 (急聘优先 -> 时间倒序 -> 取前4)
    if (jobRes.rows) {
      jobs.value = jobRes.rows.sort((a, b) => {
        const urgentA = a.isUrgent === '1' ? 1 : 0
        const urgentB = b.isUrgent === '1' ? 1 : 0
        if (urgentA !== urgentB) return urgentB - urgentA

        const timeA = new Date(a.createTime).getTime()
        const timeB = new Date(b.createTime).getTime()
        return timeB - timeA
      }).slice(0, 4)
      .map(job => ({
        ...job,
        tags: [
          job.isUrgent === '1' ? '急聘' : null, 
          job.education, 
          job.experience
        ].filter(Boolean)
      }))
    }

  } catch (e) {
    console.error("加载首页数据失败", e)
  }
})
</script>

<template>
  <div class="w-full overflow-x-hidden font-sans bg-slate-50">

    <div class="relative w-full h-screen bg-slate-900">
      <el-carousel 
        trigger="click" 
        height="100vh" 
        :interval="8000" 
        arrow="hover" 
        indicator-position="none" 
        class="h-full"
      >
        <el-carousel-item v-for="(item, index) in bannerList" :key="index" class="h-full">
          <div class="relative w-full h-full">
            
            <img 
              :src="getImageUrl(item.imageUrl)" 
              class="w-full h-full object-cover brightness-[0.7] md:brightness-[0.85] transition-transform duration-[10s] ease-linear hover:scale-105" 
            />
            
            <div class="absolute inset-0 bg-gradient-to-t md:bg-gradient-to-r from-slate-950/90 via-slate-900/40 to-transparent"></div>
            
            <div class="absolute inset-0 flex items-center">
              <div class="w-full max-w-[1400px] mx-auto px-6 md:px-12 pt-20 md:pt-0"> 
                <div class="max-w-4xl animate-slide-up">
                  
                  <div class="w-16 md:w-24 h-1 md:h-1.5 bg-blue-500 mb-6 md:mb-10 rounded-full shadow-[0_0_20px_rgba(59,130,246,0.6)]"></div>
                  
                  <h1 class="text-4xl md:text-7xl lg:text-8xl font-black text-white leading-[1.2] md:leading-[1.1] mb-6 md:mb-8 tracking-tighter drop-shadow-2xl break-words">
                    {{ item.title }}
                  </h1>
                  
                  <p class="text-lg md:text-2xl lg:text-3xl text-slate-200 mb-10 md:mb-14 font-light max-w-xl md:max-w-3xl leading-relaxed opacity-90 border-l-4 border-blue-600/50 pl-6 md:pl-8 line-clamp-3 md:line-clamp-none">
                    {{ item.sub_title }}
                  </p>
                  
                  <button @click="goPath('/solutions')" class="bg-blue-600 hover:bg-blue-500 text-white px-8 py-4 md:px-10 md:py-5 rounded-full font-bold text-base md:text-xl transition-all shadow-[0_10px_30px_rgba(37,99,235,0.4)] hover:-translate-y-1 flex items-center gap-3 group">
                    探索解决方案 <el-icon class="group-hover:translate-x-1 transition-transform"><ArrowRight /></el-icon>
                  </button>

                </div>
              </div>
            </div>
          </div>
        </el-carousel-item>
      </el-carousel>

      <div class="absolute bottom-8 left-1/2 -translate-x-1/2 flex flex-col items-center gap-3 animate-bounce-slow opacity-60 z-20 pointer-events-none">
        <span class="text-[10px] text-slate-400 uppercase tracking-[0.3em] font-bold">Scroll Down</span>
        <div class="w-px h-12 md:h-16 bg-gradient-to-b from-slate-400 to-transparent"></div>
      </div>
    </div>

    <div class="relative z-30 px-4 mb-24 -mt-10 md:-mt-24">
      <div class="w-full max-w-[1400px] mx-auto">
        <div class="bg-white/95 backdrop-blur-xl rounded-[1.5rem] md:rounded-[2rem] shadow-[0_30px_60px_-15px_rgba(0,0,0,0.15)] p-6 md:p-10 border border-white/50 grid grid-cols-2 md:grid-cols-4 gap-6 md:gap-8">
           <div 
             v-for="(stat, idx) in homeConfig.stats" 
             :key="stat.configId"
             class="text-center group border-r border-slate-100 last:border-0 odd:border-r md:odd:border-r-0"
           >
             <div class="text-3xl md:text-5xl lg:text-6xl font-black text-slate-900 mb-2 group-hover:text-blue-600 transition-colors tracking-tighter">
               {{ stat.title }}
             </div>
             <div class="text-[10px] md:text-xs text-slate-500 uppercase tracking-widest font-bold">
               {{ stat.subTitle }}
             </div>
           </div>
        </div>
      </div>
    </div>

    <section class="py-16 md:py-24">
      <div class="w-full max-w-[1400px] mx-auto px-6 md:px-12">
        <div class="flex flex-col md:flex-row justify-between items-end mb-12 md:mb-16 gap-6">
          <div class="max-w-3xl">
            <h3 class="text-blue-600 font-bold tracking-[0.2em] uppercase mb-4 text-xs md:text-sm flex items-center gap-3">
              <span class="w-8 md:w-12 h-px bg-blue-600"></span> 
              {{ homeConfig.titles['product']?.subTitle || 'Our Solutions' }}
            </h3>
            <h2 class="text-3xl md:text-4xl lg:text-5xl font-bold text-slate-900 tracking-tight leading-tight">
              {{ homeConfig.titles['product']?.title || '核心技术与产品矩阵' }}
            </h2>
          </div>
          <button @click="goPath('/products')" class="group flex items-center gap-2 text-slate-900 font-bold text-base md:text-lg hover:text-blue-600 transition-colors pb-1 border-b-2 border-slate-200 hover:border-blue-600">
            查看所有产品 <el-icon class="group-hover:translate-x-1 transition-transform"><ArrowRight /></el-icon>
          </button>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-3 gap-8 md:gap-10">
          <div 
            v-for="(product, index) in products" 
            :key="product.productId"
            @click="goDetail('/product', product.productId)"
            class="group relative bg-white rounded-[2rem] overflow-hidden shadow-lg hover:shadow-2xl hover:shadow-blue-900/10 transition-all duration-500 hover:-translate-y-2 border border-slate-100 cursor-pointer"
          >
            <div class="h-56 md:h-72 lg:h-80 overflow-hidden relative">
              <img 
                :src="getImageUrl(product.mainImg)" 
                class="w-full h-full object-cover transition-transform duration-700 group-hover:scale-110"
              />
              <div class="absolute inset-0 bg-slate-900/10 group-hover:bg-transparent transition-colors"></div>
              <div class="absolute top-4 right-4 md:top-6 md:right-6 w-12 h-12 md:w-14 md:h-14 bg-white/90 backdrop-blur rounded-2xl flex items-center justify-center shadow-lg text-blue-600 group-hover:bg-blue-600 group-hover:text-white transition-all duration-300">
                <el-icon :size="24"><Cpu /></el-icon>
              </div>
            </div>
            <div class="p-6 md:p-8 lg:p-10">
              <h3 class="text-xl md:text-2xl font-bold text-slate-900 mb-3 md:mb-4 group-hover:text-blue-600 transition-colors line-clamp-1">
                {{ product.productName }}
              </h3>
              <p class="text-slate-500 text-sm md:text-base lg:text-lg leading-relaxed mb-6 md:mb-8 line-clamp-3">
                {{ product.summary }}
              </p>
              <div class="flex items-center gap-2 text-xs md:text-sm font-bold text-slate-300 uppercase tracking-wider group-hover:text-blue-600 transition-colors">
                Learn More <el-icon><ArrowRight /></el-icon>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <section class="py-24 md:py-32 bg-slate-950 text-white relative overflow-hidden">
      <div class="absolute top-0 right-0 w-[800px] md:w-[1000px] h-[800px] md:h-[1000px] bg-blue-600/5 blur-[120px] rounded-full translate-x-1/2 -translate-y-1/2 pointer-events-none"></div>

      <div class="w-full max-w-[1400px] mx-auto px-6 md:px-12 relative z-10">
        <div class="flex flex-col lg:flex-row gap-16 lg:gap-20 items-center">
          <div class="lg:w-1/2 w-full">
            <h3 class="text-blue-400 font-bold tracking-[0.2em] uppercase mb-4 md:mb-6 text-xs md:text-sm">Why NovaTech AI</h3>
            <h2 class="text-3xl md:text-5xl lg:text-6xl font-bold mb-8 md:mb-10 leading-tight">
              以算法驱动 <br/> 
              <span class="text-transparent bg-clip-text bg-gradient-to-r from-blue-400 to-teal-400">重塑生产力</span>
            </h2>
            <p class="text-slate-400 text-base md:text-xl mb-10 md:mb-12 leading-relaxed border-l-4 border-slate-800 pl-6 md:pl-8">
              我们提供从底层算力、中层算法到上层应用的全栈式 AI 解决方案。
            </p>

            <div class="grid gap-6 md:gap-8">
              <div v-for="item in homeConfig.whyUs" :key="item.configId" class="flex gap-4 md:gap-6 group cursor-default">
                <div class="w-14 h-14 md:w-16 md:h-16 bg-slate-900 border border-slate-800 rounded-xl md:rounded-2xl flex items-center justify-center text-blue-400 group-hover:border-blue-500 group-hover:text-blue-500 transition-all shrink-0">
                   <el-icon :size="28">
                     <component :is="getIcon(item.icon)" />
                   </el-icon>
                </div>
                <div>
                  <h4 class="text-lg md:text-xl font-bold mb-1 md:mb-2 text-white group-hover:text-blue-400 transition-colors">{{ item.title }}</h4>
                  <p class="text-slate-400 text-xs md:text-sm leading-relaxed">{{ item.subTitle }}</p>
                </div>
              </div>
            </div>
          </div>
          
          <div class="lg:w-1/2 w-full relative group">
             <div class="relative rounded-3xl overflow-hidden border border-slate-700 shadow-2xl">
               <img src="https://images.unsplash.com/photo-1550751827-4bd374c3f58b?q=80&w=1000" class="w-full h-auto min-h-[300px] object-cover opacity-70 group-hover:opacity-100 transition-opacity duration-700" />
               <div class="absolute inset-0 bg-gradient-to-t from-slate-950 to-transparent"></div>
             </div>
          </div>
        </div>
      </div>
    </section>

    <section class="py-24 md:py-32 bg-slate-100 relative overflow-hidden">
      <div class="w-full max-w-[1500px] mx-auto px-6 md:px-12">
        <div class="flex flex-col xl:flex-row gap-8 md:gap-12">

          <div class="xl:w-1/3 relative">
            <div class="bg-slate-900 rounded-[2rem] md:rounded-[2.5rem] p-8 md:p-12 text-white shadow-2xl h-full flex flex-col justify-between overflow-hidden relative min-h-[350px] md:min-h-[400px]">
              <div class="absolute top-0 right-0 w-64 h-64 bg-blue-600 rounded-full blur-[80px] opacity-30"></div>
              
              <div class="relative z-10">
                <div class="flex items-center gap-3 text-blue-400 font-bold tracking-widest uppercase mb-4 md:mb-6">
                  <span class="p-2 bg-blue-500/20 rounded-lg"><el-icon><UserFilled /></el-icon></span>
                  Join NovaTech AI
                </div>
                <h2 class="text-3xl md:text-4xl lg:text-5xl font-bold mb-4 md:mb-6 leading-tight">
                  {{ homeConfig.recruit.title || '寻找 AI 梦想家' }}
                </h2>
                <p class="text-slate-400 text-base md:text-lg leading-relaxed mb-8 md:mb-10">
                  {{ homeConfig.recruit.subTitle || '我们提供极具竞争力的薪酬与期权，以及与顶尖科学家共事的机会。' }}
                </p>
                <button @click="goPath('/jobs')" class="bg-blue-600 text-white px-6 md:px-8 py-3 md:py-4 rounded-full font-bold hover:bg-blue-500 transition-all flex items-center justify-center gap-2 w-full shadow-lg">
                  查看全部职位 <el-icon><ArrowRight /></el-icon>
                </button>
              </div>
            </div>
          </div>

          <div class="xl:w-2/3 grid grid-cols-1 md:grid-cols-2 gap-4 md:gap-6">
             <div 
               v-for="(job, idx) in jobs" 
               :key="job.jobId" 
               @click="goDetail('/job', job.jobId)"
               class="group bg-white p-6 md:p-8 lg:p-10 rounded-[1.5rem] md:rounded-[2rem] border border-slate-200 shadow-sm hover:shadow-2xl hover:border-blue-200 transition-all cursor-pointer flex flex-col justify-between h-auto md:h-[280px]"
            >
              <div>
                <div class="flex justify-between items-start mb-4">
                  <div class="w-10 h-10 md:w-12 md:h-12 bg-slate-50 rounded-xl flex items-center justify-center text-slate-400 group-hover:bg-blue-600 group-hover:text-white transition-all">
                     <el-icon :size="20"><Briefcase /></el-icon>
                  </div>
                  <div v-if="job.isUrgent === '1'" class="px-3 md:px-4 py-1 bg-red-50 text-red-600 text-[10px] md:text-xs font-bold rounded-full animate-pulse">HOT</div>
                </div>
                <h3 class="text-lg md:text-xl font-bold text-slate-900 group-hover:text-blue-600 transition-colors">{{ job.jobTitle }}</h3>
                <div class="flex flex-wrap items-center gap-2 mt-2 text-xs md:text-sm text-slate-500">
                  <span class="flex items-center gap-1"><el-icon><Location /></el-icon> {{ job.workLocation }}</span>
                  <span class="mx-1">|</span> {{ job.department }}
                </div>
              </div>
              <div class="mt-6 md:mt-0">
                <div class="w-full h-px bg-slate-100 mb-4 md:mb-6"></div>
                <div class="flex justify-between items-center">
                  <div class="flex gap-2 flex-wrap">
                    <span v-for="tag in job.tags" :key="tag" class="text-[10px] md:text-xs font-medium text-slate-500 bg-slate-100 px-2 md:px-3 py-1 rounded-lg">{{ tag }}</span>
                  </div>
                  <span class="text-lg md:text-xl font-black text-blue-600">{{ job.salaryRange }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <section class="py-24 md:py-32 bg-white">
      <div class="w-full max-w-[1400px] mx-auto px-6 md:px-12">
        <div class="flex flex-col md:flex-row justify-between items-start md:items-end mb-12 md:mb-16 gap-6">
          <div>
            <h2 class="text-3xl md:text-4xl lg:text-5xl font-bold text-slate-900 mb-2 md:mb-4">{{ homeConfig.titles['news']?.title || '最新动态' }}</h2>
            <p class="text-slate-500 text-base md:text-lg">{{ homeConfig.titles['news']?.subTitle || '洞察行业趋势' }}</p>
          </div>
          <button @click="goPath('/news')" class="text-slate-900 font-bold hover:text-blue-600 transition-colors flex items-center gap-2">
            查看更多资讯 <el-icon class="md:hidden"><ArrowRight /></el-icon>
          </button>
        </div>
        
        <div class="grid grid-cols-1 md:grid-cols-4 gap-8">
          <div v-for="news in newsList" :key="news.articleId" @click="goDetail('/news', news.articleId)" class="group cursor-pointer">
             <div class="w-full aspect-[4/3] bg-slate-100 rounded-2xl overflow-hidden mb-4 md:mb-6 relative">
               <img :src="getImageUrl(news.coverImg)" class="w-full h-full object-cover transition-transform duration-700 group-hover:scale-105" />
               
               <div v-if="news.isTop === '1'" class="absolute top-3 right-3 md:top-4 md:right-4 bg-blue-600 text-white px-2 py-1 md:px-3 rounded-lg text-[10px] md:text-xs font-bold shadow-md z-10">
                 置顶
               </div>

               <div class="absolute bottom-3 left-3 md:bottom-4 md:left-4 bg-white/95 backdrop-blur px-3 py-1.5 md:px-4 md:py-2 rounded-xl text-[10px] md:text-xs font-bold text-slate-900 shadow-lg">
                 {{ news.publishTime || news.createTime?.substring(0, 10) }}
               </div>
             </div>
             <h3 class="text-lg md:text-xl font-bold text-slate-900 group-hover:text-blue-600 transition-colors line-clamp-2 mb-2 md:mb-3 leading-snug">
               {{ news.title }}
             </h3>
             <p class="text-slate-500 text-xs md:text-sm line-clamp-2 leading-relaxed">
               {{ news.summary }}
             </p>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<style scoped>
/* 确保轮播图文字上浮动画 */
.animate-slide-up {
  animation: slideUp 1s ease-out forwards;
}

@keyframes slideUp {
  from { opacity: 0; transform: translateY(30px); }
  to { opacity: 1; transform: translateY(0); }
}

/* 简单的上下跳动动画 */
.animate-bounce-slow {
  animation: bounce 3s infinite;
}

@keyframes bounce {
  0%, 100% { transform: translate(-50%, 0); }
  50% { transform: translate(-50%, 10px); }
}

/* 移动端轮播图箭头样式微调 */
:deep(.el-carousel__arrow) {
  background-color: rgba(0,0,0,0.3);
  border: 1px solid rgba(255,255,255,0.2);
}
:deep(.el-carousel__arrow:hover) {
  background-color: #2563eb;
}
</style>