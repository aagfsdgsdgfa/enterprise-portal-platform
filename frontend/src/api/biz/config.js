import request from '@/utils/request'

// 查询首页通用配置列表
export function listConfig(query) {
  return request({
    url: '/biz/config/list',
    method: 'get',
    params: query
  })
}

// 查询首页通用配置详细
export function getConfig(configId) {
  return request({
    url: '/biz/config/' + configId,
    method: 'get'
  })
}

// 新增首页通用配置
export function addConfig(data) {
  return request({
    url: '/biz/config',
    method: 'post',
    data: data
  })
}

// 修改首页通用配置
export function updateConfig(data) {
  return request({
    url: '/biz/config',
    method: 'put',
    data: data
  })
}

// 删除首页通用配置
export function delConfig(configId) {
  return request({
    url: '/biz/config/' + configId,
    method: 'delete'
  })
}
