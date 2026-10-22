package com.ruoyi.biz.mapper;

import java.util.List;
import com.ruoyi.biz.domain.BizProject;

/**
 * 项目案例Mapper接口
 * 
 * @author 杨正知
 * @date 2025-12-08
 */
public interface BizProjectMapper 
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
     * 删除项目案例
     * 
     * @param projectId 项目案例主键
     * @return 结果
     */
    public int deleteBizProjectByProjectId(Long projectId);

    /**
     * 批量删除项目案例
     * 
     * @param projectIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizProjectByProjectIds(Long[] projectIds);
}
