package com.ruoyi.biz.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 首页通用配置对象 biz_home_config
 * 
 * @author 杨正知
 * @date 2025-12-09
 */
public class BizHomeConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long configId;

    /** 配置分组(stats, why_us, section_title) */
    @Excel(name = "配置分组(stats, why_us, section_title)")
    private String configGroup;

    /** 唯一标识(用于前端定位特定元素) */
    @Excel(name = "唯一标识(用于前端定位特定元素)")
    private String configKey;

    /** 主标题/数值(如: 99.9%) */
    @Excel(name = "主标题/数值(如: 99.9%)")
    private String title;

    /** 副标题/描述(如: 识别准确率) */
    @Excel(name = "副标题/描述(如: 识别准确率)")
    private String subTitle;

    /** 图标(Element图标名) */
    @Excel(name = "图标(Element图标名)")
    private String icon;

    /** 图片地址 */
    @Excel(name = "图片地址")
    private String imageUrl;

    /** 跳转链接 */
    @Excel(name = "跳转链接")
    private String linkUrl;

    /** 排序 */
    @Excel(name = "排序")
    private Long sortOrder;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    public void setConfigId(Long configId) 
    {
        this.configId = configId;
    }

    public Long getConfigId() 
    {
        return configId;
    }

    public void setConfigGroup(String configGroup) 
    {
        this.configGroup = configGroup;
    }

    public String getConfigGroup() 
    {
        return configGroup;
    }

    public void setConfigKey(String configKey) 
    {
        this.configKey = configKey;
    }

    public String getConfigKey() 
    {
        return configKey;
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

    public void setImageUrl(String imageUrl) 
    {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() 
    {
        return imageUrl;
    }

    public void setLinkUrl(String linkUrl) 
    {
        this.linkUrl = linkUrl;
    }

    public String getLinkUrl() 
    {
        return linkUrl;
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
            .append("configId", getConfigId())
            .append("configGroup", getConfigGroup())
            .append("configKey", getConfigKey())
            .append("title", getTitle())
            .append("subTitle", getSubTitle())
            .append("icon", getIcon())
            .append("imageUrl", getImageUrl())
            .append("linkUrl", getLinkUrl())
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
