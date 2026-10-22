package com.ruoyi.biz.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.biz.mapper.BizCompanyInfoMapper;
import com.ruoyi.biz.domain.BizCompanyInfo;
import com.ruoyi.biz.service.IBizCompanyInfoService;

/**
 * 企业基础信息Service业务层处理
 * 
 * @author 杨正知
 * @date 2025-12-08
 */
@Service
public class BizCompanyInfoServiceImpl implements IBizCompanyInfoService 
{
    @Autowired
    private BizCompanyInfoMapper bizCompanyInfoMapper;

    /**
     * 查询企业基础信息
     * 
     * @param infoId 企业基础信息主键
     * @return 企业基础信息
     */
    @Override
    public BizCompanyInfo selectBizCompanyInfoByInfoId(Long infoId)
    {
        return bizCompanyInfoMapper.selectBizCompanyInfoByInfoId(infoId);
    }

    /**
     * 查询企业基础信息列表
     * 
     * @param bizCompanyInfo 企业基础信息
     * @return 企业基础信息
     */
    @Override
    public List<BizCompanyInfo> selectBizCompanyInfoList(BizCompanyInfo bizCompanyInfo)
    {
        return bizCompanyInfoMapper.selectBizCompanyInfoList(bizCompanyInfo);
    }

    /**
     * 新增企业基础信息
     * 
     * @param bizCompanyInfo 企业基础信息
     * @return 结果
     */
    @Override
    public int insertBizCompanyInfo(BizCompanyInfo bizCompanyInfo)
    {
        return bizCompanyInfoMapper.insertBizCompanyInfo(bizCompanyInfo);
    }

    /**
     * 修改企业基础信息
     * 
     * @param bizCompanyInfo 企业基础信息
     * @return 结果
     */
    @Override
    public int updateBizCompanyInfo(BizCompanyInfo bizCompanyInfo)
    {
        bizCompanyInfo.setUpdateTime(DateUtils.getNowDate());
        return bizCompanyInfoMapper.updateBizCompanyInfo(bizCompanyInfo);
    }

    /**
     * 批量删除企业基础信息
     * 
     * @param infoIds 需要删除的企业基础信息主键
     * @return 结果
     */
    @Override
    public int deleteBizCompanyInfoByInfoIds(Long[] infoIds)
    {
        return bizCompanyInfoMapper.deleteBizCompanyInfoByInfoIds(infoIds);
    }

    /**
     * 删除企业基础信息信息
     * 
     * @param infoId 企业基础信息主键
     * @return 结果
     */
    @Override
    public int deleteBizCompanyInfoByInfoId(Long infoId)
    {
        return bizCompanyInfoMapper.deleteBizCompanyInfoByInfoId(infoId);
    }
}
