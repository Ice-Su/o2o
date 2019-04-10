package com.imooc.dao;

import java.util.List;

import com.imooc.entity.Area;

public interface AreaDao {
	
	/**
	 * 查询区域列表
	 * @return areaList
	 */
	List<Area> queryArea();
}