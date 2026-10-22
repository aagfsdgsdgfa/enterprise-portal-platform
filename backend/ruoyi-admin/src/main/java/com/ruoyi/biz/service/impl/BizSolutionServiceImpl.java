package com.ruoyi.biz.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.biz.mapper.BizSolutionMapper;
import com.ruoyi.biz.domain.BizSolution;
import com.ruoyi.biz.service.IBizSolutionService;

/**
 * 行业解决方案Service业务层处理
 * 
 * @author 杨正知
 * @date 2025-12-09
 */
@Service
public class BizSolutionServiceImpl implements IBizSolutionService 
{
    @Autowired
    private BizSolutionMapper bizSolutionMapper;

    /**
     * 查询行业解决方案
     * 
     * @param solutionId 行业解决方案主键
     * @return 行业解决方案
     */
    @Override
    public BizSolution selectBizSolutionBySolutionId(Long solutionId)
    {
        return bizSolutionMapper.selectBizSolutionBySolutionId(solutionId);
    }

    /**
     * 查询行业解决方案列表
     * 
     * @param bizSolution 行业解决方案
     * @return 行业解决方案
     */
    @Override
    public List<BizSolution> selectBizSolutionList(BizSolution bizSolution)
    {
        return bizSolutionMapper.selectBizSolutionList(bizSolution);
    }

    /**
     * 新增行业解决方案
     * 
     * @param bizSolution 行业解决方案
     * @return 结果
     */
    @Override
    public int insertBizSolution(BizSolution bizSolution)
    {
        bizSolution.setCreateTime(DateUtils.getNowDate());
        return bizSolutionMapper.insertBizSolution(bizSolution);
    }

    /**
     * 修改行业解决方案
     * 
     * @param bizSolution 行业解决方案
     * @return 结果
     */
    @Override
    public int updateBizSolution(BizSolution bizSolution)
    {
        bizSolution.setUpdateTime(DateUtils.getNowDate());
        return bizSolutionMapper.updateBizSolution(bizSolution);
    }

    /**
     * 批量删除行业解决方案
     * 
     * @param solutionIds 需要删除的行业解决方案主键
     * @return 结果
     */
    @Override
    public int deleteBizSolutionBySolutionIds(Long[] solutionIds)
    {
        return bizSolutionMapper.deleteBizSolutionBySolutionIds(solutionIds);
    }

    /**
     * 删除行业解决方案信息
     * 
     * @param solutionId 行业解决方案主键
     * @return 结果
     */
    @Override
    public int deleteBizSolutionBySolutionId(Long solutionId)
    {
        return bizSolutionMapper.deleteBizSolutionBySolutionId(solutionId);
    }
}
