package com.ruoyi.biz.service;

import java.util.List;
import com.ruoyi.biz.domain.BizProduct;

/**
 * 产品解决方案Service接口
 * 
 * @author 杨正知
 * @date 2025-12-08
 */
public interface IBizProductService 
{
    /**
     * 查询产品解决方案
     * 
     * @param productId 产品解决方案主键
     * @return 产品解决方案
     */
    public BizProduct selectBizProductByProductId(Long productId);

    /**
     * 查询产品解决方案列表
     * 
     * @param bizProduct 产品解决方案
     * @return 产品解决方案集合
     */
    public List<BizProduct> selectBizProductList(BizProduct bizProduct);

    /**
     * 新增产品解决方案
     * 
     * @param bizProduct 产品解决方案
     * @return 结果
     */
    public int insertBizProduct(BizProduct bizProduct);

    /**
     * 修改产品解决方案
     * 
     * @param bizProduct 产品解决方案
     * @return 结果
     */
    public int updateBizProduct(BizProduct bizProduct);

    /**
     * 批量删除产品解决方案
     * 
     * @param productIds 需要删除的产品解决方案主键集合
     * @return 结果
     */
    public int deleteBizProductByProductIds(Long[] productIds);

    /**
     * 删除产品解决方案信息
     * 
     * @param productId 产品解决方案主键
     * @return 结果
     */
    public int deleteBizProductByProductId(Long productId);
}
