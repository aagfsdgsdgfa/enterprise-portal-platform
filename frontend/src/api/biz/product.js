import request from '@/utils/request'

// 查询产品解决方案列表
export function listProduct(query) {
  return request({
    url: '/biz/product/list',
    method: 'get',
    params: query
  })
}

// 查询产品解决方案详细
export function getProduct(productId) {
  return request({
    url: '/biz/product/' + productId,
    method: 'get'
  })
}

// 新增产品解决方案
export function addProduct(data) {
  return request({
    url: '/biz/product',
    method: 'post',
    data: data
  })
}

// 修改产品解决方案
export function updateProduct(data) {
  return request({
    url: '/biz/product',
    method: 'put',
    data: data
  })
}

// 删除产品解决方案
export function delProduct(productId) {
  return request({
    url: '/biz/product/' + productId,
    method: 'delete'
  })
}
