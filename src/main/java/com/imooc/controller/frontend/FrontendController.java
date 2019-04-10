package com.imooc.controller.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/frontend")
public class FrontendController {
	
	/**
	 * 首页路由
	 * @return
	 */
	@GetMapping(value = "/index")
	private String index() {
		System.out.println("首页");
		return "frontend/index";
	}
	
	/**
	 * 商品列表路由
	 * @return
	 */
	@GetMapping(value = "/shoplist")
	private String showShopList() {
		return "frontend/shoplist";
	}
}
