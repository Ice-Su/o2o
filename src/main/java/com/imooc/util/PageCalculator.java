package com.imooc.util;

public class PageCalculator {
	/**
	 * 通过当前页数和每页的条数计算下次查询的起始位置
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public static int calculatorRowIndex(int pageIndex,int pageSize) {
		return (pageIndex > 0) ? (pageIndex - 1) * pageSize : 0;
	}
}
