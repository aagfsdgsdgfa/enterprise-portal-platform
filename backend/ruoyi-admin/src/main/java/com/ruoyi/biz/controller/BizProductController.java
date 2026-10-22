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
import com.ruoyi.biz.domain.BizProduct;
import com.ruoyi.biz.service.IBizProductService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 产品解决方案Controller
 * 
 * @author 杨正知
 * @date 2025-12-08
 */
@RestController
@RequestMapping("/biz/product")
public class BizProductController extends BaseController
{
    @Autowired
    private IBizProductService bizProductService;

    /**
     * 查询产品解决方案列表
     */
    @Anonymous
    //@PreAuthorize("@ss.hasPermi('biz:product:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizProduct bizProduct)
    {
        startPage();
        List<BizProduct> list = bizProductService.selectBizProductList(bizProduct);
        return getDataTable(list);
    }

    /**
     * 导出产品解决方案列表
     */
    @PreAuthorize("@ss.hasPermi('biz:product:export')")
    @Log(title = "产品解决方案", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizProduct bizProduct)
    {
        List<BizProduct> list = bizProductService.selectBizProductList(bizProduct);
        ExcelUtil<BizProduct> util = new ExcelUtil<BizProduct>(BizProduct.class);
        util.exportExcel(response, list, "产品解决方案数据");
    }

    /**
     * 获取产品解决方案详细信息
     */
    @Anonymous
    //@PreAuthorize("@ss.hasPermi('biz:product:query')")
    @GetMapping(value = "/{productId}")
    public AjaxResult getInfo(@PathVariable("productId") Long productId)
    {
        return success(bizProductService.selectBizProductByProductId(productId));
    }

    /**
     * 新增产品解决方案
     */
    @PreAuthorize("@ss.hasPermi('biz:product:add')")
    @Log(title = "产品解决方案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizProduct bizProduct)
    {
        return toAjax(bizProductService.insertBizProduct(bizProduct));
    }

    /**
     * 修改产品解决方案
     */
    @PreAuthorize("@ss.hasPermi('biz:product:edit')")
    @Log(title = "产品解决方案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizProduct bizProduct)
    {
        return toAjax(bizProductService.updateBizProduct(bizProduct));
    }

    /**
     * 删除产品解决方案
     */
    @PreAuthorize("@ss.hasPermi('biz:product:remove')")
    @Log(title = "产品解决方案", businessType = BusinessType.DELETE)
	@DeleteMapping("/{productIds}")
    public AjaxResult remove(@PathVariable Long[] productIds)
    {
        return toAjax(bizProductService.deleteBizProductByProductIds(productIds));
    }
}
