import request from '@/utils/request'

// 查询企业文化列表
export function listCulture(query) {
  return request({
    url: '/biz/culture/list',
    method: 'get',
    params: query
  })
}

// 查询企业文化详细
export function getCulture(cultureId) {
  return request({
    url: '/biz/culture/' + cultureId,
    method: 'get'
  })
}

// 新增企业文化
export function addCulture(data) {
  return request({
    url: '/biz/culture',
    method: 'post',
    data: data
  })
}

// 修改企业文化
export function updateCulture(data) {
  return request({
    url: '/biz/culture',
    method: 'put',
    data: data
  })
}

// 删除企业文化
export function delCulture(cultureId) {
  return request({
    url: '/biz/culture/' + cultureId,
    method: 'delete'
  })
}
