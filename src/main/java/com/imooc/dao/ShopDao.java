package com.imooc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imooc.entity.Shop;

/**
   *  商店的持久层
 * @author Administrator
 *
 */
public interface ShopDao {
	/**
	 * 分页查询店铺,可输入的条件有：店铺名(模糊查询),店铺状态,店铺类别,区域Id,owner
	 * @param shopCondition
	 * @param rowIndex 从第几行开始取
	 * @param pageSize 每页多少条数据
	 * @return
	 */
	List<Shop> queryShopList(@Param("shopCondition")Shop shopCondition,
			@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);
	
	/**
	 * 返回queryShopList总数
	 * @param shopCondition
	 * @return
	 */
	int queryShopCount(@Param("shopCondition")Shop shopCondition);
	/**
	 * 通过shopId查询店铺
	 * @param shopId
	 * @return
	 */
	Shop queryByShopId(long shopId);

	/**
	 * 新增店铺
	 * @param shop
	 * @return
	 */
	int insertShop(Shop shop);
	
	/**
	 * 更新店铺信息
	 * @param shop
	 * @return
	 */
	int updateShop(Shop shop);
}
