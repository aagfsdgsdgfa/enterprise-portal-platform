package com.ruoyi.biz.mapper;

import java.util.List;
import com.ruoyi.biz.domain.BizSolution;

/**
 * 行业解决方案Mapper接口
 * 
 * @author 杨正知
 * @date 2025-12-09
 */
public interface BizSolutionMapper 
{
    /**
     * 查询行业解决方案
     * 
     * @param solutionId 行业解决方案主键
     * @return 行业解决方案
     */
    public BizSolution selectBizSolutionBySolutionId(Long solutionId);

    /**
     * 查询行业解决方案列表
     * 
     * @param bizSolution 行业解决方案
     * @return 行业解决方案集合
     */
    public List<BizSolution> selectBizSolutionList(BizSolution bizSolution);

    /**
     * 新增行业解决方案
     * 
     * @param bizSolution 行业解决方案
     * @return 结果
     */
    public int insertBizSolution(BizSolution bizSolution);

    /**
     * 修改行业解决方案
     * 
     * @param bizSolution 行业解决方案
     * @return 结果
     */
    public int updateBizSolution(BizSolution bizSolution);

    /**
     * 删除行业解决方案
     * 
     * @param solutionId 行业解决方案主键
     * @return 结果
     */
    public int deleteBizSolutionBySolutionId(Long solutionId);

    /**
     * 批量删除行业解决方案
     * 
     * @param solutionIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizSolutionBySolutionIds(Long[] solutionIds);
}
