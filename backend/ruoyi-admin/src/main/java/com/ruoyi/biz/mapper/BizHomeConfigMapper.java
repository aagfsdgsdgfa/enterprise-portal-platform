package com.ruoyi.biz.mapper;

import java.util.List;
import com.ruoyi.biz.domain.BizHomeConfig;

/**
 * 首页通用配置Mapper接口
 * 
 * @author 杨正知
 * @date 2025-12-09
 */
public interface BizHomeConfigMapper 
{
    /**
     * 查询首页通用配置
     * 
     * @param configId 首页通用配置主键
     * @return 首页通用配置
     */
    public BizHomeConfig selectBizHomeConfigByConfigId(Long configId);

    /**
     * 查询首页通用配置列表
     * 
     * @param bizHomeConfig 首页通用配置
     * @return 首页通用配置集合
     */
    public List<BizHomeConfig> selectBizHomeConfigList(BizHomeConfig bizHomeConfig);

    /**
     * 新增首页通用配置
     * 
     * @param bizHomeConfig 首页通用配置
     * @return 结果
     */
    public int insertBizHomeConfig(BizHomeConfig bizHomeConfig);

    /**
     * 修改首页通用配置
     * 
     * @param bizHomeConfig 首页通用配置
     * @return 结果
     */
    public int updateBizHomeConfig(BizHomeConfig bizHomeConfig);

    /**
     * 删除首页通用配置
     * 
     * @param configId 首页通用配置主键
     * @return 结果
     */
    public int deleteBizHomeConfigByConfigId(Long configId);

    /**
     * 批量删除首页通用配置
     * 
     * @param configIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizHomeConfigByConfigIds(Long[] configIds);
}
