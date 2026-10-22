package com.ruoyi.biz.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.biz.mapper.BizProjectMapper;
import com.ruoyi.biz.domain.BizProject;
import com.ruoyi.biz.service.IBizProjectService;

/**
 * 项目案例Service业务层处理
 * 
 * @author 杨正知
 * @date 2025-12-08
 */
@Service
public class BizProjectServiceImpl implements IBizProjectService 
{
    @Autowired
    private BizProjectMapper bizProjectMapper;

    /**
     * 查询项目案例
     * 
     * @param projectId 项目案例主键
     * @return 项目案例
     */
    @Override
    public BizProject selectBizProjectByProjectId(Long projectId)
    {
        return bizProjectMapper.selectBizProjectByProjectId(projectId);
    }

    /**
     * 查询项目案例列表
     * 
     * @param bizProject 项目案例
     * @return 项目案例
     */
    @Override
    public List<BizProject> selectBizProjectList(BizProject bizProject)
    {
        return bizProjectMapper.selectBizProjectList(bizProject);
    }

    /**
     * 新增项目案例
     * 
     * @param bizProject 项目案例
     * @return 结果
     */
    @Override
    public int insertBizProject(BizProject bizProject)
    {
        bizProject.setCreateTime(DateUtils.getNowDate());
        return bizProjectMapper.insertBizProject(bizProject);
    }

    /**
     * 修改项目案例
     * 
     * @param bizProject 项目案例
     * @return 结果
     */
    @Override
    public int updateBizProject(BizProject bizProject)
    {
        bizProject.setUpdateTime(DateUtils.getNowDate());
        return bizProjectMapper.updateBizProject(bizProject);
    }

    /**
     * 批量删除项目案例
     * 
     * @param projectIds 需要删除的项目案例主键
     * @return 结果
     */
    @Override
    public int deleteBizProjectByProjectIds(Long[] projectIds)
    {
        return bizProjectMapper.deleteBizProjectByProjectIds(projectIds);
    }

    /**
     * 删除项目案例信息
     * 
     * @param projectId 项目案例主键
     * @return 结果
     */
    @Override
    public int deleteBizProjectByProjectId(Long projectId)
    {
        return bizProjectMapper.deleteBizProjectByProjectId(projectId);
    }
}
