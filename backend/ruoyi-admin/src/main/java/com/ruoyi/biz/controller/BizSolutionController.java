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
import com.ruoyi.biz.domain.BizSolution;
import com.ruoyi.biz.service.IBizSolutionService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 行业解决方案Controller
 * 
 * @author 杨正知
 * @date 2025-12-09
 */
@RestController
@RequestMapping("/biz/solution")
public class BizSolutionController extends BaseController
{
    @Autowired
    private IBizSolutionService bizSolutionService;

    /**
     * 查询行业解决方案列表
     */
    @Anonymous
    // @PreAuthorize("@ss.hasPermi('biz:solution:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizSolution bizSolution)
    {
        startPage();
        List<BizSolution> list = bizSolutionService.selectBizSolutionList(bizSolution);
        return getDataTable(list);
    }

    /**
     * 导出行业解决方案列表
     */
    @PreAuthorize("@ss.hasPermi('biz:solution:export')")
    @Log(title = "行业解决方案", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizSolution bizSolution)
    {
        List<BizSolution> list = bizSolutionService.selectBizSolutionList(bizSolution);
        ExcelUtil<BizSolution> util = new ExcelUtil<BizSolution>(BizSolution.class);
        util.exportExcel(response, list, "行业解决方案数据");
    }

    /**
     * 获取行业解决方案详细信息
     */
    @Anonymous
    //@PreAuthorize("@ss.hasPermi('biz:solution:query')")
    @GetMapping(value = "/{solutionId}")
    public AjaxResult getInfo(@PathVariable("solutionId") Long solutionId)
    {
        return success(bizSolutionService.selectBizSolutionBySolutionId(solutionId));
    }

    /**
     * 新增行业解决方案
     */
    @PreAuthorize("@ss.hasPermi('biz:solution:add')")
    @Log(title = "行业解决方案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizSolution bizSolution)
    {
        return toAjax(bizSolutionService.insertBizSolution(bizSolution));
    }

    /**
     * 修改行业解决方案
     */
    @PreAuthorize("@ss.hasPermi('biz:solution:edit')")
    @Log(title = "行业解决方案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizSolution bizSolution)
    {
        return toAjax(bizSolutionService.updateBizSolution(bizSolution));
    }

    /**
     * 删除行业解决方案
     */
    @PreAuthorize("@ss.hasPermi('biz:solution:remove')")
    @Log(title = "行业解决方案", businessType = BusinessType.DELETE)
	@DeleteMapping("/{solutionIds}")
    public AjaxResult remove(@PathVariable Long[] solutionIds)
    {
        return toAjax(bizSolutionService.deleteBizSolutionBySolutionIds(solutionIds));
    }
}
