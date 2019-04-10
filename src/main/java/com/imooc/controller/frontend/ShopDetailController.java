package com.imooc.controller.frontend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imooc.dto.ProductExecution;
import com.imooc.entity.Product;
import com.imooc.entity.ProductCategory;
import com.imooc.entity.Shop;
import com.imooc.service.ProductCategoryService;
import com.imooc.service.ProductService;
import com.imooc.service.ShopService;
import com.imooc.util.HttpServletRequestUtil;

@Controller
@RequestMapping(value = "/frontend")
public class ShopDetailController {
	@Autowired
	private ShopService shopService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductCategoryService productCategoryService;
	
	/**
	 * 获取店铺的信息以及该店铺下的商品类别列表
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/listshopdetailpageinfo")
	@ResponseBody
	private Map<String, Object> listShopDetailPageInfo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 获取前台传过来的shopId
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		Shop shop = null;
		List<ProductCategory> productCategoryList = null;
		if (shopId != -1) {
			// 获取店铺id为shopId的店铺信息
			shop = shopService.getByShopId(shopId);
			// 获取店铺下面的商品类别列表
			productCategoryList = productCategoryService.getProductCategoryList(shopId);
			modelMap.put("shop", shop);
			modelMap.put("productCategoryList", productCategoryList);
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shopId");
		}
		return modelMap;
	}
	
	/**
	 * 依据查询条件分页列出该店铺下的所有商品
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/listproductsbyshop")
	@ResponseBody
	private Map<String, Object> listProductsByShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 获取页码
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		// 获取每页显示的条数
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		// 获取店铺id
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		// 空值判断
		if ((pageIndex > -1) && (pageSize > -1) && (shopId > -1)) {
			// 尝试获取商品类别id
			long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
			// 尝试获取模糊查找的商品名
			String productName = HttpServletRequestUtil.getString(request, "productName");
			// 组合查询条件
			Product productCondition = compactProductCondition4Search(shopId,productCategoryId,productName);
			// 按照传入的查询条件和分页信息返回相应的商品列表以及总数
			ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
			modelMap.put("productList", pe.getProductList());
			modelMap.put("count", pe.getCount());
			modelMap.put("success", true);
 		} else {
 			modelMap.put("success", false);
 			modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
 		}
		return null;
	}

	/**
	 * 组合查询条件, 并将条件封装到productCondition对象里返回
	 * @param shopId
	 * @param productCategoryId
	 * @param productName
	 * @return
	 */
	private Product compactProductCondition4Search(long shopId, long productCategoryId, String productName) {
		Product productCondition = new Product();
		Shop shop = new Shop();
		shop.setShopId(shopId);
		productCondition.setShop(shop);
		
		if (productCategoryId != -1L) {
			// 查询某个商品类别下的商品列表
			ProductCategory productCategory = new ProductCategory();
			productCategory.setProductCategoryId(productCategoryId);
			productCondition.setProductCategory(productCategory);
		}
		if (productName != null) {
			// 查询商品名带有productName的商品列表
			productCondition.setProductName(productName);
		}
		// 只允许选出状态为上架的商品
		productCondition.setEnableStatus(1);
		return productCondition;
	}
}
