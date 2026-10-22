package com.ruoyi.biz.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 产品解决方案对象 biz_product
 * 
 * @author 杨正知
 * @date 2025-12-08
 */
public class BizProduct extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 产品ID */
    private Long productId;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productName;

    /** 所属分类ID */
    @Excel(name = "所属分类ID")
    private Long categoryId;

    /** 副标题/Slogan */
    @Excel(name = "副标题/Slogan")
    private String subTitle;

    /** 主图 */
    @Excel(name = "主图")
    private String mainImg;

    /** 图标(用于首页列表) */
    @Excel(name = "图标(用于首页列表)")
    private String iconImg;

    /** 技术架构图(用于放大查看) */
    @Excel(name = "技术架构图(用于放大查看)")
    private String techArchImg;

    /** 演示视频链接 */
    @Excel(name = "演示视频链接")
    private String demoVideoUrl;

    /** 产品简介 */
    @Excel(name = "产品简介")
    private String summary;

    /** 客户痛点(JSON格式存储) */
    @Excel(name = "客户痛点(JSON格式存储)")
    private String painPoints;

    /** 解决方案详情(富文本) */
    @Excel(name = "解决方案详情(富文本)")
    private String solutions;

    /** 技术参数 */
    @Excel(name = "技术参数")
    private String techSpecs;

    /** 关联产品ID集合(1,2,3) */
    @Excel(name = "关联产品ID集合(1,2,3)")
    private String relatedIds;

    /** SEO标题 */
    @Excel(name = "SEO标题")
    private String seoTitle;

    /** SEO关键词 */
    @Excel(name = "SEO关键词")
    private String seoKeywords;

    /** SEO描述 */
    @Excel(name = "SEO描述")
    private String seoDescription;

    /** 排序 */
    @Excel(name = "排序")
    private Long sortOrder;

    /** 状态(0上架 1下架) */
    @Excel(name = "状态(0上架 1下架)")
    private String status;

    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }

    public void setProductName(String productName) 
    {
        this.productName = productName;
    }

    public String getProductName() 
    {
        return productName;
    }

    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }

    public void setSubTitle(String subTitle) 
    {
        this.subTitle = subTitle;
    }

    public String getSubTitle() 
    {
        return subTitle;
    }

    public void setMainImg(String mainImg) 
    {
        this.mainImg = mainImg;
    }

    public String getMainImg() 
    {
        return mainImg;
    }

    public void setIconImg(String iconImg) 
    {
        this.iconImg = iconImg;
    }

    public String getIconImg() 
    {
        return iconImg;
    }

    public void setTechArchImg(String techArchImg) 
    {
        this.techArchImg = techArchImg;
    }

    public String getTechArchImg() 
    {
        return techArchImg;
    }

    public void setDemoVideoUrl(String demoVideoUrl) 
    {
        this.demoVideoUrl = demoVideoUrl;
    }

    public String getDemoVideoUrl() 
    {
        return demoVideoUrl;
    }

    public void setSummary(String summary) 
    {
        this.summary = summary;
    }

    public String getSummary() 
    {
        return summary;
    }

    public void setPainPoints(String painPoints) 
    {
        this.painPoints = painPoints;
    }

    public String getPainPoints() 
    {
        return painPoints;
    }

    public void setSolutions(String solutions) 
    {
        this.solutions = solutions;
    }

    public String getSolutions() 
    {
        return solutions;
    }

    public void setTechSpecs(String techSpecs) 
    {
        this.techSpecs = techSpecs;
    }

    public String getTechSpecs() 
    {
        return techSpecs;
    }

    public void setRelatedIds(String relatedIds) 
    {
        this.relatedIds = relatedIds;
    }

    public String getRelatedIds() 
    {
        return relatedIds;
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

    public void setSortOrder(Long sortOrder) 
    {
        this.sortOrder = sortOrder;
    }

    public Long getSortOrder() 
    {
        return sortOrder;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("productId", getProductId())
            .append("productName", getProductName())
            .append("categoryId", getCategoryId())
            .append("subTitle", getSubTitle())
            .append("mainImg", getMainImg())
            .append("iconImg", getIconImg())
            .append("techArchImg", getTechArchImg())
            .append("demoVideoUrl", getDemoVideoUrl())
            .append("summary", getSummary())
            .append("painPoints", getPainPoints())
            .append("solutions", getSolutions())
            .append("techSpecs", getTechSpecs())
            .append("relatedIds", getRelatedIds())
            .append("seoTitle", getSeoTitle())
            .append("seoKeywords", getSeoKeywords())
            .append("seoDescription", getSeoDescription())
            .append("sortOrder", getSortOrder())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
