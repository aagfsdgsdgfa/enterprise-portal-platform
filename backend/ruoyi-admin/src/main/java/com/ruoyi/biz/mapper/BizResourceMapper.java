package com.ruoyi.biz.mapper;

import java.util.List;
import com.ruoyi.biz.domain.BizResource;

/**
 * 资料下载中心Mapper接口
 * 
 * @author 杨正知
 * @date 2025-12-08
 */
public interface BizResourceMapper 
{
    /**
     * 查询资料下载中心
     * 
     * @param resourceId 资料下载中心主键
     * @return 资料下载中心
     */
    public BizResource selectBizResourceByResourceId(Long resourceId);

    /**
     * 查询资料下载中心列表
     * 
     * @param bizResource 资料下载中心
     * @return 资料下载中心集合
     */
    public List<BizResource> selectBizResourceList(BizResource bizResource);

    /**
     * 新增资料下载中心
     * 
     * @param bizResource 资料下载中心
     * @return 结果
     */
    public int insertBizResource(BizResource bizResource);

    /**
     * 修改资料下载中心
     * 
     * @param bizResource 资料下载中心
     * @return 结果
     */
    public int updateBizResource(BizResource bizResource);

    /**
     * 删除资料下载中心
     * 
     * @param resourceId 资料下载中心主键
     * @return 结果
     */
    public int deleteBizResourceByResourceId(Long resourceId);

    /**
     * 批量删除资料下载中心
     * 
     * @param resourceIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizResourceByResourceIds(Long[] resourceIds);
}
