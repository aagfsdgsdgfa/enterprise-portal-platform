<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getSolution, listProducts } from '../../api/website'
import { getImageUrl } from '../../utils/common'
import { 
  ArrowLeft, Warning, CircleCheck, Connection, Right 
} from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const loading = ref(true)
const solution = ref({})
const relatedProducts = ref([])

// --- 1. 数据处理 ---
// 将 "痛点1;痛点2" 转为数组
const painPointList = computed(() => {
  if (!solution.value.painPoints) return []
  return solution.value.painPoints.split(/[;；]/).filter(i => i)
})

// 将 "价值1;价值2" 转为数组
const valuePropList = computed(() => {
  if (!solution.value.valueProps) return []
  return solution.value.valueProps.split(/[;；]/).filter(i => i)
})

// --- 2. 获取数据 ---
onMounted(async () => {
  const id = route.params.id
  if (!id) return

  try {
    loading.value = true
    // 1. 获取方案详情
    const res = await getSolution(id)
    // 兼容直接返回data或者在res.data中返回
    const data = res.data || res
    if (data) {
      solution.value = data
      
      // 2. 如果有关联产品ID，获取所有产品并筛选
      if (solution.value.relatedProductIds) {
        const pRes = await listProducts()
        if (pRes.rows) {
          // 兼容中文或英文逗号
          const ids = solution.value.relatedProductIds.split(/[,，]/).map(s => Number(s.trim()))
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

const goBack = () => router.push('/solutions')
const goProduct = (id) => router.push(`/product/${id}`)
</script>

<template>
  <div class="bg-white min-h-screen font-sans pb-20">
    
    <div v-if="loading" class="h-screen flex items-center justify-center">
      <div class="w-12 h-12 border-4 border-indigo-600 border-t-transparent rounded-full animate-spin"></div>
    </div>

    <div v-else>
      <div class="relative h-[500px] w-full overflow-hidden bg-slate-900">
        <img 
          :src="getImageUrl(solution.bannerImg)" 
          class="absolute inset-0 w-full h-full object-cover opacity-60"
        />
        <div class="absolute inset-0 bg-gradient-to-t from-slate-900 via-slate-900/60 to-slate-900/30"></div>
        
        <div class="container mx-auto px-6 h-full flex flex-col justify-end pb-20 relative z-10">
          
          <div class="absolute top-10 left-6">
            <button 
              @click="goBack" 
              class="group flex items-center gap-2 px-5 py-2.5 bg-slate-900/50 hover:bg-slate-900/80 border border-white/10 hover:border-indigo-500/50 backdrop-blur-md rounded-full text-white text-sm font-medium transition-all duration-300 shadow-lg hover:shadow-indigo-500/20"
            >
              <el-icon class="text-lg group-hover:text-indigo-300 transition-colors group-hover:-translate-x-1 transform duration-300"><ArrowLeft /></el-icon>
              <span class="group-hover:text-indigo-100 transition-colors">返回方案列表</span>
            </button>
          </div>
          
          <div class="inline-block px-3 py-1 bg-indigo-500/20 border border-indigo-400/30 text-indigo-300 rounded-lg text-sm font-bold mb-4 backdrop-blur-md w-fit">
            SOLUTION #0{{ solution.solutionId }}
          </div>
          
          <h1 class="text-4xl md:text-6xl font-bold text-white mb-4 drop-shadow-lg tracking-tight">
            {{ solution.title }}
          </h1>
          
          <p class="text-xl md:text-2xl text-slate-200 font-light border-l-4 border-indigo-500 pl-6 drop-shadow-md">
            {{ solution.subTitle }}
          </p>
        </div>
      </div>

      <div class="container mx-auto px-6 max-w-6xl -mt-16 relative z-20 animate-fade-in-up">
        
        <div class="bg-white rounded-[2rem] shadow-2xl shadow-slate-200/50 p-8 md:p-12 border border-slate-100 mb-16">
          <div class="grid grid-cols-1 md:grid-cols-2 gap-12 relative">
            
            <div class="hidden md:block absolute top-0 bottom-0 left-1/2 w-px bg-gradient-to-b from-transparent via-slate-200 to-transparent"></div>

            <div>
              <h3 class="text-2xl font-bold text-slate-900 mb-8 flex items-center gap-3">
                <div class="w-10 h-10 bg-red-100 rounded-xl flex items-center justify-center text-red-600 shadow-sm">
                   <el-icon :size="20"><Warning /></el-icon>
                </div>
                行业痛点与挑战
              </h3>
              <div class="space-y-5">
                <div 
                  v-for="(point, idx) in painPointList" 
                  :key="'pain'+idx"
                  class="flex items-start gap-4 p-5 bg-red-50/50 rounded-2xl border border-red-100 hover:border-red-200 hover:shadow-sm transition-all"
                >
                  <div class="min-w-[28px] h-7 rounded-full bg-red-200 text-red-700 flex items-center justify-center text-sm font-bold mt-0.5 shadow-inner">
                    {{ idx + 1 }}
                  </div>
                  <p class="text-slate-700 font-medium leading-relaxed">{{ point }}</p>
                </div>
              </div>
            </div>

            <div class="md:pl-12">
              <h3 class="text-2xl font-bold text-slate-900 mb-8 flex items-center gap-3">
                <div class="w-10 h-10 bg-teal-100 rounded-xl flex items-center justify-center text-teal-600 shadow-sm">
                   <el-icon :size="20"><CircleCheck /></el-icon>
                </div>
                方案核心价值
              </h3>
              <div class="space-y-5">
                <div 
                  v-for="(val, idx) in valuePropList" 
                  :key="'val'+idx"
                  class="flex items-start gap-4 p-5 bg-teal-50/50 rounded-2xl border border-teal-100 hover:border-teal-200 hover:shadow-sm transition-all"
                >
                  <el-icon class="text-teal-600 mt-1 text-xl"><CircleCheck /></el-icon>
                  <p class="text-slate-800 font-bold leading-relaxed">{{ val }}</p>
                </div>
              </div>
            </div>

          </div>
        </div>

        <div class="flex flex-col lg:flex-row gap-16">
          
          <div class="lg:w-2/3">
             <div class="mb-8 pb-4 border-b border-slate-100">
               <span class="text-indigo-600 font-bold tracking-widest uppercase text-sm">Details</span>
               <h2 class="text-3xl font-bold text-slate-900 mt-2">方案详细阐述</h2>
             </div>
             <div class="prose prose-lg prose-slate max-w-none 
                 prose-headings:font-bold prose-headings:text-slate-900 
                 prose-p:text-slate-600 prose-p:leading-8
                 prose-img:rounded-2xl prose-img:shadow-lg prose-img:my-10"
                 v-html="solution.content">
             </div>
          </div>

          <div class="lg:w-1/3">
            <div class="sticky top-24">
              <div class="bg-slate-50 rounded-[2rem] p-8 border border-slate-200/60 shadow-xl shadow-slate-100">
                <h3 class="text-lg font-bold text-slate-900 mb-6 flex items-center gap-3">
                  <div class="w-8 h-8 bg-indigo-100 rounded-lg flex items-center justify-center text-indigo-600">
                     <el-icon><Connection /></el-icon>
                  </div>
                  推荐硬件/算法
                </h3>

                <div v-if="relatedProducts.length > 0" class="space-y-4">
                  <div 
                    v-for="prod in relatedProducts" 
                    :key="prod.productId"
                    @click="goProduct(prod.productId)"
                    class="group bg-white p-4 rounded-2xl border border-slate-200 shadow-sm hover:border-indigo-300 hover:shadow-md cursor-pointer flex gap-4 items-center transition-all duration-300"
                  >
                    <div class="w-16 h-16 bg-slate-100 rounded-xl overflow-hidden shrink-0 border border-slate-100">
                      <img :src="getImageUrl(prod.mainImg)" class="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500" />
                    </div>
                    <div class="flex-grow min-w-0 py-1">
                      <div class="text-base font-bold text-slate-900 truncate group-hover:text-indigo-600 transition-colors">
                        {{ prod.productName }}
                      </div>
                      <div class="text-sm text-slate-500 truncate mt-1.5">
                        {{ prod.subTitle || '高性能AI终端' }}
                      </div>
                    </div>
                    <el-icon class="text-slate-300 group-hover:text-indigo-500 transition-colors transform group-hover:translate-x-1"><Right /></el-icon>
                  </div>
                </div>

                <div v-else class="text-center py-12 bg-white rounded-2xl border border-dashed border-slate-200 text-slate-400 text-sm">
                  暂无关联硬件产品
                </div>

                <div class="mt-8 pt-6 border-t border-slate-200">
                   <button class="w-full py-4 bg-gradient-to-r from-indigo-600 to-indigo-500 hover:from-indigo-500 hover:to-indigo-600 text-white rounded-xl font-bold transition-all shadow-lg shadow-indigo-600/20 hover:shadow-indigo-600/30 active:scale-[0.98]">
                     获取详细报价方案
                   </button>
                </div>
              </div>
            </div>
          </div>

        </div>

      </div>
    </div>
  </div>
</template>

<style scoped>
/* 简单的进场动画 */
.animate-fade-in-up {
  animation: fadeInUp 0.6s ease-out forwards;
}
@keyframes fadeInUp {
  from { opacity: 0; transform: translateY(20px); }
  to { opacity: 1; transform: translateY(0); }
}

/* 针对富文本的简单优化 */
:deep(.prose img) {
  max-width: 100%;
}
:deep(.prose h3) {
  margin-top: 2.5rem;
  font-size: 1.5rem;
  color: #1e293b;
  padding-left: 1rem;
  border-left: 4px solid #6366f1; /* indigo-500 */
}
:deep(.prose ul) {
  list-style-type: disc;
  padding-left: 1.5rem;
  margin-bottom: 1.5rem;
}
:deep(.prose li) {
  margin-bottom: 0.5rem;
}
</style>