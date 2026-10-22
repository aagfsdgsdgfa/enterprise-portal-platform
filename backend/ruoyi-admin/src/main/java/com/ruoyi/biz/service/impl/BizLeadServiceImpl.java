package com.ruoyi.biz.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.biz.mapper.BizLeadMapper;
import com.ruoyi.biz.domain.BizLead;
import com.ruoyi.biz.service.IBizLeadService;

/**
 * 营销线索Service业务层处理
 * 
 * @author 杨正知
 * @date 2025-12-08
 */
@Service
public class BizLeadServiceImpl implements IBizLeadService 
{
    @Autowired
    private BizLeadMapper bizLeadMapper;

    /**
     * 查询营销线索
     * 
     * @param leadId 营销线索主键
     * @return 营销线索
     */
    @Override
    public BizLead selectBizLeadByLeadId(Long leadId)
    {
        return bizLeadMapper.selectBizLeadByLeadId(leadId);
    }

    /**
     * 查询营销线索列表
     * 
     * @param bizLead 营销线索
     * @return 营销线索
     */
    @Override
    public List<BizLead> selectBizLeadList(BizLead bizLead)
    {
        return bizLeadMapper.selectBizLeadList(bizLead);
    }

    /**
     * 新增营销线索
     * 
     * @param bizLead 营销线索
     * @return 结果
     */
    @Override
    public int insertBizLead(BizLead bizLead)
    {
        bizLead.setCreateTime(DateUtils.getNowDate());
        return bizLeadMapper.insertBizLead(bizLead);
    }

    /**
     * 修改营销线索
     * 
     * @param bizLead 营销线索
     * @return 结果
     */
    @Override
    public int updateBizLead(BizLead bizLead)
    {
        bizLead.setUpdateTime(DateUtils.getNowDate());
        return bizLeadMapper.updateBizLead(bizLead);
    }

    /**
     * 批量删除营销线索
     * 
     * @param leadIds 需要删除的营销线索主键
     * @return 结果
     */
    @Override
    public int deleteBizLeadByLeadIds(Long[] leadIds)
    {
        return bizLeadMapper.deleteBizLeadByLeadIds(leadIds);
    }

    /**
     * 删除营销线索信息
     * 
     * @param leadId 营销线索主键
     * @return 结果
     */
    @Override
    public int deleteBizLeadByLeadId(Long leadId)
    {
        return bizLeadMapper.deleteBizLeadByLeadId(leadId);
    }
}
