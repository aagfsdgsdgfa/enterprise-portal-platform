import request from '@/utils/request'

// 查询项目案例列表
export function listProject(query) {
  return request({
    url: '/biz/project/list',
    method: 'get',
    params: query
  })
}

// 查询项目案例详细
export function getProject(projectId) {
  return request({
    url: '/biz/project/' + projectId,
    method: 'get'
  })
}

// 新增项目案例
export function addProject(data) {
  return request({
    url: '/biz/project',
    method: 'post',
    data: data
  })
}

// 修改项目案例
export function updateProject(data) {
  return request({
    url: '/biz/project',
    method: 'put',
    data: data
  })
}

// 删除项目案例
export function delProject(projectId) {
  return request({
    url: '/biz/project/' + projectId,
    method: 'delete'
  })
}
