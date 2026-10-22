import request from '@/utils/request'

// 查询营销线索列表
export function listLead(query) {
  return request({
    url: '/biz/lead/list',
    method: 'get',
    params: query
  })
}

// 查询营销线索详细
export function getLead(leadId) {
  return request({
    url: '/biz/lead/' + leadId,
    method: 'get'
  })
}

// 新增营销线索
export function addLead(data) {
  return request({
    url: '/biz/lead',
    method: 'post',
    data: data
  })
}

// 修改营销线索
export function updateLead(data) {
  return request({
    url: '/biz/lead',
    method: 'put',
    data: data
  })
}

// 删除营销线索
export function delLead(leadId) {
  return request({
    url: '/biz/lead/' + leadId,
    method: 'delete'
  })
}
