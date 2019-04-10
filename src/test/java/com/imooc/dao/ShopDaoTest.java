package com.imooc.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.BaseTest;
import com.imooc.entity.Area;
import com.imooc.entity.PersonInfo;
import com.imooc.entity.Shop;
import com.imooc.entity.ShopCategory;

public class ShopDaoTest extends BaseTest{

	@Autowired
	private ShopDao shopDao;
	
	@Test
	public void testQueryShopListAndCount() {
		Shop shopCondition = new Shop();
		ShopCategory childCategory = new ShopCategory();
		ShopCategory parentCategory = new ShopCategory();
		parentCategory.setShopCategoryId(3L);
		childCategory.setParent(parentCategory);
		shopCondition.setShopCategory(childCategory);
		List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 5);
		System.out.println("店铺列表的大小:" + shopList.size());
		
		int count = shopDao.queryShopCount(shopCondition);
		System.out.println("店铺总数:" + count);
		
//		ShopCategory shopCategory = new ShopCategory();
//		shopCategory.setShopCategoryId(1L);
//		shopCondition.setShopCategory(shopCategory);
//		
//		shopList = shopDao.queryShopList(shopCondition, 0, 3);
//		System.out.println("店铺列表的大小:" + shopList.size());
//		
//		count = shopDao.queryShopCount(shopCondition);
//		System.out.println("店铺总数:" + count);
 	}
	
	@Test
	@Ignore
	public void testQueryByShopId() {
		long shopId = 1L;
		Shop shop = shopDao.queryByShopId(shopId);
		System.out.println(shop);
	}
	
	@Test
	@Ignore
	public void testInsertShop() {
		Shop shop = new Shop();
		PersonInfo owner = new PersonInfo();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		
		owner.setUserId(1l);
		area.setAreaId(1);
		shopCategory.setShopCategoryId(1l);
		
		shop.setOwner(owner);
		shop.setArea(area);
		shop.setShopCategory(shopCategory);
		shop.setShopName("测试主从店铺");
		shop.setShopDesc("test描述");
		shop.setShopAddr("测试地址");
		shop.setPhone("test");
		shop.setShopImg("test");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(1);
		shop.setAdvice("审核中");
		
		int effectedNum = shopDao.insertShop(shop);
		assertEquals(1, effectedNum);
	}
	
	@Test
	@Ignore
	public void tesUpdateShop() {
		Shop shop = new Shop();

		shop.setShopId(1l);
		shop.setShopDesc("测试描述");
		shop.setShopAddr("测试地址");
		shop.setUpdateTime(new Date());
		
		int effectedNum = shopDao.updateShop(shop);
		assertEquals(1, effectedNum);
	}
}
