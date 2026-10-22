package com.ruoyi.biz.mapper;

import java.util.List;
import com.ruoyi.biz.domain.BizCulture;

/**
 * 企业文化Mapper接口
 * 
 * @author 杨正知
 * @date 2025-12-08
 */
public interface BizCultureMapper 
{
    /**
     * 查询企业文化
     * 
     * @param cultureId 企业文化主键
     * @return 企业文化
     */
    public BizCulture selectBizCultureByCultureId(Long cultureId);

    /**
     * 查询企业文化列表
     * 
     * @param bizCulture 企业文化
     * @return 企业文化集合
     */
    public List<BizCulture> selectBizCultureList(BizCulture bizCulture);

    /**
     * 新增企业文化
     * 
     * @param bizCulture 企业文化
     * @return 结果
     */
    public int insertBizCulture(BizCulture bizCulture);

    /**
     * 修改企业文化
     * 
     * @param bizCulture 企业文化
     * @return 结果
     */
    public int updateBizCulture(BizCulture bizCulture);

    /**
     * 删除企业文化
     * 
     * @param cultureId 企业文化主键
     * @return 结果
     */
    public int deleteBizCultureByCultureId(Long cultureId);

    /**
     * 批量删除企业文化
     * 
     * @param cultureIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizCultureByCultureIds(Long[] cultureIds);
}
