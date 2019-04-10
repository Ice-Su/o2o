package com.imooc.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.BaseTest;
import com.imooc.dto.ImageHolder;
import com.imooc.dto.ProductExecution;
import com.imooc.entity.Product;
import com.imooc.entity.ProductCategory;
import com.imooc.entity.Shop;
import com.imooc.enums.ProductStateEnum;
import com.imooc.exceptions.ProductOperationException;

public class ProductServiceTest extends BaseTest{
	@Autowired
	private ProductService productService;
	
	@Test
	@Ignore
	public void testAddProduct() throws ProductOperationException,FileNotFoundException{
		//创建shopId为1且productCategoryId为1的商品示例并给其成员变量赋值
		Product product = new Product();
		Shop shop = new Shop();
		shop.setShopId(1L);
		ProductCategory pc = new ProductCategory();
		pc.setProductCategoryId(1L);
		product.setShop(shop);
		product.setProductCategory(pc);
		product.setProductName("测试商品1");
		product.setProductDesc("测试商品1");
		product.setPriority(20);
		product.setCreateTime(new Date());
		product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
		// 创建缩略图文件流
		File thumbnailFile = new File("F:\\images\\_20190117111128.jpg");
		InputStream is = new FileInputStream(thumbnailFile);
		ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(), is);
		
		// 创建两个商品详情图文件流并将它们添加到详情图列表中
		File prodductImg1 = new File("F:\\images\\shixi.jpg");
		InputStream is1 = new FileInputStream(prodductImg1);
		File prodductImg2 = new File("F:\\images\\xijing.jpg");
		InputStream is2 = new FileInputStream(prodductImg2);
		List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
		productImgList.add(new ImageHolder(prodductImg1.getName(), is1));
		productImgList.add(new ImageHolder(prodductImg2.getName(), is2));
		
		//添加商品并验证
		ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
		assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());
	}
	
	@Test
	public void testUpdateProduct() throws ProductOperationException,FileNotFoundException{
		//创建shopId为1且productCategoryId为1的商品示例并给其成员变量赋值
		Product product = new Product();
		Shop shop = new Shop();
		shop.setShopId(1L);
		ProductCategory pc = new ProductCategory();
		pc.setProductCategoryId(1L);
		product.setProductId(4L);
		product.setShop(shop);
		product.setProductCategory(pc);
		product.setProductName("正式的商品");
		product.setProductDesc("正式的商品描述");
		// 创建缩略图文件流
		File thumbnailFile = new File("F:\\images\\_20190117111128.jpg");
		InputStream is = new FileInputStream(thumbnailFile);
		ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(), is);
		
		// 创建两个商品详情图文件流并将它们添加到详情图列表中
		File prodductImg1 = new File("F:\\images\\shixi.jpg");
		InputStream is1 = new FileInputStream(prodductImg1);
		File prodductImg2 = new File("F:\\images\\xijing.jpg");
		InputStream is2 = new FileInputStream(prodductImg2);
		List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
		productImgList.add(new ImageHolder(prodductImg1.getName(), is1));
		productImgList.add(new ImageHolder(prodductImg2.getName(), is2));
		
		//添加商品并验证
		ProductExecution pe = productService.updateProduct(product, thumbnail, productImgList);
		assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());
	}
}
