<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getProduct } from '../../api/website'
import { getImageUrl } from '../../utils/common'
import { ArrowLeft, Download, ChatDotRound, Setting, Cpu, Files } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const product = ref({})
const loading = ref(true)
const activeTab = ref('solution') 

// --- 1. 痛点标签处理 ---
const painPointTags = computed(() => {
  if (!product.value.painPoints) return []
  return product.value.painPoints.split(/[;；]/).filter(t => t.trim())
})

// 获取数据
onMounted(async () => {
  const id = route.params.id
  if (!id) return
  
  try {
    loading.value = true
    const res = await getProduct(id)
    if (res.data) {
      product.value = res.data
    } else {
      product.value = res 
    }
  } catch (e) {
    console.error('获取详情失败:', e)
  } finally {
    loading.value = false
  }
})

const goBack = () => router.back()

// 【新增】跳转联系我们
const goContact = () => {
  router.push('/contact')
}
</script>

<template>
  <div class="bg-white min-h-screen pb-20 font-sans">
    
    <div class="h-20 bg-slate-900"></div>

    <div v-if="loading" class="h-[60vh] flex items-center justify-center">
      <div class="flex flex-col items-center gap-4">
        <div class="w-10 h-10 border-4 border-blue-600 border-t-transparent rounded-full animate-spin"></div>
        <p class="text-slate-400 text-sm">正在加载产品详情...</p>
      </div>
    </div>

    <div v-else class="animate-fade-in">
      
      <div class="container mx-auto px-6 py-8">
        <button @click="goBack" class="flex items-center gap-2 text-slate-500 hover:text-blue-600 transition-colors mb-6 group text-sm font-medium">
          <el-icon class="group-hover:-translate-x-1 transition-transform"><ArrowLeft /></el-icon>
          返回产品列表
        </button>
      </div>

      <div class="container mx-auto px-6 mb-20">
        <div class="flex flex-col lg:flex-row gap-12 items-start">
          
          <div class="lg:w-1/2 w-full">
            <div class="relative rounded-3xl overflow-hidden shadow-2xl border border-slate-100 bg-slate-50 aspect-[4/3] group">
              <img 
                :src="getImageUrl(product.mainImg)" 
                class="w-full h-full object-cover transition-transform duration-700 group-hover:scale-105"
              />
              <div class="absolute inset-0 bg-gradient-to-t from-slate-900/40 via-transparent to-transparent"></div>
              
              <div class="absolute top-6 left-6 bg-white/90 backdrop-blur px-4 py-1.5 rounded-full text-xs font-bold text-slate-900 shadow-lg">
                ID: {{ product.productId }}
              </div>
            </div>
          </div>

          <div class="lg:w-1/2 w-full pt-2">
            <div class="inline-flex items-center gap-2 px-3 py-1 bg-blue-50 text-blue-700 rounded-full text-xs font-bold mb-6 border border-blue-100 uppercase tracking-wider">
               <span class="w-2 h-2 rounded-full bg-blue-500 animate-pulse"></span>
               AI PRODUCT / 0{{ product.categoryId }}
            </div>

            <h1 class="text-4xl md:text-5xl font-bold text-slate-900 mb-4 leading-tight">
              {{ product.productName }}
            </h1>
            
            <p class="text-lg text-blue-600 font-medium mb-8" v-if="product.subTitle">
              {{ product.subTitle }}
            </p>

            <p class="text-slate-500 mb-8 leading-relaxed text-lg">
              {{ product.summary }}
            </p>
            
            <div class="mb-10" v-if="painPointTags.length > 0">
               <p class="text-xs font-bold text-slate-400 uppercase tracking-wider mb-3">解决痛点 / Pain Points</p>
               <div class="flex flex-wrap gap-2">
                 <span 
                    v-for="(tag, index) in painPointTags" 
                    :key="index"
                    class="px-3 py-1.5 bg-red-50 text-red-600 border border-red-100 rounded-lg text-sm font-medium flex items-center gap-1.5"
                 >
                    <el-icon><ChatDotRound /></el-icon> {{ tag }}
                 </span>
               </div>
            </div>

            <div class="flex flex-col sm:flex-row gap-4">
              <button 
                @click="goContact"
                class="flex-1 bg-slate-900 hover:bg-blue-600 text-white py-4 rounded-xl font-bold transition-all shadow-xl shadow-slate-200 hover:shadow-blue-600/30 flex items-center justify-center gap-2"
              >
                <el-icon><ChatDotRound /></el-icon> 立即咨询方案
              </button>
              
              <button class="flex-1 bg-white border border-slate-200 hover:border-slate-400 text-slate-700 hover:text-slate-900 py-4 rounded-xl font-bold transition-all flex items-center justify-center gap-2">
                <el-icon><Files /></el-icon> 下载产品白皮书
              </button>
            </div>
          </div>
        </div>
      </div>

      <div class="bg-slate-50 py-20 border-t border-slate-200">
        <div class="container mx-auto px-6 max-w-5xl">
          
          <div class="flex justify-center mb-12">
            <div class="bg-white p-1.5 rounded-full shadow-sm border border-slate-200 inline-flex">
              <button 
                @click="activeTab = 'solution'"
                class="px-8 py-2.5 rounded-full text-sm font-bold transition-all flex items-center gap-2"
                :class="activeTab === 'solution' ? 'bg-blue-600 text-white shadow-md' : 'text-slate-500 hover:text-slate-900'"
              >
                <el-icon><Setting /></el-icon> 解决方案详情
              </button>
              <button 
                @click="activeTab = 'specs'"
                class="px-8 py-2.5 rounded-full text-sm font-bold transition-all flex items-center gap-2"
                :class="activeTab === 'specs' ? 'bg-blue-600 text-white shadow-md' : 'text-slate-500 hover:text-slate-900'"
              >
                <el-icon><Cpu /></el-icon> 技术参数规格
              </button>
            </div>
          </div>

          <div class="bg-white rounded-[2rem] p-8 md:p-16 shadow-xl shadow-slate-200/40 min-h-[400px]">
            
            <div v-show="activeTab === 'solution'" class="animate-fade-in-up">
              <div 
                class="prose prose-lg max-w-none prose-headings:font-bold prose-headings:text-slate-900 prose-p:text-slate-600 prose-img:rounded-2xl prose-a:text-blue-600" 
                v-html="product.solutions || '<p class=\'text-center text-slate-300 italic py-10\'>暂无详细方案描述</p>'"
              ></div>
              
              <div v-if="product.techArchImg" class="mt-16 pt-12 border-t border-slate-100">
                <h3 class="text-xl font-bold mb-8 flex items-center gap-2 text-slate-900">
                   <span class="w-1 h-6 bg-blue-600 rounded-full"></span>
                   技术架构图示 / Architecture
                </h3>
                <div class="bg-slate-50 rounded-2xl p-4 border border-slate-100">
                  <img :src="getImageUrl(product.techArchImg)" class="w-full rounded-xl" />
                </div>
              </div>
            </div>

            <div v-show="activeTab === 'specs'" class="animate-fade-in-up">
              <div 
                class="prose prose-slate max-w-none" 
                v-html="product.techSpecs || '<p class=\'text-center text-slate-300 italic py-10\'>暂无具体参数规格</p>'"
              ></div>
            </div>

          </div>

        </div>
      </div>

    </div>
  </div>
</template>

<style scoped>
/* 简单的淡入动画 */
.animate-fade-in {
  animation: fadeIn 0.6s ease-out;
}
.animate-fade-in-up {
  animation: fadeInUp 0.4s ease-out forwards;
}

@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}
@keyframes fadeInUp {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}

/* 即使没有 tailwind-typography 插件，保证图片不溢出 */
:deep(.prose img) {
  max-width: 100%;
  height: auto;
  border-radius: 12px;
}
</style>