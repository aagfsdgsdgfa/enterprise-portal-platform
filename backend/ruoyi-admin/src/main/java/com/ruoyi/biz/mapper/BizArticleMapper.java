package com.ruoyi.biz.mapper;

import java.util.List;
import com.ruoyi.biz.domain.BizArticle;

/**
 * 文章资讯Mapper接口
 * 
 * @author 杨正知
 * @date 2025-12-08
 */
public interface BizArticleMapper 
{
    /**
     * 查询文章资讯
     * 
     * @param articleId 文章资讯主键
     * @return 文章资讯
     */
    public BizArticle selectBizArticleByArticleId(Long articleId);

    /**
     * 查询文章资讯列表
     * 
     * @param bizArticle 文章资讯
     * @return 文章资讯集合
     */
    public List<BizArticle> selectBizArticleList(BizArticle bizArticle);

    /**
     * 新增文章资讯
     * 
     * @param bizArticle 文章资讯
     * @return 结果
     */
    public int insertBizArticle(BizArticle bizArticle);

    /**
     * 修改文章资讯
     * 
     * @param bizArticle 文章资讯
     * @return 结果
     */
    public int updateBizArticle(BizArticle bizArticle);

    /**
     * 删除文章资讯
     * 
     * @param articleId 文章资讯主键
     * @return 结果
     */
    public int deleteBizArticleByArticleId(Long articleId);

    /**
     * 批量删除文章资讯
     * 
     * @param articleIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizArticleByArticleIds(Long[] articleIds);
}
