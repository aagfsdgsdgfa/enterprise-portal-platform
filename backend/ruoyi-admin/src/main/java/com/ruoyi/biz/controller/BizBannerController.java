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
import com.ruoyi.biz.domain.BizBanner;
import com.ruoyi.biz.service.IBizBannerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 轮播图管理Controller
 * 
 * @author 杨正知
 * @date 2025-12-08
 */
@RestController
@RequestMapping("/biz/banner")
public class BizBannerController extends BaseController
{
    @Autowired
    private IBizBannerService bizBannerService;

    /**
     * 查询轮播图管理列表
     */
    @Anonymous
    //@PreAuthorize("@ss.hasPermi('biz:banner:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizBanner bizBanner)
    {
        startPage();
        List<BizBanner> list = bizBannerService.selectBizBannerList(bizBanner);
        return getDataTable(list);
    }

    /**
     * 导出轮播图管理列表
     */
    @PreAuthorize("@ss.hasPermi('biz:banner:export')")
    @Log(title = "轮播图管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizBanner bizBanner)
    {
        List<BizBanner> list = bizBannerService.selectBizBannerList(bizBanner);
        ExcelUtil<BizBanner> util = new ExcelUtil<BizBanner>(BizBanner.class);
        util.exportExcel(response, list, "轮播图管理数据");
    }

    /**
     * 获取轮播图管理详细信息
     */
    @Anonymous
    //@PreAuthorize("@ss.hasPermi('biz:banner:query')")
    @GetMapping(value = "/{bannerId}")
    public AjaxResult getInfo(@PathVariable("bannerId") Long bannerId)
    {
        return success(bizBannerService.selectBizBannerByBannerId(bannerId));
    }

    /**
     * 新增轮播图管理
     */
    @PreAuthorize("@ss.hasPermi('biz:banner:add')")
    @Log(title = "轮播图管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizBanner bizBanner)
    {
        return toAjax(bizBannerService.insertBizBanner(bizBanner));
    }

    /**
     * 修改轮播图管理
     */
    @PreAuthorize("@ss.hasPermi('biz:banner:edit')")
    @Log(title = "轮播图管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizBanner bizBanner)
    {
        return toAjax(bizBannerService.updateBizBanner(bizBanner));
    }

    /**
     * 删除轮播图管理
     */
    @PreAuthorize("@ss.hasPermi('biz:banner:remove')")
    @Log(title = "轮播图管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{bannerIds}")
    public AjaxResult remove(@PathVariable Long[] bannerIds)
    {
        return toAjax(bizBannerService.deleteBizBannerByBannerIds(bannerIds));
    }
}
