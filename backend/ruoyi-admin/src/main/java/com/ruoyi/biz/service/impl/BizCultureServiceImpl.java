package com.ruoyi.biz.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.biz.mapper.BizCultureMapper;
import com.ruoyi.biz.domain.BizCulture;
import com.ruoyi.biz.service.IBizCultureService;

/**
 * 企业文化Service业务层处理
 * 
 * @author 杨正知
 * @date 2025-12-08
 */
@Service
public class BizCultureServiceImpl implements IBizCultureService 
{
    @Autowired
    private BizCultureMapper bizCultureMapper;

    /**
     * 查询企业文化
     * 
     * @param cultureId 企业文化主键
     * @return 企业文化
     */
    @Override
    public BizCulture selectBizCultureByCultureId(Long cultureId)
    {
        return bizCultureMapper.selectBizCultureByCultureId(cultureId);
    }

    /**
     * 查询企业文化列表
     * 
     * @param bizCulture 企业文化
     * @return 企业文化
     */
    @Override
    public List<BizCulture> selectBizCultureList(BizCulture bizCulture)
    {
        return bizCultureMapper.selectBizCultureList(bizCulture);
    }

    /**
     * 新增企业文化
     * 
     * @param bizCulture 企业文化
     * @return 结果
     */
    @Override
    public int insertBizCulture(BizCulture bizCulture)
    {
        bizCulture.setCreateTime(DateUtils.getNowDate());
        return bizCultureMapper.insertBizCulture(bizCulture);
    }

    /**
     * 修改企业文化
     * 
     * @param bizCulture 企业文化
     * @return 结果
     */
    @Override
    public int updateBizCulture(BizCulture bizCulture)
    {
        bizCulture.setUpdateTime(DateUtils.getNowDate());
        return bizCultureMapper.updateBizCulture(bizCulture);
    }

    /**
     * 批量删除企业文化
     * 
     * @param cultureIds 需要删除的企业文化主键
     * @return 结果
     */
    @Override
    public int deleteBizCultureByCultureIds(Long[] cultureIds)
    {
        return bizCultureMapper.deleteBizCultureByCultureIds(cultureIds);
    }

    /**
     * 删除企业文化信息
     * 
     * @param cultureId 企业文化主键
     * @return 结果
     */
    @Override
    public int deleteBizCultureByCultureId(Long cultureId)
    {
        return bizCultureMapper.deleteBizCultureByCultureId(cultureId);
    }
}
