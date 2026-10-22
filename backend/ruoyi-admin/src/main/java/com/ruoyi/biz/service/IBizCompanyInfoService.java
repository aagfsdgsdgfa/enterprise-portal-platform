package com.ruoyi.biz.service;

import java.util.List;
import com.ruoyi.biz.domain.BizCompanyInfo;

/**
 * 企业基础信息Service接口
 * 
 * @author 杨正知
 * @date 2025-12-08
 */
public interface IBizCompanyInfoService 
{
    /**
     * 查询企业基础信息
     * 
     * @param infoId 企业基础信息主键
     * @return 企业基础信息
     */
    public BizCompanyInfo selectBizCompanyInfoByInfoId(Long infoId);

    /**
     * 查询企业基础信息列表
     * 
     * @param bizCompanyInfo 企业基础信息
     * @return 企业基础信息集合
     */
    public List<BizCompanyInfo> selectBizCompanyInfoList(BizCompanyInfo bizCompanyInfo);

    /**
     * 新增企业基础信息
     * 
     * @param bizCompanyInfo 企业基础信息
     * @return 结果
     */
    public int insertBizCompanyInfo(BizCompanyInfo bizCompanyInfo);

    /**
     * 修改企业基础信息
     * 
     * @param bizCompanyInfo 企业基础信息
     * @return 结果
     */
    public int updateBizCompanyInfo(BizCompanyInfo bizCompanyInfo);

    /**
     * 批量删除企业基础信息
     * 
     * @param infoIds 需要删除的企业基础信息主键集合
     * @return 结果
     */
    public int deleteBizCompanyInfoByInfoIds(Long[] infoIds);

    /**
     * 删除企业基础信息信息
     * 
     * @param infoId 企业基础信息主键
     * @return 结果
     */
    public int deleteBizCompanyInfoByInfoId(Long infoId);
}
