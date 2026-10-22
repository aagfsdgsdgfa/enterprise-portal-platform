<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getArticle } from '../../api/website'
import { getImageUrl } from '../../utils/common'
import { Back, Clock, User, View, CollectionTag } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const articleId = route.params.id
const article = ref({})
const loading = ref(true)

// --- 1. 日期格式化 (优先用发布时间) ---
const displayDate = computed(() => {
  // 后端返回的 publishTime 已经是 "2025-12-08" 格式，直接用
  if (article.value.publishTime) return article.value.publishTime
  // 或者是 createTime "2025-12-08 17:44:36"，需要截取
  return article.value.createTime ? article.value.createTime.split(' ')[0] : ''
})

// --- 2. 分类名称映射 ---
const categoryLabel = computed(() => {
  const type = article.value.categoryType
  const map = {
    'news': '企业资讯',
    'notice': '通知公告',
    'industry': '行业动态'
  }
  return map[type] || 'NEWS CENTER'
})

// 获取详情
onMounted(async () => {
  if (!articleId) return
  loading.value = true
  try {
    const res = await getArticle(articleId)
    // 兼容 { code: 200, data: {...} } 或直接返回 data 的情况
    const data = res.data || res
    if (data) {
      article.value = data
    }
  } catch (e) {
    console.error("获取新闻详情失败:", e)
  } finally {
    loading.value = false
  }
})

// 返回
const goBack = () => router.back()
</script>

<template>
  <div class="bg-white min-h-screen pb-24 font-sans">
    
    <div class="fixed top-0 left-0 right-0 h-20 bg-white/90 backdrop-blur-md z-50 border-b border-slate-100 flex items-center transition-all">
      <div class="container mx-auto px-4 max-w-4xl flex items-center justify-between">
        <button @click="goBack" class="flex items-center text-slate-500 hover:text-blue-600 transition-colors gap-2 font-medium text-sm">
          <el-icon><Back /></el-icon> 返回列表
        </button>
        <span class="text-xs font-bold text-slate-900 tracking-[0.2em] uppercase hidden md:block">
          NOVATECH AI NEWS
        </span>
        <div class="w-16"></div> </div>
    </div>

    <div class="pt-32 container mx-auto px-4 max-w-3xl">
      
      <div v-if="loading" class="animate-pulse space-y-6">
        <div class="flex justify-center mb-8"><div class="h-4 bg-slate-100 rounded w-24"></div></div>
        <div class="h-10 bg-slate-100 rounded w-3/4 mx-auto mb-4"></div>
        <div class="h-10 bg-slate-100 rounded w-2/3 mx-auto mb-12"></div>
        <div class="h-64 bg-slate-100 rounded-2xl mb-8"></div>
        <div class="space-y-3">
          <div class="h-4 bg-slate-100 rounded w-full"></div>
          <div class="h-4 bg-slate-100 rounded w-full"></div>
          <div class="h-4 bg-slate-100 rounded w-5/6"></div>
        </div>
      </div>

      <div v-else class="article-content animate-fade-in">
        
        <header class="mb-12 text-center">
          <div class="flex items-center justify-center gap-3 text-xs font-bold text-blue-600 uppercase tracking-widest mb-6">
            <span class="px-2 py-1 bg-blue-50 rounded-md">{{ categoryLabel }}</span>
            <span class="w-1 h-1 rounded-full bg-slate-300"></span>
            <span class="text-slate-400">OFFICIAL</span>
          </div>
          
          <h1 class="text-3xl md:text-4xl lg:text-5xl font-bold text-slate-900 mb-8 leading-tight font-serif tracking-tight">
            {{ article.title }}
          </h1>

          <div class="flex flex-wrap items-center justify-center gap-6 text-slate-500 text-sm border-y border-slate-100 py-5">
            <span class="flex items-center gap-2">
              <el-icon class="text-blue-500"><User /></el-icon> 
              {{ article.author || '星曜AI' }}
            </span>
            <span class="flex items-center gap-2">
              <el-icon class="text-blue-500"><Clock /></el-icon> 
              {{ displayDate }}
            </span>
            <span v-if="article.viewCount" class="flex items-center gap-2">
              <el-icon class="text-blue-500"><View /></el-icon> 
              {{ article.viewCount }} 阅读
            </span>
          </div>
        </header>

        <div v-if="article.summary" class="mb-12 relative">
          <div class="absolute left-0 top-0 bottom-0 w-1 bg-blue-600 rounded-full"></div>
          <div class="pl-6 py-2">
            <p class="text-xl text-slate-600 font-medium leading-relaxed italic font-serif">
              "{{ article.summary }}"
            </p>
          </div>
        </div>

        <div v-if="article.coverImg" class="mb-14 rounded-2xl overflow-hidden shadow-2xl shadow-slate-200 border border-slate-100">
          <img :src="getImageUrl(article.coverImg)" class="w-full h-auto object-cover" />
          <div class="p-3 bg-slate-50 text-center text-xs text-slate-400 border-t border-slate-100">
            {{ article.title }} - 现场影像
          </div>
        </div>

        <div 
          class="prose prose-lg prose-slate max-w-none text-slate-700 leading-8"
          v-html="article.content"
        ></div>

        <div class="mt-16 pt-8 border-t border-slate-100 flex items-center justify-between text-slate-400 text-sm">
           <div class="flex items-center gap-2">
              <el-icon><CollectionTag /></el-icon>
              <span>{{ article.seoKeywords || '人工智能, 星曜AI' }}</span>
           </div>
           <div>© NOVATECH AI</div>
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
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}

/* 核心：优化 v-html 内部样式 */
/* 使用 :deep() 穿透 scoped 样式去影响 v-html 里的内容 */
:deep(.prose) {
  color: #334155;
}
:deep(.prose p) {
  margin-bottom: 1.5em;
  font-size: 1.125rem; /* 18px */
  line-height: 1.8;
  text-align: justify;
}
:deep(.prose img) {
  border-radius: 12px;
  margin: 2.5rem auto;
  box-shadow: 0 10px 30px -5px rgba(0, 0, 0, 0.1);
  max-width: 100%;
}
:deep(.prose h2) {
  font-size: 1.75rem;
  font-weight: 700;
  color: #0f172a;
  margin-top: 3rem;
  margin-bottom: 1rem;
  padding-left: 1rem;
  border-left: 4px solid #2563eb; /* 蓝色左边框标题 */
}
:deep(.prose h3) {
  font-size: 1.5rem;
  font-weight: 600;
  color: #1e293b;
  margin-top: 2rem;
}
:deep(.prose ul) {
  list-style-type: disc;
  padding-left: 1.5rem;
  margin-bottom: 1.5rem;
}
:deep(.prose blockquote) {
  border-left: 4px solid #e2e8f0;
  padding-left: 1rem;
  font-style: italic;
  color: #64748b;
  margin: 2rem 0;
}
</style>