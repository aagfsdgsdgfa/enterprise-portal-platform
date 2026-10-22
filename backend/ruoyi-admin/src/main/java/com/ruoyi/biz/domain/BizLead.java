package com.ruoyi.biz.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 营销线索对象 biz_lead
 * 
 * @author 杨正知
 * @date 2025-12-08
 */
public class BizLead extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 线索ID */
    private Long leadId;

    /** 类型(contact留言/download下载/agent招商) */
    @Excel(name = "类型(contact留言/download下载/agent招商)")
    private String type;

    /** 联系人姓名 */
    @Excel(name = "联系人姓名")
    private String contactName;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String phone;

    /** 企业名称 */
    @Excel(name = "企业名称")
    private String companyName;

    /** 电子邮箱 */
    @Excel(name = "电子邮箱")
    private String email;

    /** 意向产品/下载资料名 */
    @Excel(name = "意向产品/下载资料名")
    private String intentProduct;

    /** 留言内容 */
    @Excel(name = "留言内容")
    private String message;

    /** 预算范围(用于计算器结果) */
    @Excel(name = "预算范围(用于计算器结果)")
    private String budgetRange;

    /** 客户IP */
    @Excel(name = "客户IP")
    private String ipAddress;

    /** 来源页面 */
    @Excel(name = "来源页面")
    private String sourceUrl;

    /** 状态(0待处理 1跟进中 2已转化 3无效) */
    @Excel(name = "状态(0待处理 1跟进中 2已转化 3无效)")
    private String status;

    /** 处理结果/备注 */
    @Excel(name = "处理结果/备注")
    private String handleResult;

    public void setLeadId(Long leadId) 
    {
        this.leadId = leadId;
    }

    public Long getLeadId() 
    {
        return leadId;
    }

    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }

    public void setContactName(String contactName) 
    {
        this.contactName = contactName;
    }

    public String getContactName() 
    {
        return contactName;
    }

    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }

    public void setCompanyName(String companyName) 
    {
        this.companyName = companyName;
    }

    public String getCompanyName() 
    {
        return companyName;
    }

    public void setEmail(String email) 
    {
        this.email = email;
    }

    public String getEmail() 
    {
        return email;
    }

    public void setIntentProduct(String intentProduct) 
    {
        this.intentProduct = intentProduct;
    }

    public String getIntentProduct() 
    {
        return intentProduct;
    }

    public void setMessage(String message) 
    {
        this.message = message;
    }

    public String getMessage() 
    {
        return message;
    }

    public void setBudgetRange(String budgetRange) 
    {
        this.budgetRange = budgetRange;
    }

    public String getBudgetRange() 
    {
        return budgetRange;
    }

    public void setIpAddress(String ipAddress) 
    {
        this.ipAddress = ipAddress;
    }

    public String getIpAddress() 
    {
        return ipAddress;
    }

    public void setSourceUrl(String sourceUrl) 
    {
        this.sourceUrl = sourceUrl;
    }

    public String getSourceUrl() 
    {
        return sourceUrl;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setHandleResult(String handleResult) 
    {
        this.handleResult = handleResult;
    }

    public String getHandleResult() 
    {
        return handleResult;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("leadId", getLeadId())
            .append("type", getType())
            .append("contactName", getContactName())
            .append("phone", getPhone())
            .append("companyName", getCompanyName())
            .append("email", getEmail())
            .append("intentProduct", getIntentProduct())
            .append("message", getMessage())
            .append("budgetRange", getBudgetRange())
            .append("ipAddress", getIpAddress())
            .append("sourceUrl", getSourceUrl())
            .append("status", getStatus())
            .append("handleResult", getHandleResult())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
