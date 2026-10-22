package com.ruoyi.biz.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 职位招聘对象 biz_job
 * 
 * @author 杨正知
 * @date 2025-12-08
 */
public class BizJob extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 职位ID */
    private Long jobId;

    /** 职位名称 */
    @Excel(name = "职位名称")
    private String jobTitle;

    /** 所属部门 */
    @Excel(name = "所属部门")
    private String department;

    /** 类型(1研发 2市场 3职能) */
    @Excel(name = "类型(1研发 2市场 3职能)")
    private String jobType;

    /** 工作地点 */
    @Excel(name = "工作地点")
    private String workLocation;

    /** 薪资范围 */
    @Excel(name = "薪资范围")
    private String salaryRange;

    /** 经验要求 */
    @Excel(name = "经验要求")
    private String experience;

    /** 学历要求 */
    @Excel(name = "学历要求")
    private String education;

    /** 岗位职责 */
    @Excel(name = "岗位职责")
    private String jobDesc;

    /** 任职要求 */
    @Excel(name = "任职要求")
    private String jobRequire;

    /** 是否急聘(1是 0否) */
    @Excel(name = "是否急聘(1是 0否)")
    private String isUrgent;

    /** 状态(0招聘中 1已停止) */
    @Excel(name = "状态(0招聘中 1已停止)")
    private String status;

    public void setJobId(Long jobId) 
    {
        this.jobId = jobId;
    }

    public Long getJobId() 
    {
        return jobId;
    }

    public void setJobTitle(String jobTitle) 
    {
        this.jobTitle = jobTitle;
    }

    public String getJobTitle() 
    {
        return jobTitle;
    }

    public void setDepartment(String department) 
    {
        this.department = department;
    }

    public String getDepartment() 
    {
        return department;
    }

    public void setJobType(String jobType) 
    {
        this.jobType = jobType;
    }

    public String getJobType() 
    {
        return jobType;
    }

    public void setWorkLocation(String workLocation) 
    {
        this.workLocation = workLocation;
    }

    public String getWorkLocation() 
    {
        return workLocation;
    }

    public void setSalaryRange(String salaryRange) 
    {
        this.salaryRange = salaryRange;
    }

    public String getSalaryRange() 
    {
        return salaryRange;
    }

    public void setExperience(String experience) 
    {
        this.experience = experience;
    }

    public String getExperience() 
    {
        return experience;
    }

    public void setEducation(String education) 
    {
        this.education = education;
    }

    public String getEducation() 
    {
        return education;
    }

    public void setJobDesc(String jobDesc) 
    {
        this.jobDesc = jobDesc;
    }

    public String getJobDesc() 
    {
        return jobDesc;
    }

    public void setJobRequire(String jobRequire) 
    {
        this.jobRequire = jobRequire;
    }

    public String getJobRequire() 
    {
        return jobRequire;
    }

    public void setIsUrgent(String isUrgent) 
    {
        this.isUrgent = isUrgent;
    }

    public String getIsUrgent() 
    {
        return isUrgent;
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
            .append("jobId", getJobId())
            .append("jobTitle", getJobTitle())
            .append("department", getDepartment())
            .append("jobType", getJobType())
            .append("workLocation", getWorkLocation())
            .append("salaryRange", getSalaryRange())
            .append("experience", getExperience())
            .append("education", getEducation())
            .append("jobDesc", getJobDesc())
            .append("jobRequire", getJobRequire())
            .append("isUrgent", getIsUrgent())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
