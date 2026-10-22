package com.ruoyi.biz.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 资料下载中心对象 biz_resource
 * 
 * @author 杨正知
 * @date 2025-12-08
 */
public class BizResource extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 资料ID */
    private Long resourceId;

    /** 资料标题 */
    @Excel(name = "资料标题")
    private String title;

    /** 文件路径(OSS/本地) */
    @Excel(name = "文件路径(OSS/本地)")
    private String filePath;

    /** 文件大小 */
    @Excel(name = "文件大小")
    private String fileSize;

    /** 文件格式 */
    @Excel(name = "文件格式")
    private String fileType;

    /** 需留资下载(1是 0否) */
    @Excel(name = "需留资下载(1是 0否)")
    private String needLogin;

    /** 下载次数 */
    @Excel(name = "下载次数")
    private Long downloadCount;

    /** 状态(0正常 1停用) */
    @Excel(name = "状态(0正常 1停用)")
    private String status;

    public void setResourceId(Long resourceId) 
    {
        this.resourceId = resourceId;
    }

    public Long getResourceId() 
    {
        return resourceId;
    }

    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }

    public void setFilePath(String filePath) 
    {
        this.filePath = filePath;
    }

    public String getFilePath() 
    {
        return filePath;
    }

    public void setFileSize(String fileSize) 
    {
        this.fileSize = fileSize;
    }

    public String getFileSize() 
    {
        return fileSize;
    }

    public void setFileType(String fileType) 
    {
        this.fileType = fileType;
    }

    public String getFileType() 
    {
        return fileType;
    }

    public void setNeedLogin(String needLogin) 
    {
        this.needLogin = needLogin;
    }

    public String getNeedLogin() 
    {
        return needLogin;
    }

    public void setDownloadCount(Long downloadCount) 
    {
        this.downloadCount = downloadCount;
    }

    public Long getDownloadCount() 
    {
        return downloadCount;
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
            .append("resourceId", getResourceId())
            .append("title", getTitle())
            .append("filePath", getFilePath())
            .append("fileSize", getFileSize())
            .append("fileType", getFileType())
            .append("needLogin", getNeedLogin())
            .append("downloadCount", getDownloadCount())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
