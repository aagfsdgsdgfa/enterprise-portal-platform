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
import com.ruoyi.biz.domain.BizArticle;
import com.ruoyi.biz.service.IBizArticleService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 文章资讯Controller
 * 
 * @author 杨正知
 * @date 2025-12-08
 */
@RestController
@RequestMapping("/biz/article")
public class BizArticleController extends BaseController
{
    @Autowired
    private IBizArticleService bizArticleService;

    /**
     * 查询文章资讯列表
     */
    @Anonymous
    //@PreAuthorize("@ss.hasPermi('biz:article:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizArticle bizArticle)
    {
        startPage();
        List<BizArticle> list = bizArticleService.selectBizArticleList(bizArticle);
        return getDataTable(list);
    }

    /**
     * 导出文章资讯列表
     */
    @PreAuthorize("@ss.hasPermi('biz:article:export')")
    @Log(title = "文章资讯", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizArticle bizArticle)
    {
        List<BizArticle> list = bizArticleService.selectBizArticleList(bizArticle);
        ExcelUtil<BizArticle> util = new ExcelUtil<BizArticle>(BizArticle.class);
        util.exportExcel(response, list, "文章资讯数据");
    }

    /**
     * 获取文章资讯详细信息
     */
    @Anonymous
    //@PreAuthorize("@ss.hasPermi('biz:article:query')")
    @GetMapping(value = "/{articleId}")
    public AjaxResult getInfo(@PathVariable("articleId") Long articleId)
    {
        return success(bizArticleService.selectBizArticleByArticleId(articleId));
    }

    /**
     * 新增文章资讯
     */
    @PreAuthorize("@ss.hasPermi('biz:article:add')")
    @Log(title = "文章资讯", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizArticle bizArticle)
    {
        return toAjax(bizArticleService.insertBizArticle(bizArticle));
    }

    /**
     * 修改文章资讯
     */
    @PreAuthorize("@ss.hasPermi('biz:article:edit')")
    @Log(title = "文章资讯", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizArticle bizArticle)
    {
        return toAjax(bizArticleService.updateBizArticle(bizArticle));
    }

    /**
     * 删除文章资讯
     */
    @PreAuthorize("@ss.hasPermi('biz:article:remove')")
    @Log(title = "文章资讯", businessType = BusinessType.DELETE)
	@DeleteMapping("/{articleIds}")
    public AjaxResult remove(@PathVariable Long[] articleIds)
    {
        return toAjax(bizArticleService.deleteBizArticleByArticleIds(articleIds));
    }
}
