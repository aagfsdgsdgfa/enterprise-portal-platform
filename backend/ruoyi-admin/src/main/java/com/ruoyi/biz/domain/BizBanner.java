package com.ruoyi.biz.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 轮播图管理对象 biz_banner
 * 
 * @author 杨正知
 * @date 2025-12-08
 */
public class BizBanner extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** BannerID */
    private Long bannerId;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 图片地址 */
    @Excel(name = "图片地址")
    private String imageUrl;

    /** 跳转链接 */
    @Excel(name = "跳转链接")
    private String linkUrl;

    /** 跳转方式(_self/_blank) */
    @Excel(name = "跳转方式(_self/_blank)")
    private String target;

    /** 排序(越大越前) */
    @Excel(name = "排序(越大越前)")
    private Long sortOrder;

    /** 设备类型(0PC端 1移动端) */
    @Excel(name = "设备类型(0PC端 1移动端)")
    private String deviceType;

    /** 状态(0正常 1停用) */
    @Excel(name = "状态(0正常 1停用)")
    private String status;

    public void setBannerId(Long bannerId) 
    {
        this.bannerId = bannerId;
    }

    public Long getBannerId() 
    {
        return bannerId;
    }

    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
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

    public void setTarget(String target) 
    {
        this.target = target;
    }

    public String getTarget() 
    {
        return target;
    }

    public void setSortOrder(Long sortOrder) 
    {
        this.sortOrder = sortOrder;
    }

    public Long getSortOrder() 
    {
        return sortOrder;
    }

    public void setDeviceType(String deviceType) 
    {
        this.deviceType = deviceType;
    }

    public String getDeviceType() 
    {
        return deviceType;
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
            .append("bannerId", getBannerId())
            .append("title", getTitle())
            .append("imageUrl", getImageUrl())
            .append("linkUrl", getLinkUrl())
            .append("target", getTarget())
            .append("sortOrder", getSortOrder())
            .append("deviceType", getDeviceType())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
