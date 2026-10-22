package com.ruoyi.biz.mapper;

import java.util.List;
import com.ruoyi.biz.domain.BizJob;

/**
 * 职位招聘Mapper接口
 * 
 * @author 杨正知
 * @date 2025-12-08
 */
public interface BizJobMapper 
{
    /**
     * 查询职位招聘
     * 
     * @param jobId 职位招聘主键
     * @return 职位招聘
     */
    public BizJob selectBizJobByJobId(Long jobId);

    /**
     * 查询职位招聘列表
     * 
     * @param bizJob 职位招聘
     * @return 职位招聘集合
     */
    public List<BizJob> selectBizJobList(BizJob bizJob);

    /**
     * 新增职位招聘
     * 
     * @param bizJob 职位招聘
     * @return 结果
     */
    public int insertBizJob(BizJob bizJob);

    /**
     * 修改职位招聘
     * 
     * @param bizJob 职位招聘
     * @return 结果
     */
    public int updateBizJob(BizJob bizJob);

    /**
     * 删除职位招聘
     * 
     * @param jobId 职位招聘主键
     * @return 结果
     */
    public int deleteBizJobByJobId(Long jobId);

    /**
     * 批量删除职位招聘
     * 
     * @param jobIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizJobByJobIds(Long[] jobIds);
}
