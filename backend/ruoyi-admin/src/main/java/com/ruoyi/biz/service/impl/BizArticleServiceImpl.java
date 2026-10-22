package com.ruoyi.biz.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.biz.mapper.BizArticleMapper;
import com.ruoyi.biz.domain.BizArticle;
import com.ruoyi.biz.service.IBizArticleService;

/**
 * 文章资讯Service业务层处理
 * 
 * @author 杨正知
 * @date 2025-12-08
 */
@Service
public class BizArticleServiceImpl implements IBizArticleService 
{
    @Autowired
    private BizArticleMapper bizArticleMapper;

    /**
     * 查询文章资讯
     * 
     * @param articleId 文章资讯主键
     * @return 文章资讯
     */
    @Override
    public BizArticle selectBizArticleByArticleId(Long articleId)
    {
        return bizArticleMapper.selectBizArticleByArticleId(articleId);
    }

    /**
     * 查询文章资讯列表
     * 
     * @param bizArticle 文章资讯
     * @return 文章资讯
     */
    @Override
    public List<BizArticle> selectBizArticleList(BizArticle bizArticle)
    {
        return bizArticleMapper.selectBizArticleList(bizArticle);
    }

    /**
     * 新增文章资讯
     * 
     * @param bizArticle 文章资讯
     * @return 结果
     */
    @Override
    public int insertBizArticle(BizArticle bizArticle)
    {
        bizArticle.setCreateTime(DateUtils.getNowDate());
        return bizArticleMapper.insertBizArticle(bizArticle);
    }

    /**
     * 修改文章资讯
     * 
     * @param bizArticle 文章资讯
     * @return 结果
     */
    @Override
    public int updateBizArticle(BizArticle bizArticle)
    {
        bizArticle.setUpdateTime(DateUtils.getNowDate());
        return bizArticleMapper.updateBizArticle(bizArticle);
    }

    /**
     * 批量删除文章资讯
     * 
     * @param articleIds 需要删除的文章资讯主键
     * @return 结果
     */
    @Override
    public int deleteBizArticleByArticleIds(Long[] articleIds)
    {
        return bizArticleMapper.deleteBizArticleByArticleIds(articleIds);
    }

    /**
     * 删除文章资讯信息
     * 
     * @param articleId 文章资讯主键
     * @return 结果
     */
    @Override
    public int deleteBizArticleByArticleId(Long articleId)
    {
        return bizArticleMapper.deleteBizArticleByArticleId(articleId);
    }
}
