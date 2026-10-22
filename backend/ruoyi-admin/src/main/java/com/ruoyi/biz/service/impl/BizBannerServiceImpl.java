package com.ruoyi.biz.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.biz.mapper.BizBannerMapper;
import com.ruoyi.biz.domain.BizBanner;
import com.ruoyi.biz.service.IBizBannerService;

/**
 * 轮播图管理Service业务层处理
 * 
 * @author 杨正知
 * @date 2025-12-08
 */
@Service
public class BizBannerServiceImpl implements IBizBannerService 
{
    @Autowired
    private BizBannerMapper bizBannerMapper;

    /**
     * 查询轮播图管理
     * 
     * @param bannerId 轮播图管理主键
     * @return 轮播图管理
     */
    @Override
    public BizBanner selectBizBannerByBannerId(Long bannerId)
    {
        return bizBannerMapper.selectBizBannerByBannerId(bannerId);
    }

    /**
     * 查询轮播图管理列表
     * 
     * @param bizBanner 轮播图管理
     * @return 轮播图管理
     */
    @Override
    public List<BizBanner> selectBizBannerList(BizBanner bizBanner)
    {
        return bizBannerMapper.selectBizBannerList(bizBanner);
    }

    /**
     * 新增轮播图管理
     * 
     * @param bizBanner 轮播图管理
     * @return 结果
     */
    @Override
    public int insertBizBanner(BizBanner bizBanner)
    {
        bizBanner.setCreateTime(DateUtils.getNowDate());
        return bizBannerMapper.insertBizBanner(bizBanner);
    }

    /**
     * 修改轮播图管理
     * 
     * @param bizBanner 轮播图管理
     * @return 结果
     */
    @Override
    public int updateBizBanner(BizBanner bizBanner)
    {
        bizBanner.setUpdateTime(DateUtils.getNowDate());
        return bizBannerMapper.updateBizBanner(bizBanner);
    }

    /**
     * 批量删除轮播图管理
     * 
     * @param bannerIds 需要删除的轮播图管理主键
     * @return 结果
     */
    @Override
    public int deleteBizBannerByBannerIds(Long[] bannerIds)
    {
        return bizBannerMapper.deleteBizBannerByBannerIds(bannerIds);
    }

    /**
     * 删除轮播图管理信息
     * 
     * @param bannerId 轮播图管理主键
     * @return 结果
     */
    @Override
    public int deleteBizBannerByBannerId(Long bannerId)
    {
        return bizBannerMapper.deleteBizBannerByBannerId(bannerId);
    }
}
