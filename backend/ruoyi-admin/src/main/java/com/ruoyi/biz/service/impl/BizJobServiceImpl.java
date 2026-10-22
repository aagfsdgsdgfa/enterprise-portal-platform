package com.ruoyi.biz.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.biz.mapper.BizJobMapper;
import com.ruoyi.biz.domain.BizJob;
import com.ruoyi.biz.service.IBizJobService;

/**
 * 职位招聘Service业务层处理
 * 
 * @author 杨正知
 * @date 2025-12-08
 */
@Service
public class BizJobServiceImpl implements IBizJobService 
{
    @Autowired
    private BizJobMapper bizJobMapper;

    /**
     * 查询职位招聘
     * 
     * @param jobId 职位招聘主键
     * @return 职位招聘
     */
    @Override
    public BizJob selectBizJobByJobId(Long jobId)
    {
        return bizJobMapper.selectBizJobByJobId(jobId);
    }

    /**
     * 查询职位招聘列表
     * 
     * @param bizJob 职位招聘
     * @return 职位招聘
     */
    @Override
    public List<BizJob> selectBizJobList(BizJob bizJob)
    {
        return bizJobMapper.selectBizJobList(bizJob);
    }

    /**
     * 新增职位招聘
     * 
     * @param bizJob 职位招聘
     * @return 结果
     */
    @Override
    public int insertBizJob(BizJob bizJob)
    {
        bizJob.setCreateTime(DateUtils.getNowDate());
        return bizJobMapper.insertBizJob(bizJob);
    }

    /**
     * 修改职位招聘
     * 
     * @param bizJob 职位招聘
     * @return 结果
     */
    @Override
    public int updateBizJob(BizJob bizJob)
    {
        bizJob.setUpdateTime(DateUtils.getNowDate());
        return bizJobMapper.updateBizJob(bizJob);
    }

    /**
     * 批量删除职位招聘
     * 
     * @param jobIds 需要删除的职位招聘主键
     * @return 结果
     */
    @Override
    public int deleteBizJobByJobIds(Long[] jobIds)
    {
        return bizJobMapper.deleteBizJobByJobIds(jobIds);
    }

    /**
     * 删除职位招聘信息
     * 
     * @param jobId 职位招聘主键
     * @return 结果
     */
    @Override
    public int deleteBizJobByJobId(Long jobId)
    {
        return bizJobMapper.deleteBizJobByJobId(jobId);
    }
}
