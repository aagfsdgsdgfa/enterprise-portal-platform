import request from '@/utils/request'

// 查询企业基础信息列表
export function listInfo(query) {
  return request({
    url: '/biz/info/list',
    method: 'get',
    params: query
  })
}

// 查询企业基础信息详细
export function getInfo(infoId) {
  return request({
    url: '/biz/info/' + infoId,
    method: 'get'
  })
}

// 新增企业基础信息
export function addInfo(data) {
  return request({
    url: '/biz/info',
    method: 'post',
    data: data
  })
}

// 修改企业基础信息
export function updateInfo(data) {
  return request({
    url: '/biz/info',
    method: 'put',
    data: data
  })
}

// 删除企业基础信息
export function delInfo(infoId) {
  return request({
    url: '/biz/info/' + infoId,
    method: 'delete'
  })
}
