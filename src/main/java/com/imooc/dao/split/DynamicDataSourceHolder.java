package com.imooc.dao.split;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DynamicDataSourceHolder {
	private static Logger logger = LoggerFactory.getLogger(DynamicDataSourceHolder.class);
	private static ThreadLocal<String> contextHolder = new ThreadLocal<String>();
	
	public static String DB_MASTER = "master";
	public static String DB_SLAVE = "slave";
	
	/**
	 * 获得DbType
	 * @return
	 */
	public static String getDbType() {
		String db = contextHolder.get();
		if (db == null) {
			db = DB_MASTER;
		}
		return db;
	}
	
	/**
	 * 设置线程的DbType
	 * @param db
	 */
	public static void setDbType(String db) {
		logger.debug("所使用的数据源为" + db);
		contextHolder.set(db);
	}
	
	/**
	 * 清理连接类型
	 */
	public static void clearDbType() {
		contextHolder.remove();
	}
}
