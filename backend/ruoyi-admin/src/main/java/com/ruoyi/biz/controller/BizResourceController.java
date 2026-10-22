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
import com.ruoyi.biz.domain.BizResource;
import com.ruoyi.biz.service.IBizResourceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 资料下载中心Controller
 * 
 * @author 杨正知
 * @date 2025-12-08
 */
@RestController
@RequestMapping("/biz/resource")
public class BizResourceController extends BaseController
{
    @Autowired
    private IBizResourceService bizResourceService;

    /**
     * 查询资料下载中心列表
     */
    @Anonymous
    //@PreAuthorize("@ss.hasPermi('biz:resource:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizResource bizResource)
    {
        startPage();
        List<BizResource> list = bizResourceService.selectBizResourceList(bizResource);
        return getDataTable(list);
    }

    /**
     * 导出资料下载中心列表
     */
    @PreAuthorize("@ss.hasPermi('biz:resource:export')")
    @Log(title = "资料下载中心", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizResource bizResource)
    {
        List<BizResource> list = bizResourceService.selectBizResourceList(bizResource);
        ExcelUtil<BizResource> util = new ExcelUtil<BizResource>(BizResource.class);
        util.exportExcel(response, list, "资料下载中心数据");
    }

    /**
     * 获取资料下载中心详细信息
     */
    @PreAuthorize("@ss.hasPermi('biz:resource:query')")
    @GetMapping(value = "/{resourceId}")
    public AjaxResult getInfo(@PathVariable("resourceId") Long resourceId)
    {
        return success(bizResourceService.selectBizResourceByResourceId(resourceId));
    }

    /**
     * 新增资料下载中心
     */
    @PreAuthorize("@ss.hasPermi('biz:resource:add')")
    @Log(title = "资料下载中心", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizResource bizResource)
    {
        return toAjax(bizResourceService.insertBizResource(bizResource));
    }

    /**
     * 修改资料下载中心
     */
    @PreAuthorize("@ss.hasPermi('biz:resource:edit')")
    @Log(title = "资料下载中心", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizResource bizResource)
    {
        return toAjax(bizResourceService.updateBizResource(bizResource));
    }

    /**
     * 删除资料下载中心
     */
    @PreAuthorize("@ss.hasPermi('biz:resource:remove')")
    @Log(title = "资料下载中心", businessType = BusinessType.DELETE)
	@DeleteMapping("/{resourceIds}")
    public AjaxResult remove(@PathVariable Long[] resourceIds)
    {
        return toAjax(bizResourceService.deleteBizResourceByResourceIds(resourceIds));
    }
}
