package com.imooc.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.BaseTest;
import com.imooc.dto.ImageHolder;
import com.imooc.dto.ShopExecution;
import com.imooc.entity.Area;
import com.imooc.entity.PersonInfo;
import com.imooc.entity.Shop;
import com.imooc.entity.ShopCategory;
import com.imooc.enums.ShopStateEnum;
import com.imooc.exceptions.ShopOperationException;

public class ShopServiceTest extends BaseTest {
	@Autowired
	private ShopService shopService;

	@Test
	public void testGetShopList() {
		Shop shopCondition = new Shop();
		PersonInfo owner = new PersonInfo();
		owner.setUserId(1L);
		ShopCategory shopCategory = new ShopCategory();
		shopCategory.setShopCategoryId(1L);
		ShopExecution se = shopService.getShopList(shopCondition, 2, 3);
		System.out.println("店铺列表数:" + se.getShopList().size());
		System.out.println("店铺总数:" + se.getCount() + se.getState());
	}

	@Test
	@Ignore
	public void testUpdateShop() throws ShopOperationException, FileNotFoundException {
		Shop shop = new Shop();
		shop.setShopId(1L);
		shop.setShopName("修改后的店铺名称");
		File shopImg = new File("F:\\记录\\_20190117111128.jpg");
		InputStream is = new FileInputStream(shopImg);
		ImageHolder imageHolder = new ImageHolder("21sui.jpg", is);
		ShopExecution shopExecution = shopService.updateShop(shop, imageHolder);
		System.out.println("新的图片地址" + shopExecution.getShop().getShopImg());
	}

	@Test
	@Ignore
	public void testAddShop() throws FileNotFoundException {
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
		shop.setShopName("测试店铺2");
		shop.setShopDesc("test描述2");
		shop.setShopAddr("测试地址2");
		shop.setPhone("test2");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(ShopStateEnum.CHECK.getState());
		shop.setAdvice("审核中");

		File shopImg = new File("F:\\eclipse-workspace\\o2o\\src\\main\\resources\\kaocong.jpg");
		InputStream shopImgInputStream = new FileInputStream(shopImg);
		ImageHolder imageHolder = new ImageHolder(shopImg.getName(), shopImgInputStream);
		ShopExecution shopExecution = shopService.addShop(shop, imageHolder);
		assertEquals(ShopStateEnum.CHECK.getState(), shopExecution.getState());
	}
}
