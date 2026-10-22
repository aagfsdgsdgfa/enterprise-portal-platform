package com.ruoyi.biz.service;

import java.util.List;
import com.ruoyi.biz.domain.BizProject;

/**
 * 项目案例Service接口
 * 
 * @author 杨正知
 * @date 2025-12-08
 */
public interface IBizProjectService 
{
    /**
     * 查询项目案例
     * 
     * @param projectId 项目案例主键
     * @return 项目案例
     */
    public BizProject selectBizProjectByProjectId(Long projectId);

    /**
     * 查询项目案例列表
     * 
     * @param bizProject 项目案例
     * @return 项目案例集合
     */
    public List<BizProject> selectBizProjectList(BizProject bizProject);

    /**
     * 新增项目案例
     * 
     * @param bizProject 项目案例
     * @return 结果
     */
    public int insertBizProject(BizProject bizProject);

    /**
     * 修改项目案例
     * 
     * @param bizProject 项目案例
     * @return 结果
     */
    public int updateBizProject(BizProject bizProject);

    /**
     * 批量删除项目案例
     * 
     * @param projectIds 需要删除的项目案例主键集合
     * @return 结果
     */
    public int deleteBizProjectByProjectIds(Long[] projectIds);

    /**
     * 删除项目案例信息
     * 
     * @param projectId 项目案例主键
     * @return 结果
     */
    public int deleteBizProjectByProjectId(Long projectId);
}
