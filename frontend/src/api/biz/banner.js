import request from '@/utils/request'

// 查询轮播图管理列表
export function listBanner(query) {
  return request({
    url: '/biz/banner/list',
    method: 'get',
    params: query
  })
}

// 查询轮播图管理详细
export function getBanner(bannerId) {
  return request({
    url: '/biz/banner/' + bannerId,
    method: 'get'
  })
}

// 新增轮播图管理
export function addBanner(data) {
  return request({
    url: '/biz/banner',
    method: 'post',
    data: data
  })
}

// 修改轮播图管理
export function updateBanner(data) {
  return request({
    url: '/biz/banner',
    method: 'put',
    data: data
  })
}

// 删除轮播图管理
export function delBanner(bannerId) {
  return request({
    url: '/biz/banner/' + bannerId,
    method: 'delete'
  })
}
