package com.ruoyi.biz.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 项目案例对象 biz_project
 * 
 * @author 杨正知
 * @date 2025-12-08
 */
public class BizProject extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 项目ID */
    private Long projectId;

    /** 项目名称(如:某市智慧交通系统) */
    @Excel(name = "项目名称(如:某市智慧交通系统)")
    private String projectName;

    /** 客户名称(如:某市政府) */
    @Excel(name = "客户名称(如:某市政府)")
    private String clientName;

    /** 所属行业(智慧城市/工业/教育) */
    @Excel(name = "所属行业(智慧城市/工业/教育)")
    private String industryType;

    /** 列表封面图 */
    @Excel(name = "列表封面图")
    private String coverImg;

    /** 详情页Banner大图 */
    @Excel(name = "详情页Banner大图")
    private String detailBanner;

    /** 项目背景 */
    @Excel(name = "项目背景")
    private String background;

    /** 解决方案描述 */
    @Excel(name = "解决方案描述")
    private String solutionDesc;

    /** 建设成果/价值 */
    @Excel(name = "建设成果/价值")
    private String achievement;

    /** 关键数据值(如:50%) */
    @Excel(name = "关键数据值(如:50%)")
    private String keyDataVal;

    /** 数据说明(如:通行效率提升) */
    @Excel(name = "数据说明(如:通行效率提升)")
    private String keyDataDesc;

    /** 完工日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "完工日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date completionDate;

    /** 关联产品ID */
    @Excel(name = "关联产品ID")
    private String relatedProductIds;

    /** 排序 */
    @Excel(name = "排序")
    private Long sortOrder;

    public void setProjectId(Long projectId) 
    {
        this.projectId = projectId;
    }

    public Long getProjectId() 
    {
        return projectId;
    }

    public void setProjectName(String projectName) 
    {
        this.projectName = projectName;
    }

    public String getProjectName() 
    {
        return projectName;
    }

    public void setClientName(String clientName) 
    {
        this.clientName = clientName;
    }

    public String getClientName() 
    {
        return clientName;
    }

    public void setIndustryType(String industryType) 
    {
        this.industryType = industryType;
    }

    public String getIndustryType() 
    {
        return industryType;
    }

    public void setCoverImg(String coverImg) 
    {
        this.coverImg = coverImg;
    }

    public String getCoverImg() 
    {
        return coverImg;
    }

    public void setDetailBanner(String detailBanner) 
    {
        this.detailBanner = detailBanner;
    }

    public String getDetailBanner() 
    {
        return detailBanner;
    }

    public void setBackground(String background) 
    {
        this.background = background;
    }

    public String getBackground() 
    {
        return background;
    }

    public void setSolutionDesc(String solutionDesc) 
    {
        this.solutionDesc = solutionDesc;
    }

    public String getSolutionDesc() 
    {
        return solutionDesc;
    }

    public void setAchievement(String achievement) 
    {
        this.achievement = achievement;
    }

    public String getAchievement() 
    {
        return achievement;
    }

    public void setKeyDataVal(String keyDataVal) 
    {
        this.keyDataVal = keyDataVal;
    }

    public String getKeyDataVal() 
    {
        return keyDataVal;
    }

    public void setKeyDataDesc(String keyDataDesc) 
    {
        this.keyDataDesc = keyDataDesc;
    }

    public String getKeyDataDesc() 
    {
        return keyDataDesc;
    }

    public void setCompletionDate(Date completionDate) 
    {
        this.completionDate = completionDate;
    }

    public Date getCompletionDate() 
    {
        return completionDate;
    }

    public void setRelatedProductIds(String relatedProductIds) 
    {
        this.relatedProductIds = relatedProductIds;
    }

    public String getRelatedProductIds() 
    {
        return relatedProductIds;
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
            .append("projectId", getProjectId())
            .append("projectName", getProjectName())
            .append("clientName", getClientName())
            .append("industryType", getIndustryType())
            .append("coverImg", getCoverImg())
            .append("detailBanner", getDetailBanner())
            .append("background", getBackground())
            .append("solutionDesc", getSolutionDesc())
            .append("achievement", getAchievement())
            .append("keyDataVal", getKeyDataVal())
            .append("keyDataDesc", getKeyDataDesc())
            .append("completionDate", getCompletionDate())
            .append("relatedProductIds", getRelatedProductIds())
            .append("sortOrder", getSortOrder())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
