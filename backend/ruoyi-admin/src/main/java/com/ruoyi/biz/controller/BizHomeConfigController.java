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
import com.ruoyi.biz.domain.BizHomeConfig;
import com.ruoyi.biz.service.IBizHomeConfigService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 首页通用配置Controller
 * 
 * @author 杨正知
 * @date 2025-12-09
 */
@RestController
@RequestMapping("/biz/config")
public class BizHomeConfigController extends BaseController
{
    @Autowired
    private IBizHomeConfigService bizHomeConfigService;

    /**
     * 查询首页通用配置列表
     */
    @Anonymous
    //@PreAuthorize("@ss.hasPermi('biz:config:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizHomeConfig bizHomeConfig)
    {
        startPage();
        List<BizHomeConfig> list = bizHomeConfigService.selectBizHomeConfigList(bizHomeConfig);
        return getDataTable(list);
    }

    /**
     * 导出首页通用配置列表
     */
    @PreAuthorize("@ss.hasPermi('biz:config:export')")
    @Log(title = "首页通用配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizHomeConfig bizHomeConfig)
    {
        List<BizHomeConfig> list = bizHomeConfigService.selectBizHomeConfigList(bizHomeConfig);
        ExcelUtil<BizHomeConfig> util = new ExcelUtil<BizHomeConfig>(BizHomeConfig.class);
        util.exportExcel(response, list, "首页通用配置数据");
    }

    /**
     * 获取首页通用配置详细信息
     */
    @Anonymous
    //@PreAuthorize("@ss.hasPermi('biz:config:query')")
    @GetMapping(value = "/{configId}")
    public AjaxResult getInfo(@PathVariable("configId") Long configId)
    {
        return success(bizHomeConfigService.selectBizHomeConfigByConfigId(configId));
    }

    /**
     * 新增首页通用配置
     */
    @PreAuthorize("@ss.hasPermi('biz:config:add')")
    @Log(title = "首页通用配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizHomeConfig bizHomeConfig)
    {
        return toAjax(bizHomeConfigService.insertBizHomeConfig(bizHomeConfig));
    }

    /**
     * 修改首页通用配置
     */
    @PreAuthorize("@ss.hasPermi('biz:config:edit')")
    @Log(title = "首页通用配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizHomeConfig bizHomeConfig)
    {
        return toAjax(bizHomeConfigService.updateBizHomeConfig(bizHomeConfig));
    }

    /**
     * 删除首页通用配置
     */
    @PreAuthorize("@ss.hasPermi('biz:config:remove')")
    @Log(title = "首页通用配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{configIds}")
    public AjaxResult remove(@PathVariable Long[] configIds)
    {
        return toAjax(bizHomeConfigService.deleteBizHomeConfigByConfigIds(configIds));
    }
}
