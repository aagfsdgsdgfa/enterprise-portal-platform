package com.ruoyi.biz.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.biz.mapper.BizHomeConfigMapper;
import com.ruoyi.biz.domain.BizHomeConfig;
import com.ruoyi.biz.service.IBizHomeConfigService;

/**
 * 首页通用配置Service业务层处理
 * 
 * @author 杨正知
 * @date 2025-12-09
 */
@Service
public class BizHomeConfigServiceImpl implements IBizHomeConfigService 
{
    @Autowired
    private BizHomeConfigMapper bizHomeConfigMapper;

    /**
     * 查询首页通用配置
     * 
     * @param configId 首页通用配置主键
     * @return 首页通用配置
     */
    @Override
    public BizHomeConfig selectBizHomeConfigByConfigId(Long configId)
    {
        return bizHomeConfigMapper.selectBizHomeConfigByConfigId(configId);
    }

    /**
     * 查询首页通用配置列表
     * 
     * @param bizHomeConfig 首页通用配置
     * @return 首页通用配置
     */
    @Override
    public List<BizHomeConfig> selectBizHomeConfigList(BizHomeConfig bizHomeConfig)
    {
        return bizHomeConfigMapper.selectBizHomeConfigList(bizHomeConfig);
    }

    /**
     * 新增首页通用配置
     * 
     * @param bizHomeConfig 首页通用配置
     * @return 结果
     */
    @Override
    public int insertBizHomeConfig(BizHomeConfig bizHomeConfig)
    {
        bizHomeConfig.setCreateTime(DateUtils.getNowDate());
        return bizHomeConfigMapper.insertBizHomeConfig(bizHomeConfig);
    }

    /**
     * 修改首页通用配置
     * 
     * @param bizHomeConfig 首页通用配置
     * @return 结果
     */
    @Override
    public int updateBizHomeConfig(BizHomeConfig bizHomeConfig)
    {
        bizHomeConfig.setUpdateTime(DateUtils.getNowDate());
        return bizHomeConfigMapper.updateBizHomeConfig(bizHomeConfig);
    }

    /**
     * 批量删除首页通用配置
     * 
     * @param configIds 需要删除的首页通用配置主键
     * @return 结果
     */
    @Override
    public int deleteBizHomeConfigByConfigIds(Long[] configIds)
    {
        return bizHomeConfigMapper.deleteBizHomeConfigByConfigIds(configIds);
    }

    /**
     * 删除首页通用配置信息
     * 
     * @param configId 首页通用配置主键
     * @return 结果
     */
    @Override
    public int deleteBizHomeConfigByConfigId(Long configId)
    {
        return bizHomeConfigMapper.deleteBizHomeConfigByConfigId(configId);
    }
}
