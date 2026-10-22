<script setup>
import { ref, reactive } from 'vue'
import { submitLead } from '../../api/website'
import { ElMessage } from 'element-plus'
import { 
  Location, Phone, Message, Promotion, 
  User, OfficeBuilding, ChatDotRound 
} from '@element-plus/icons-vue'

const loading = ref(false)
const formRef = ref(null)

// 表单数据
const form = reactive({
  contactName: '',
  phone: '',
  companyName: '',
  email: '',
  intentProduct: '', // 意向产品
  message: ''
})

// 表单验证规则
const rules = {
  contactName: [{ required: true, message: '请输入您的称呼', trigger: 'blur' }],
  phone: [
    { required: true, message: '请输入联系电话', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
  ],
  message: [{ required: true, message: '请简单描述您的需求', trigger: 'blur' }]
}

// 意向产品选项 (也可以从后端字典获取)
const productOptions = [
  '边缘计算盒子 X1',
  '智慧城市大脑',
  '工业视觉质检方案',
  '定制化算法开发',
  '商务合作/代理',
  '其他'
]

// 提交逻辑
const handleSubmit = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        // 构造提交数据
        const submitData = {
          ...form,
          sourceUrl: window.location.href // 记录来源URL
        }
        
        const res = await submitLead(submitData)
        if (res.code === 200) {
          ElMessage.success('提交成功！我们的客户经理将尽快与您联系。')
          // 重置表单
          formRef.value.resetFields()
        } else {
          ElMessage.error(res.msg || '提交失败，请稍后重试')
        }
      } catch (e) {
        console.error(e)
        ElMessage.error('网络繁忙，请稍后重试')
      } finally {
        loading.value = false
      }
    }
  })
}
</script>

<template>
  <div class="min-h-screen bg-slate-50 font-sans pb-20">
    
    <div class="relative bg-slate-900 h-[400px] flex items-center justify-center overflow-hidden">
      <div class="absolute inset-0 bg-[url('https://grainy-gradients.vercel.app/noise.svg')] opacity-20"></div>
      <div class="absolute top-0 right-0 w-[600px] h-[600px] bg-blue-600/20 blur-[120px] rounded-full translate-x-1/2 -translate-y-1/2"></div>
      
      <div class="relative z-10 text-center px-4 animate-fade-in-up">
        <h1 class="text-4xl md:text-6xl font-bold text-white mb-6 tracking-tight">
          联系 <span class="text-transparent bg-clip-text bg-gradient-to-r from-blue-400 to-teal-400">NovaTech AI</span>
        </h1>
        <p class="text-slate-400 text-lg max-w-2xl mx-auto">
          无论您有业务需求、技术咨询还是合作意向，我们随时准备为您服务。
        </p>
      </div>
    </div>

    <div class="container mx-auto px-4 md:px-6 -mt-24 relative z-20">
      <div class="bg-white rounded-[2rem] shadow-2xl overflow-hidden flex flex-col lg:flex-row min-h-[700px]">
        
        <div class="lg:w-2/5 bg-slate-900 p-10 md:p-14 text-white flex flex-col justify-between relative overflow-hidden">
          <div class="absolute bottom-0 right-0 w-64 h-64 bg-blue-600 rounded-full blur-[80px] opacity-40 translate-x-1/3 translate-y-1/3"></div>
          
          <div class="relative z-10">
            <h3 class="text-2xl font-bold mb-8">联系方式</h3>
            <div class="space-y-8">
              <div class="flex items-start gap-5">
                <div class="w-12 h-12 rounded-xl bg-blue-600/20 flex items-center justify-center text-blue-400 shrink-0">
                  <el-icon :size="20"><Phone /></el-icon>
                </div>
                <div>
                  <div class="text-xs text-slate-400 uppercase tracking-wider mb-1">商务热线</div>
                  <div class="text-xl font-bold text-white font-mono">400-800-8888</div>
                  <div class="text-sm text-slate-400 mt-1">周一至周五 9:00-18:00</div>
                </div>
              </div>

              <div class="flex items-start gap-5">
                <div class="w-12 h-12 rounded-xl bg-teal-600/20 flex items-center justify-center text-teal-400 shrink-0">
                  <el-icon :size="20"><Message /></el-icon>
                </div>
                <div>
                  <div class="text-xs text-slate-400 uppercase tracking-wider mb-1">电子邮箱</div>
                  <div class="text-lg font-medium text-white">contact@novatech.example.com</div>
                </div>
              </div>

              <div class="flex items-start gap-5">
                <div class="w-12 h-12 rounded-xl bg-purple-600/20 flex items-center justify-center text-purple-400 shrink-0">
                  <el-icon :size="20"><Location /></el-icon>
                </div>
                <div>
                  <div class="text-xs text-slate-400 uppercase tracking-wider mb-1">总部地址</div>
                  <div class="text-base text-slate-300 leading-relaxed">
                    云南省昆明市高新区<br>高新智慧产业园A座10层
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div class="relative z-10 mt-12">
            <div class="p-6 bg-white/5 rounded-2xl border border-white/10 backdrop-blur-sm">
              <p class="text-slate-300 text-sm leading-relaxed italic">
                "致力于通过前沿AI技术为行业赋能，让智能触手可及。期待与您携手共创未来。"
              </p>
            </div>
          </div>
        </div>

        <div class="lg:w-3/5 p-10 md:p-14 bg-white">
          <div class="max-w-xl mx-auto">
            <h2 class="text-3xl font-bold text-slate-900 mb-2">发送留言</h2>
            <p class="text-slate-500 mb-10">请填写以下信息，我们的专家将在24小时内与您联系。</p>

            <el-form 
              ref="formRef" 
              :model="form" 
              :rules="rules" 
              label-position="top"
              size="large"
              class="space-y-6"
            >
              <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                <el-form-item label="您的称呼" prop="contactName">
                  <el-input 
                    v-model="form.contactName" 
                    placeholder="请输入姓名" 
                    :prefix-icon="User"
                  />
                </el-form-item>
                
                <el-form-item label="联系电话" prop="phone">
                  <el-input 
                    v-model="form.phone" 
                    placeholder="请输入手机号码" 
                    :prefix-icon="Phone"
                  />
                </el-form-item>
              </div>

              <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                <el-form-item label="公司名称" prop="companyName">
                  <el-input 
                    v-model="form.companyName" 
                    placeholder="请输入公司名称（选填）" 
                    :prefix-icon="OfficeBuilding"
                  />
                </el-form-item>

                <el-form-item label="电子邮箱" prop="email">
                  <el-input 
                    v-model="form.email" 
                    placeholder="请输入电子邮箱（选填）" 
                    :prefix-icon="Message"
                  />
                </el-form-item>
              </div>

              <el-form-item label="感兴趣的产品/服务" prop="intentProduct">
                <el-select 
                  v-model="form.intentProduct" 
                  placeholder="请选择感兴趣的领域" 
                  class="w-full"
                >
                  <el-option 
                    v-for="item in productOptions" 
                    :key="item" 
                    :label="item" 
                    :value="item" 
                  />
                </el-select>
              </el-form-item>

              <el-form-item label="需求描述" prop="message">
                <el-input
                  v-model="form.message"
                  type="textarea"
                  :rows="4"
                  placeholder="请简单描述您的具体需求或应用场景..."
                  resize="none"
                />
              </el-form-item>

              <div class="pt-4">
                <button 
                  @click.prevent="handleSubmit"
                  :disabled="loading"
                  class="w-full bg-blue-600 hover:bg-blue-500 text-white font-bold py-4 rounded-xl shadow-lg shadow-blue-600/30 transition-all flex items-center justify-center gap-2 disabled:opacity-70 disabled:cursor-not-allowed"
                >
                  <span v-if="loading" class="w-5 h-5 border-2 border-white/30 border-t-white rounded-full animate-spin"></span>
                  <span v-else>立即提交申请</span>
                  <el-icon v-if="!loading"><Promotion /></el-icon>
                </button>
                <p class="text-center text-xs text-slate-400 mt-4">
                  提交即表示您同意我们的隐私政策，您的信息将被严格保密。
                </p>
              </div>

            </el-form>
          </div>
        </div>

      </div>
    </div>
    
  </div>
</template>

<style scoped>
/* 覆盖 Element Plus 的默认样式，使其更现代化 */
:deep(.el-input__wrapper) {
  background-color: #f8fafc; /* slate-50 */
  box-shadow: none !important;
  border: 1px solid #e2e8f0; /* slate-200 */
  border-radius: 0.75rem; /* rounded-xl */
  padding: 0.75rem 1rem;
  transition: all 0.3s;
}

:deep(.el-input__wrapper:hover),
:deep(.el-input__wrapper.is-focus) {
  background-color: #fff;
  border-color: #3b82f6; /* blue-500 */
  box-shadow: 0 0 0 4px rgba(59, 130, 246, 0.1) !important;
}

:deep(.el-form-item__label) {
  color: #334155; /* slate-700 */
  font-weight: 600;
  padding-bottom: 0.5rem;
}

:deep(.el-textarea__inner) {
  background-color: #f8fafc;
  box-shadow: none !important;
  border: 1px solid #e2e8f0;
  border-radius: 0.75rem;
  padding: 1rem;
}
:deep(.el-textarea__inner:focus) {
  background-color: #fff;
  border-color: #3b82f6;
}

.animate-fade-in-up {
  animation: fadeInUp 0.8s ease-out forwards;
}
@keyframes fadeInUp {
  from { opacity: 0; transform: translateY(20px); }
  to { opacity: 1; transform: translateY(0); }
}
</style>