package com.imooc.service;

import java.util.List;

import com.imooc.dto.ProductCategoryExecution;
import com.imooc.entity.ProductCategory;
import com.imooc.exceptions.ProductCategoryOperationException;

public interface ProductCategoryService {

	/**
	 * 查询指定商店所有的物品种类
	 * 
	 * @param long shopId
	 * @return List<ProductCategory>
	 */
	List<ProductCategory> getProductCategoryList(long shopId);

	/**
	 * 批量插入商品类别
	 * @param productCategoryList
	 * @return
	 * @throws ProductCategoryOperationException
	 */
	ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
			throws ProductCategoryOperationException;
	
	/**
	 * 将此类别下的商品里的类别id置为空，再删除该商品类别
	 * @param productCategoryId
	 * @param shopId
	 * @return
	 */
	ProductCategoryExecution deleteProductCategory(long productCategoryId,long shopId);
}
