import request from '../utils/request'

// 获取轮播图
export function listBanners() {
  return request({
    url: '/biz/banner/list',
    method: 'get',
    params: { status: '0' } 
  })
}

// 获取新闻列表
export function listArticles(query) {
  return request({
    url: '/biz/article/list',
    method: 'get',
     params: {
      ...query,
      status: '0' 
    }
  })
}

// 获取产品列表
export function listProducts() {
  return request({
    url: '/biz/product/list',
    method: 'get',
    params: { status: '0' } 
  })
}
// 获取产品详情
export function getProduct(productId) {
  return request({
    url: '/biz/product/' + productId,
    method: 'get'
  })}

// 提交线索 (招商留言)
export function addLead(data) {
  return request({
    url: '/biz/lead',
    method: 'post',
    data: data
  })
}


export function getArticle(articleId) {
  return request({
    url: '/biz/article/' + articleId,
    method: 'get',
    params: { status: '0' } 
  })
}

// 获取企业基础信息 (通常取列表的第一条作为主信息)
export function listCompanyInfo() {
  return request({
    url: '/biz/info/list',
    method: 'get'
  })
}

// 获取企业文化列表
export function listCultures() {
  return request({
    url: '/biz/culture/list',
    method: 'get'
  })
}

// 1. 获取解决方案列表
export function listSolutions() {
  return request({
    url: '/biz/solution/list',
    method: 'get',
    params: { status: '0' } 
  })
}

// 2. 获取解决方案详情
export function getSolution(solutionId) {
  return request({
    url: '/biz/solution/' + solutionId,
    method: 'get'
  })
}

// 1. 获取项目列表
export function listProjects(query) {
  return request({
    url: '/biz/project/list',
    method: 'get',
     params: {
      ...query,
      status: '0' 
    }
  })
}

// 2. 获取项目详情
export function getProject(projectId) {
  return request({
    url: '/biz/project/' + projectId,
    method: 'get'
  })
}

// 1. 获取职位列表
export function listJobs(query) {
  return request({
    url: '/biz/job/list',
    method: 'get',
     params: {
      ...query,
      status: '0' 
    }
  })
}

// 2. 获取职位详情
export function getJob(jobId) {
  return request({
    url: '/biz/job/' + jobId,
    method: 'get'
  })
}

// 获取首页配置
export function listHomeConfigs() {
  return request({
    url: '/biz/config/list',
    method: 'get',
    params: { pageNum: 1, pageSize: 100 } 
  })
}

export function submitLead(data) {
  return request({
    url: '/biz/lead/submit',
    method: 'post',
    data: data
  })
}