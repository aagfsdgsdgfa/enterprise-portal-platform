package com.ruoyi.biz.mapper;

import java.util.List;
import com.ruoyi.biz.domain.BizBanner;

/**
 * 轮播图管理Mapper接口
 * 
 * @author 杨正知
 * @date 2025-12-08
 */
public interface BizBannerMapper 
{
    /**
     * 查询轮播图管理
     * 
     * @param bannerId 轮播图管理主键
     * @return 轮播图管理
     */
    public BizBanner selectBizBannerByBannerId(Long bannerId);

    /**
     * 查询轮播图管理列表
     * 
     * @param bizBanner 轮播图管理
     * @return 轮播图管理集合
     */
    public List<BizBanner> selectBizBannerList(BizBanner bizBanner);

    /**
     * 新增轮播图管理
     * 
     * @param bizBanner 轮播图管理
     * @return 结果
     */
    public int insertBizBanner(BizBanner bizBanner);

    /**
     * 修改轮播图管理
     * 
     * @param bizBanner 轮播图管理
     * @return 结果
     */
    public int updateBizBanner(BizBanner bizBanner);

    /**
     * 删除轮播图管理
     * 
     * @param bannerId 轮播图管理主键
     * @return 结果
     */
    public int deleteBizBannerByBannerId(Long bannerId);

    /**
     * 批量删除轮播图管理
     * 
     * @param bannerIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizBannerByBannerIds(Long[] bannerIds);
}
