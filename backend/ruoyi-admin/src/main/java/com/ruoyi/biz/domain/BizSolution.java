package com.ruoyi.biz.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 行业解决方案对象 biz_solution
 * 
 * @author 杨正知
 * @date 2025-12-09
 */
public class BizSolution extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 方案ID */
    private Long solutionId;

    /** 方案标题 */
    @Excel(name = "方案标题")
    private String title;

    /** 副标题(Slogan) */
    @Excel(name = "副标题(Slogan)")
    private String subTitle;

    /** 方案摘要(列表页显示) */
    @Excel(name = "方案摘要(列表页显示)")
    private String summary;

    /** 列表图标(ElementPlus图标名或图片URL) */
    @Excel(name = "列表图标(ElementPlus图标名或图片URL)")
    private String icon;

    /** 详情页顶部大图 */
    @Excel(name = "详情页顶部大图")
    private String bannerImg;

    /** 方案详情(富文本) */
    @Excel(name = "方案详情(富文本)")
    private String content;

    /** 客户痛点(分号隔开) */
    @Excel(name = "客户痛点(分号隔开)")
    private String painPoints;

    /** 方案价值/优势(分号隔开) */
    @Excel(name = "方案价值/优势(分号隔开)")
    private String valueProps;

    /** 关联产品ID集合(如: 1,2,5) */
    @Excel(name = "关联产品ID集合(如: 1,2,5)")
    private String relatedProductIds;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    private Long sortOrder;

    public void setSolutionId(Long solutionId) 
    {
        this.solutionId = solutionId;
    }

    public Long getSolutionId() 
    {
        return solutionId;
    }

    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }

    public void setSubTitle(String subTitle) 
    {
        this.subTitle = subTitle;
    }

    public String getSubTitle() 
    {
        return subTitle;
    }

    public void setSummary(String summary) 
    {
        this.summary = summary;
    }

    public String getSummary() 
    {
        return summary;
    }

    public void setIcon(String icon) 
    {
        this.icon = icon;
    }

    public String getIcon() 
    {
        return icon;
    }

    public void setBannerImg(String bannerImg) 
    {
        this.bannerImg = bannerImg;
    }

    public String getBannerImg() 
    {
        return bannerImg;
    }

    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }

    public void setPainPoints(String painPoints) 
    {
        this.painPoints = painPoints;
    }

    public String getPainPoints() 
    {
        return painPoints;
    }

    public void setValueProps(String valueProps) 
    {
        this.valueProps = valueProps;
    }

    public String getValueProps() 
    {
        return valueProps;
    }

    public void setRelatedProductIds(String relatedProductIds) 
    {
        this.relatedProductIds = relatedProductIds;
    }

    public String getRelatedProductIds() 
    {
        return relatedProductIds;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setSortOrder(Long sortOrder) 
    {
        this.sortOrder = sortOrder;
    }

    public Long getSortOrder() 
    {
        return sortOrder;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("solutionId", getSolutionId())
            .append("title", getTitle())
            .append("subTitle", getSubTitle())
            .append("summary", getSummary())
            .append("icon", getIcon())
            .append("bannerImg", getBannerImg())
            .append("content", getContent())
            .append("painPoints", getPainPoints())
            .append("valueProps", getValueProps())
            .append("relatedProductIds", getRelatedProductIds())
            .append("status", getStatus())
            .append("sortOrder", getSortOrder())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
