package com.ruoyi.biz.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.biz.mapper.BizResourceMapper;
import com.ruoyi.biz.domain.BizResource;
import com.ruoyi.biz.service.IBizResourceService;

/**
 * 资料下载中心Service业务层处理
 * 
 * @author 杨正知
 * @date 2025-12-08
 */
@Service
public class BizResourceServiceImpl implements IBizResourceService 
{
    @Autowired
    private BizResourceMapper bizResourceMapper;

    /**
     * 查询资料下载中心
     * 
     * @param resourceId 资料下载中心主键
     * @return 资料下载中心
     */
    @Override
    public BizResource selectBizResourceByResourceId(Long resourceId)
    {
        return bizResourceMapper.selectBizResourceByResourceId(resourceId);
    }

    /**
     * 查询资料下载中心列表
     * 
     * @param bizResource 资料下载中心
     * @return 资料下载中心
     */
    @Override
    public List<BizResource> selectBizResourceList(BizResource bizResource)
    {
        return bizResourceMapper.selectBizResourceList(bizResource);
    }

    /**
     * 新增资料下载中心
     * 
     * @param bizResource 资料下载中心
     * @return 结果
     */
    @Override
    public int insertBizResource(BizResource bizResource)
    {
        bizResource.setCreateTime(DateUtils.getNowDate());
        return bizResourceMapper.insertBizResource(bizResource);
    }

    /**
     * 修改资料下载中心
     * 
     * @param bizResource 资料下载中心
     * @return 结果
     */
    @Override
    public int updateBizResource(BizResource bizResource)
    {
        bizResource.setUpdateTime(DateUtils.getNowDate());
        return bizResourceMapper.updateBizResource(bizResource);
    }

    /**
     * 批量删除资料下载中心
     * 
     * @param resourceIds 需要删除的资料下载中心主键
     * @return 结果
     */
    @Override
    public int deleteBizResourceByResourceIds(Long[] resourceIds)
    {
        return bizResourceMapper.deleteBizResourceByResourceIds(resourceIds);
    }

    /**
     * 删除资料下载中心信息
     * 
     * @param resourceId 资料下载中心主键
     * @return 结果
     */
    @Override
    public int deleteBizResourceByResourceId(Long resourceId)
    {
        return bizResourceMapper.deleteBizResourceByResourceId(resourceId);
    }
}
