import request from '@/utils/request'

// 查询职位招聘列表
export function listJob(query) {
  return request({
    url: '/biz/job/list',
    method: 'get',
    params: query
  })
}

// 查询职位招聘详细
export function getJob(jobId) {
  return request({
    url: '/biz/job/' + jobId,
    method: 'get'
  })
}

// 新增职位招聘
export function addJob(data) {
  return request({
    url: '/biz/job',
    method: 'post',
    data: data
  })
}

// 修改职位招聘
export function updateJob(data) {
  return request({
    url: '/biz/job',
    method: 'put',
    data: data
  })
}

// 删除职位招聘
export function delJob(jobId) {
  return request({
    url: '/biz/job/' + jobId,
    method: 'delete'
  })
}
