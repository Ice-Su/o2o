package com.imooc.enums;

public enum ProductStateEnum {
	CHECK(0,"审核中"),OFFLINE(-1,"非法商品"),SUCCESS(1,"操作成功"),PASS(2,"通过认证"),EMPTY(-1000,"空值错误"),INNER_EROR(-1001,"内部系统错误"),
	NULL_PRODUCTID(-1002,"productId为空"),NULL_PRODUCT(-1003,"product信息为空");
	
	private int state;
	private String stateInfo;
	
	private ProductStateEnum(int state,String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
	
	/**
	 * 根据传入的state返回相应的enum值
	 */
	public static ProductStateEnum stateOf(int state) {
		for (ProductStateEnum stateEnum : values()) {
			if (stateEnum.getState() == state) {
				return stateEnum;
			}
		}
		return null;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}
}
