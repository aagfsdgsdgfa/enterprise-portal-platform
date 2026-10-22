package com.ruoyi.biz.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 企业基础信息对象 biz_company_info
 * 
 * @author 杨正知
 * @date 2025-12-08
 */
public class BizCompanyInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long infoId;

    /** 公司名称 */
    @Excel(name = "公司名称")
    private String companyName;

    /** 公司地址 */
    @Excel(name = "公司地址")
    private String address;

    /** 官方热线 */
    @Excel(name = "官方热线")
    private String phoneHotline;

    /** 招商电话 */
    @Excel(name = "招商电话")
    private String phoneSales;

    /** 联系邮箱 */
    @Excel(name = "联系邮箱")
    private String email;

    /** 工作时间 */
    @Excel(name = "工作时间")
    private String workTime;

    /** Logo(深色模式) */
    @Excel(name = "Logo(深色模式)")
    private String logoDark;

    /** Logo(浅色模式) */
    @Excel(name = "Logo(浅色模式)")
    private String logoLight;

    /** 公众号二维码 */
    @Excel(name = "公众号二维码")
    private String qrCode;

    /** ICP备案号 */
    @Excel(name = "ICP备案号")
    private String icpBeian;

    /** 版权信息 */
    @Excel(name = "版权信息")
    private String copyright;

    /** 纬度 */
    @Excel(name = "纬度")
    private String latitude;

    /** 经度 */
    @Excel(name = "经度")
    private String longitude;

    public void setInfoId(Long infoId) 
    {
        this.infoId = infoId;
    }

    public Long getInfoId() 
    {
        return infoId;
    }

    public void setCompanyName(String companyName) 
    {
        this.companyName = companyName;
    }

    public String getCompanyName() 
    {
        return companyName;
    }

    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }

    public void setPhoneHotline(String phoneHotline) 
    {
        this.phoneHotline = phoneHotline;
    }

    public String getPhoneHotline() 
    {
        return phoneHotline;
    }

    public void setPhoneSales(String phoneSales) 
    {
        this.phoneSales = phoneSales;
    }

    public String getPhoneSales() 
    {
        return phoneSales;
    }

    public void setEmail(String email) 
    {
        this.email = email;
    }

    public String getEmail() 
    {
        return email;
    }

    public void setWorkTime(String workTime) 
    {
        this.workTime = workTime;
    }

    public String getWorkTime() 
    {
        return workTime;
    }

    public void setLogoDark(String logoDark) 
    {
        this.logoDark = logoDark;
    }

    public String getLogoDark() 
    {
        return logoDark;
    }

    public void setLogoLight(String logoLight) 
    {
        this.logoLight = logoLight;
    }

    public String getLogoLight() 
    {
        return logoLight;
    }

    public void setQrCode(String qrCode) 
    {
        this.qrCode = qrCode;
    }

    public String getQrCode() 
    {
        return qrCode;
    }

    public void setIcpBeian(String icpBeian) 
    {
        this.icpBeian = icpBeian;
    }

    public String getIcpBeian() 
    {
        return icpBeian;
    }

    public void setCopyright(String copyright) 
    {
        this.copyright = copyright;
    }

    public String getCopyright() 
    {
        return copyright;
    }

    public void setLatitude(String latitude) 
    {
        this.latitude = latitude;
    }

    public String getLatitude() 
    {
        return latitude;
    }

    public void setLongitude(String longitude) 
    {
        this.longitude = longitude;
    }

    public String getLongitude() 
    {
        return longitude;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("infoId", getInfoId())
            .append("companyName", getCompanyName())
            .append("address", getAddress())
            .append("phoneHotline", getPhoneHotline())
            .append("phoneSales", getPhoneSales())
            .append("email", getEmail())
            .append("workTime", getWorkTime())
            .append("logoDark", getLogoDark())
            .append("logoLight", getLogoLight())
            .append("qrCode", getQrCode())
            .append("icpBeian", getIcpBeian())
            .append("copyright", getCopyright())
            .append("latitude", getLatitude())
            .append("longitude", getLongitude())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
