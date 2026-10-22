package com.ruoyi.biz.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 文章资讯对象 biz_article
 * 
 * @author 杨正知
 * @date 2025-12-08
 */
public class BizArticle extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 文章ID */
    private Long articleId;

    /** 分类(news新闻/notice公告/culture文化) */
    @Excel(name = "分类(news新闻/notice公告/culture文化)")
    private String categoryType;

    /** 文章标题 */
    @Excel(name = "文章标题")
    private String title;

    /** 文章摘要 */
    @Excel(name = "文章摘要")
    private String summary;

    /** 封面图片 */
    @Excel(name = "封面图片")
    private String coverImg;

    /** 文章内容(富文本) */
    @Excel(name = "文章内容(富文本)")
    private String content;

    /** 作者 */
    @Excel(name = "作者")
    private String author;

    /** 是否置顶(1是 0否) */
    @Excel(name = "是否置顶(1是 0否)")
    private String isTop;

    /** 浏览量 */
    @Excel(name = "浏览量")
    private Long viewCount;

    /** SEO标题 */
    @Excel(name = "SEO标题")
    private String seoTitle;

    /** SEO关键词 */
    @Excel(name = "SEO关键词")
    private String seoKeywords;

    /** SEO描述 */
    @Excel(name = "SEO描述")
    private String seoDescription;

    /** 状态(0发布 1草稿) */
    @Excel(name = "状态(0发布 1草稿)")
    private String status;

    /** 发布时间(支持定时) */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发布时间(支持定时)", width = 30, dateFormat = "yyyy-MM-dd")
    private Date publishTime;

    public void setArticleId(Long articleId) 
    {
        this.articleId = articleId;
    }

    public Long getArticleId() 
    {
        return articleId;
    }

    public void setCategoryType(String categoryType) 
    {
        this.categoryType = categoryType;
    }

    public String getCategoryType() 
    {
        return categoryType;
    }

    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }

    public void setSummary(String summary) 
    {
        this.summary = summary;
    }

    public String getSummary() 
    {
        return summary;
    }

    public void setCoverImg(String coverImg) 
    {
        this.coverImg = coverImg;
    }

    public String getCoverImg() 
    {
        return coverImg;
    }

    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }

    public void setAuthor(String author) 
    {
        this.author = author;
    }

    public String getAuthor() 
    {
        return author;
    }

    public void setIsTop(String isTop) 
    {
        this.isTop = isTop;
    }

    public String getIsTop() 
    {
        return isTop;
    }

    public void setViewCount(Long viewCount) 
    {
        this.viewCount = viewCount;
    }

    public Long getViewCount() 
    {
        return viewCount;
    }

    public void setSeoTitle(String seoTitle) 
    {
        this.seoTitle = seoTitle;
    }

    public String getSeoTitle() 
    {
        return seoTitle;
    }

    public void setSeoKeywords(String seoKeywords) 
    {
        this.seoKeywords = seoKeywords;
    }

    public String getSeoKeywords() 
    {
        return seoKeywords;
    }

    public void setSeoDescription(String seoDescription) 
    {
        this.seoDescription = seoDescription;
    }

    public String getSeoDescription() 
    {
        return seoDescription;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setPublishTime(Date publishTime) 
    {
        this.publishTime = publishTime;
    }

    public Date getPublishTime() 
    {
        return publishTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("articleId", getArticleId())
            .append("categoryType", getCategoryType())
            .append("title", getTitle())
            .append("summary", getSummary())
            .append("coverImg", getCoverImg())
            .append("content", getContent())
            .append("author", getAuthor())
            .append("isTop", getIsTop())
            .append("viewCount", getViewCount())
            .append("seoTitle", getSeoTitle())
            .append("seoKeywords", getSeoKeywords())
            .append("seoDescription", getSeoDescription())
            .append("status", getStatus())
            .append("publishTime", getPublishTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
