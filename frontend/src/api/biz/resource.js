import request from '@/utils/request'

// 查询资料下载中心列表
export function listResource(query) {
  return request({
    url: '/biz/resource/list',
    method: 'get',
    params: query
  })
}

// 查询资料下载中心详细
export function getResource(resourceId) {
  return request({
    url: '/biz/resource/' + resourceId,
    method: 'get'
  })
}

// 新增资料下载中心
export function addResource(data) {
  return request({
    url: '/biz/resource',
    method: 'post',
    data: data
  })
}

// 修改资料下载中心
export function updateResource(data) {
  return request({
    url: '/biz/resource',
    method: 'put',
    data: data
  })
}

// 删除资料下载中心
export function delResource(resourceId) {
  return request({
    url: '/biz/resource/' + resourceId,
    method: 'delete'
  })
}
