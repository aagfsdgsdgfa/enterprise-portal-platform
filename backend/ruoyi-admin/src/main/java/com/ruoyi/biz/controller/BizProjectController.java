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
import com.ruoyi.biz.domain.BizProject;
import com.ruoyi.biz.service.IBizProjectService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 项目案例Controller
 * 
 * @author 杨正知
 * @date 2025-12-08
 */
@RestController
@RequestMapping("/biz/project")
public class BizProjectController extends BaseController
{
    @Autowired
    private IBizProjectService bizProjectService;

    /**
     * 查询项目案例列表
     */
    @Anonymous
    //@PreAuthorize("@ss.hasPermi('biz:project:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizProject bizProject)
    {
        startPage();
        List<BizProject> list = bizProjectService.selectBizProjectList(bizProject);
        return getDataTable(list);
    }

    /**
     * 导出项目案例列表
     */
    @PreAuthorize("@ss.hasPermi('biz:project:export')")
    @Log(title = "项目案例", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizProject bizProject)
    {
        List<BizProject> list = bizProjectService.selectBizProjectList(bizProject);
        ExcelUtil<BizProject> util = new ExcelUtil<BizProject>(BizProject.class);
        util.exportExcel(response, list, "项目案例数据");
    }

    /**
     * 获取项目案例详细信息
     */
    @Anonymous
    //@PreAuthorize("@ss.hasPermi('biz:project:query')")
    @GetMapping(value = "/{projectId}")
    public AjaxResult getInfo(@PathVariable("projectId") Long projectId)
    {
        return success(bizProjectService.selectBizProjectByProjectId(projectId));
    }

    /**
     * 新增项目案例
     */
    @PreAuthorize("@ss.hasPermi('biz:project:add')")
    @Log(title = "项目案例", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizProject bizProject)
    {
        return toAjax(bizProjectService.insertBizProject(bizProject));
    }

    /**
     * 修改项目案例
     */
    @PreAuthorize("@ss.hasPermi('biz:project:edit')")
    @Log(title = "项目案例", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizProject bizProject)
    {
        return toAjax(bizProjectService.updateBizProject(bizProject));
    }

    /**
     * 删除项目案例
     */
    @PreAuthorize("@ss.hasPermi('biz:project:remove')")
    @Log(title = "项目案例", businessType = BusinessType.DELETE)
	@DeleteMapping("/{projectIds}")
    public AjaxResult remove(@PathVariable Long[] projectIds)
    {
        return toAjax(bizProjectService.deleteBizProjectByProjectIds(projectIds));
    }
}
