import { createRouter, createWebHistory } from 'vue-router'

// 引入布局组件
import Layout from '../layout/index.vue'

// --- 引入页面组件 (懒加载) ---
const Home = () => import('../views/Home.vue')

// 产品模块
const ProductList = () => import('../views/product/index.vue')
const ProductDetail = () => import('../views/product/detail.vue')

const NewsList = () => import('../views/news/index.vue')
const NewsDetail = () => import('../views/news/detail.vue')
const AboutIndex = () => import('../views/about/index.vue')
const SolutionList = () => import('../views/solution/index.vue')
const SolutionDetail = () => import('../views/solution/detail.vue')
const ProjectList = () => import('../views/project/index.vue')
const ProjectDetail = () => import('../views/project/detail.vue')

const JobIndex = () => import('../views/job/index.vue')
const JobDetail = () => import('../views/job/detail.vue')
const ContactUs = () => import('../views/contact/index.vue')

const routes = [
  {
    path: '/',
    component: Layout,
    children: [
      {
        path: '',
        name: 'Home',
        component: Home,
        meta: { title: '首页' }
      },
      
      // --- 产品模块 ---
      {
        path: 'products',
        name: 'Products',
        component: ProductList,
        meta: { title: '产品中心' }
      },
      {
        path: 'product/:id',
        name: 'ProductDetail',
        component: ProductDetail,
        meta: { title: '产品详情' }
      },

      {
        path: 'news',
        name: 'News',
        component: NewsList,
        meta: { title: '新闻资讯' }
      },
      {
        // 动态路由，匹配 /news/101 这种格式
        path: 'news/:id',
        name: 'NewsDetail',
        component: NewsDetail,
        meta: { title: '资讯详情' }
      },
      {
  path: 'project',
  name: 'Projects',
  component: ProjectList,
  meta: { title: '成功案例' }
},
{
  path: 'project/:id',
  name: 'ProjectDetail',
  component: ProjectDetail,
  meta: { title: '案例详情' }
},
      {
  path: 'about',
  name: 'About',
  component: AboutIndex,
  meta: { title: '关于我们' },
},
{
  path: 'solutions',
  name: 'Solutions',
  component: SolutionList,
  meta: { title: '行业解决方案' }
},
{
  path: 'solution/:id',
  name: 'SolutionDetail',
  component: SolutionDetail,
  meta: { title: '方案详情' }
},
{
  path: 'jobs',
  name: 'Jobs',
  component: JobIndex,
  meta: { title: '加入我们' }
},
{
  path: 'job/:id',
  name: 'JobDetail',
  component: JobDetail,
  meta: { title: '职位详情' }
},
{
  path: 'contact',
  name: 'Contact',
  component: ContactUs,
  meta: { title: '联系合作' }
}
    ]
  },
  
  {
    path: '/:pathMatch(.*)*',
    redirect: '/'
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes,
  // 滚动行为：切换页面时自动回到顶部
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return { top: 0, behavior: 'smooth' } // 加了 smooth 让滚动更平滑
    }
  }
})

export default router