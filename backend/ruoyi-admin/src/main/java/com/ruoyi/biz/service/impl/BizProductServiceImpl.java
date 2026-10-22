package com.ruoyi.biz.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.biz.mapper.BizProductMapper;
import com.ruoyi.biz.domain.BizProduct;
import com.ruoyi.biz.service.IBizProductService;

/**
 * 产品解决方案Service业务层处理
 * 
 * @author 杨正知
 * @date 2025-12-08
 */
@Service
public class BizProductServiceImpl implements IBizProductService 
{
    @Autowired
    private BizProductMapper bizProductMapper;

    /**
     * 查询产品解决方案
     * 
     * @param productId 产品解决方案主键
     * @return 产品解决方案
     */
    @Override
    public BizProduct selectBizProductByProductId(Long productId)
    {
        return bizProductMapper.selectBizProductByProductId(productId);
    }

    /**
     * 查询产品解决方案列表
     * 
     * @param bizProduct 产品解决方案
     * @return 产品解决方案
     */
    @Override
    public List<BizProduct> selectBizProductList(BizProduct bizProduct)
    {
        return bizProductMapper.selectBizProductList(bizProduct);
    }

    /**
     * 新增产品解决方案
     * 
     * @param bizProduct 产品解决方案
     * @return 结果
     */
    @Override
    public int insertBizProduct(BizProduct bizProduct)
    {
        bizProduct.setCreateTime(DateUtils.getNowDate());
        return bizProductMapper.insertBizProduct(bizProduct);
    }

    /**
     * 修改产品解决方案
     * 
     * @param bizProduct 产品解决方案
     * @return 结果
     */
    @Override
    public int updateBizProduct(BizProduct bizProduct)
    {
        bizProduct.setUpdateTime(DateUtils.getNowDate());
        return bizProductMapper.updateBizProduct(bizProduct);
    }

    /**
     * 批量删除产品解决方案
     * 
     * @param productIds 需要删除的产品解决方案主键
     * @return 结果
     */
    @Override
    public int deleteBizProductByProductIds(Long[] productIds)
    {
        return bizProductMapper.deleteBizProductByProductIds(productIds);
    }

    /**
     * 删除产品解决方案信息
     * 
     * @param productId 产品解决方案主键
     * @return 结果
     */
    @Override
    public int deleteBizProductByProductId(Long productId)
    {
        return bizProductMapper.deleteBizProductByProductId(productId);
    }
}
