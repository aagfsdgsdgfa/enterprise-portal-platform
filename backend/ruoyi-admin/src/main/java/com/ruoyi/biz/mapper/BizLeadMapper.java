package com.ruoyi.biz.mapper;

import java.util.List;
import com.ruoyi.biz.domain.BizLead;

/**
 * 营销线索Mapper接口
 * 
 * @author 杨正知
 * @date 2025-12-08
 */
public interface BizLeadMapper 
{
    /**
     * 查询营销线索
     * 
     * @param leadId 营销线索主键
     * @return 营销线索
     */
    public BizLead selectBizLeadByLeadId(Long leadId);

    /**
     * 查询营销线索列表
     * 
     * @param bizLead 营销线索
     * @return 营销线索集合
     */
    public List<BizLead> selectBizLeadList(BizLead bizLead);

    /**
     * 新增营销线索
     * 
     * @param bizLead 营销线索
     * @return 结果
     */
    public int insertBizLead(BizLead bizLead);

    /**
     * 修改营销线索
     * 
     * @param bizLead 营销线索
     * @return 结果
     */
    public int updateBizLead(BizLead bizLead);

    /**
     * 删除营销线索
     * 
     * @param leadId 营销线索主键
     * @return 结果
     */
    public int deleteBizLeadByLeadId(Long leadId);

    /**
     * 批量删除营销线索
     * 
     * @param leadIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizLeadByLeadIds(Long[] leadIds);
}
