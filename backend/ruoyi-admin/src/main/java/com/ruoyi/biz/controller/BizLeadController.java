package com.ruoyi.biz.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.utils.ip.IpUtils;
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
import com.ruoyi.biz.domain.BizLead;
import com.ruoyi.biz.service.IBizLeadService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

import static com.ruoyi.common.utils.ServletUtils.getRequest;

/**
 * 营销线索Controller
 * 
 * @author 杨正知
 * @date 2025-12-08
 */
@RestController
@RequestMapping("/biz/lead")
public class BizLeadController extends BaseController
{
    @Autowired
    private IBizLeadService bizLeadService;

    /**
     * 官网前端提交线索 (允许匿名访问)
     */
    @Anonymous // 若依的注解，允许未登录访问
    @Log(title = "官网线索提交", businessType = BusinessType.INSERT)
    @PostMapping("/submit")
    public AjaxResult submit(@RequestBody BizLead bizLead)
    {
        // 1. 自动设置一些默认值
        bizLead.setType("contact"); // 默认为留言咨询
        bizLead.setStatus("0");     // 默认为待处理

        // 2. 自动获取客户IP (若依内置工具)
        bizLead.setIpAddress(IpUtils.getIpAddr(getRequest()));

        return toAjax(bizLeadService.insertBizLead(bizLead));
    }

    /**
     * 查询营销线索列表
     */
    @PreAuthorize("@ss.hasPermi('biz:lead:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizLead bizLead)
    {
        startPage();
        List<BizLead> list = bizLeadService.selectBizLeadList(bizLead);
        return getDataTable(list);
    }

    /**
     * 导出营销线索列表
     */
    @PreAuthorize("@ss.hasPermi('biz:lead:export')")
    @Log(title = "营销线索", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizLead bizLead)
    {
        List<BizLead> list = bizLeadService.selectBizLeadList(bizLead);
        ExcelUtil<BizLead> util = new ExcelUtil<BizLead>(BizLead.class);
        util.exportExcel(response, list, "营销线索数据");
    }

    /**
     * 获取营销线索详细信息
     */
    @PreAuthorize("@ss.hasPermi('biz:lead:query')")
    @GetMapping(value = "/{leadId}")
    public AjaxResult getInfo(@PathVariable("leadId") Long leadId)
    {
        return success(bizLeadService.selectBizLeadByLeadId(leadId));
    }

    /**
     * 新增营销线索
     */
    @PreAuthorize("@ss.hasPermi('biz:lead:add')")
    @Log(title = "营销线索", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizLead bizLead)
    {
        return toAjax(bizLeadService.insertBizLead(bizLead));
    }

    /**
     * 修改营销线索
     */
    @PreAuthorize("@ss.hasPermi('biz:lead:edit')")
    @Log(title = "营销线索", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizLead bizLead)
    {
        return toAjax(bizLeadService.updateBizLead(bizLead));
    }

    /**
     * 删除营销线索
     */
    @PreAuthorize("@ss.hasPermi('biz:lead:remove')")
    @Log(title = "营销线索", businessType = BusinessType.DELETE)
	@DeleteMapping("/{leadIds}")
    public AjaxResult remove(@PathVariable Long[] leadIds)
    {
        return toAjax(bizLeadService.deleteBizLeadByLeadIds(leadIds));
    }
}
