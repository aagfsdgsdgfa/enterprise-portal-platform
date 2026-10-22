package com.ruoyi.biz.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 企业文化对象 biz_culture
 * 
 * @author 杨正知
 * @date 2025-12-08
 */
public class BizCulture extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long cultureId;

    /** 标题(如:企业使命) */
    @Excel(name = "标题(如:企业使命)")
    private String title;

    /** 短描述(如:让AI造福人类) */
    @Excel(name = "短描述(如:让AI造福人类)")
    private String subTitle;

    /** 图标(ElementUI图标类名或图片) */
    @Excel(name = "图标(ElementUI图标类名或图片)")
    private String icon;

    /** 详细阐述 */
    @Excel(name = "详细阐述")
    private String description;

    /** 排序 */
    @Excel(name = "排序")
    private Long sortOrder;

    /** 类型 */
    @Excel(name = "类型")
    private String type;

    public void setCultureId(Long cultureId) 
    {
        this.cultureId = cultureId;
    }

    public Long getCultureId() 
    {
        return cultureId;
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

    public void setIcon(String icon) 
    {
        this.icon = icon;
    }

    public String getIcon() 
    {
        return icon;
    }

    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }

    public void setSortOrder(Long sortOrder) 
    {
        this.sortOrder = sortOrder;
    }

    public Long getSortOrder() 
    {
        return sortOrder;
    }

    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("cultureId", getCultureId())
            .append("title", getTitle())
            .append("subTitle", getSubTitle())
            .append("icon", getIcon())
            .append("description", getDescription())
            .append("sortOrder", getSortOrder())
            .append("type", getType())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
