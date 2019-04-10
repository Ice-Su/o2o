package com.imooc.service;

import com.imooc.dto.ImageHolder;
import com.imooc.dto.ShopExecution;
import com.imooc.entity.Shop;

public interface ShopService {
	
	/**
	 * 根据shopCondition分页返回相应店铺列表
	 * @param shopCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);
	
	/**
	 * 根据店铺Id获取店铺信息
	 * @param id
	 * @return
	 */
	Shop getByShopId(long shopId);
	
	/**
	 * 更新店铺信息，包括图片处理
	 * @param shop
	 * @param thumbnail
	 * @return
	 */
	ShopExecution updateShop(Shop shop,ImageHolder thumbnail);
	
	/**
	 * 注册店铺信息，包括图片处理
	 * @param shop
	 * @param thumbnail
	 * @return
	 */
	ShopExecution addShop(Shop shop,ImageHolder thumbnail);
}
