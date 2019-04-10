package com.imooc.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.BaseTest;
import com.imooc.entity.ProductCategory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductCategoryDaoTest extends BaseTest{
	@Autowired
	private ProductCategoryDao productCategoryDao;
	
	@Test
	@Ignore
	public void testBQueryProductCategoryList() {
		List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(1L);
		System.out.println(productCategoryList.get(0).getProductCategoryName());
	}
	
	@Test
	@Ignore
	public void testABatchInsertProductCategory() {
		List<ProductCategory> list = new ArrayList<ProductCategory>();
		ProductCategory pc1 = new ProductCategory();
		pc1.setProductCategoryName("测试批量类别增加1");
		pc1.setCreateTime(new Date());
		pc1.setPriority(10);
		pc1.setShopId(1L);
		
		ProductCategory pc2 = new ProductCategory();
		pc2.setProductCategoryName("测试批量类别增加2");
		pc2.setCreateTime(new Date());
		pc2.setPriority(1);
		pc2.setShopId(1L);
		
		list.add(pc1);
		list.add(pc2);
		
		int effect = productCategoryDao.batchInsertProductCategory(list);
		System.out.println(effect);
	}
	
	@Test
	public void testCDeleteProductCategory() {
		long shopId = 1L;
		List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(shopId);
		for (ProductCategory pc : productCategoryList) {
			if (pc.getProductCategoryId() > 2) {
				int effectedNum = productCategoryDao.deleteProductCategory(pc.getProductCategoryId(), shopId);
				System.out.println(effectedNum);
			}
		}
	}
}
