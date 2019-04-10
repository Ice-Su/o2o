package com.imooc.dao;

import java.util.List;

import com.imooc.entity.ProductImg;

public interface ProductImgDao {
	/**
	 * 查询指定商品下的所有详情图
	 * @param productId
	 * @return
	 */
	List<ProductImg> queryProductImgList(Long productId);
	
	/**
	 * 批量添加商品详情图
	 * @param productImgList
	 * @return
	 */
	int batchInsertProductImg(List<ProductImg> productImgList);
	
	/**
	 * 删除指定商品下的所有详情图
	 * @param productId
	 * @return
	 */
	int deleteProductImgByProductId(long productId);


}
