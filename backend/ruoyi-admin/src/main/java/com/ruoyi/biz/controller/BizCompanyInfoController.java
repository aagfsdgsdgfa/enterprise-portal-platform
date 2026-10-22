package com.ruoyi.biz.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.annotation.Anonymous;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.biz.domain.BizCompanyInfo;
import com.ruoyi.biz.service.IBizCompanyInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 企业基础信息Controller
 * 
 * @author 杨正知
 * @date 2025-12-08
 */
@RestController
@RequestMapping("/biz/info")
public class BizCompanyInfoController extends BaseController
{
    @Autowired
    private IBizCompanyInfoService bizCompanyInfoService;

    /**
     * 查询企业基础信息列表
     */
    @Anonymous
    //@PreAuthorize("@ss.hasPermi('biz:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizCompanyInfo bizCompanyInfo)
    {
        startPage();
        List<BizCompanyInfo> list = bizCompanyInfoService.selectBizCompanyInfoList(bizCompanyInfo);
        return getDataTable(list);
    }

    /**
     * 导出企业基础信息列表
     */
    @PreAuthorize("@ss.hasPermi('biz:info:export')")
    @Log(title = "企业基础信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizCompanyInfo bizCompanyInfo)
    {
        List<BizCompanyInfo> list = bizCompanyInfoService.selectBizCompanyInfoList(bizCompanyInfo);
        ExcelUtil<BizCompanyInfo> util = new ExcelUtil<BizCompanyInfo>(BizCompanyInfo.class);
        util.exportExcel(response, list, "企业基础信息数据");
    }

    /**
     * 获取企业基础信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('biz:info:query')")
    @GetMapping(value = "/{infoId}")
    public AjaxResult getInfo(@PathVariable("infoId") Long infoId)
    {
        return success(bizCompanyInfoService.selectBizCompanyInfoByInfoId(infoId));
    }

    /**
     * 新增企业基础信息
     */
    @PreAuthorize("@ss.hasPermi('biz:info:add')")
    @Log(title = "企业基础信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizCompanyInfo bizCompanyInfo)
    {
        return toAjax(bizCompanyInfoService.insertBizCompanyInfo(bizCompanyInfo));
    }

    /**
     * 修改企业基础信息
     */
    @PreAuthorize("@ss.hasPermi('biz:info:edit')")
    @Log(title = "企业基础信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizCompanyInfo bizCompanyInfo)
    {
        return toAjax(bizCompanyInfoService.updateBizCompanyInfo(bizCompanyInfo));
    }

    /**
     * 删除企业基础信息
     */
    @PreAuthorize("@ss.hasPermi('biz:info:remove')")
    @Log(title = "企业基础信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{infoIds}")
    public AjaxResult remove(@PathVariable Long[] infoIds)
    {
        return toAjax(bizCompanyInfoService.deleteBizCompanyInfoByInfoIds(infoIds));
    }
}
