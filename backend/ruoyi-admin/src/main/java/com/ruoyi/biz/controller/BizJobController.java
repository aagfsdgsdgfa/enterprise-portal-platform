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
import com.ruoyi.biz.domain.BizJob;
import com.ruoyi.biz.service.IBizJobService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 职位招聘Controller
 * 
 * @author 杨正知
 * @date 2025-12-08
 */
@RestController
@RequestMapping("/biz/job")
public class BizJobController extends BaseController
{
    @Autowired
    private IBizJobService bizJobService;

    /**
     * 查询职位招聘列表
     */
    @Anonymous
    //@PreAuthorize("@ss.hasPermi('biz:job:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizJob bizJob)
    {
        startPage();
        List<BizJob> list = bizJobService.selectBizJobList(bizJob);
        return getDataTable(list);
    }

    /**
     * 导出职位招聘列表
     */
    @PreAuthorize("@ss.hasPermi('biz:job:export')")
    @Log(title = "职位招聘", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizJob bizJob)
    {
        List<BizJob> list = bizJobService.selectBizJobList(bizJob);
        ExcelUtil<BizJob> util = new ExcelUtil<BizJob>(BizJob.class);
        util.exportExcel(response, list, "职位招聘数据");
    }

    /**
     * 获取职位招聘详细信息
     */
    @Anonymous
    //@PreAuthorize("@ss.hasPermi('biz:job:query')")
    @GetMapping(value = "/{jobId}")
    public AjaxResult getInfo(@PathVariable("jobId") Long jobId)
    {
        return success(bizJobService.selectBizJobByJobId(jobId));
    }

    /**
     * 新增职位招聘
     */
    @PreAuthorize("@ss.hasPermi('biz:job:add')")
    @Log(title = "职位招聘", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizJob bizJob)
    {
        return toAjax(bizJobService.insertBizJob(bizJob));
    }

    /**
     * 修改职位招聘
     */
    @PreAuthorize("@ss.hasPermi('biz:job:edit')")
    @Log(title = "职位招聘", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizJob bizJob)
    {
        return toAjax(bizJobService.updateBizJob(bizJob));
    }

    /**
     * 删除职位招聘
     */
    @PreAuthorize("@ss.hasPermi('biz:job:remove')")
    @Log(title = "职位招聘", businessType = BusinessType.DELETE)
	@DeleteMapping("/{jobIds}")
    public AjaxResult remove(@PathVariable Long[] jobIds)
    {
        return toAjax(bizJobService.deleteBizJobByJobIds(jobIds));
    }
}
