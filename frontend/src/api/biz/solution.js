import request from '@/utils/request'

// 查询行业解决方案列表
export function listSolution(query) {
  return request({
    url: '/biz/solution/list',
    method: 'get',
    params: query
  })
}

// 查询行业解决方案详细
export function getSolution(solutionId) {
  return request({
    url: '/biz/solution/' + solutionId,
    method: 'get'
  })
}

// 新增行业解决方案
export function addSolution(data) {
  return request({
    url: '/biz/solution',
    method: 'post',
    data: data
  })
}

// 修改行业解决方案
export function updateSolution(data) {
  return request({
    url: '/biz/solution',
    method: 'put',
    data: data
  })
}

// 删除行业解决方案
export function delSolution(solutionId) {
  return request({
    url: '/biz/solution/' + solutionId,
    method: 'delete'
  })
}
