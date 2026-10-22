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
import com.ruoyi.biz.domain.BizCulture;
import com.ruoyi.biz.service.IBizCultureService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 企业文化Controller
 * 
 * @author 杨正知
 * @date 2025-12-08
 */
@RestController
@RequestMapping("/biz/culture")
public class BizCultureController extends BaseController
{
    @Autowired
    private IBizCultureService bizCultureService;

    /**
     * 查询企业文化列表
     */
    @Anonymous
    //@PreAuthorize("@ss.hasPermi('biz:culture:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizCulture bizCulture)
    {
        startPage();
        List<BizCulture> list = bizCultureService.selectBizCultureList(bizCulture);
        return getDataTable(list);
    }

    /**
     * 导出企业文化列表
     */
    @PreAuthorize("@ss.hasPermi('biz:culture:export')")
    @Log(title = "企业文化", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizCulture bizCulture)
    {
        List<BizCulture> list = bizCultureService.selectBizCultureList(bizCulture);
        ExcelUtil<BizCulture> util = new ExcelUtil<BizCulture>(BizCulture.class);
        util.exportExcel(response, list, "企业文化数据");
    }

    /**
     * 获取企业文化详细信息
     */
    @PreAuthorize("@ss.hasPermi('biz:culture:query')")
    @GetMapping(value = "/{cultureId}")
    public AjaxResult getInfo(@PathVariable("cultureId") Long cultureId)
    {
        return success(bizCultureService.selectBizCultureByCultureId(cultureId));
    }

    /**
     * 新增企业文化
     */
    @PreAuthorize("@ss.hasPermi('biz:culture:add')")
    @Log(title = "企业文化", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizCulture bizCulture)
    {
        return toAjax(bizCultureService.insertBizCulture(bizCulture));
    }

    /**
     * 修改企业文化
     */
    @PreAuthorize("@ss.hasPermi('biz:culture:edit')")
    @Log(title = "企业文化", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizCulture bizCulture)
    {
        return toAjax(bizCultureService.updateBizCulture(bizCulture));
    }

    /**
     * 删除企业文化
     */
    @PreAuthorize("@ss.hasPermi('biz:culture:remove')")
    @Log(title = "企业文化", businessType = BusinessType.DELETE)
	@DeleteMapping("/{cultureIds}")
    public AjaxResult remove(@PathVariable Long[] cultureIds)
    {
        return toAjax(bizCultureService.deleteBizCultureByCultureIds(cultureIds));
    }
}
