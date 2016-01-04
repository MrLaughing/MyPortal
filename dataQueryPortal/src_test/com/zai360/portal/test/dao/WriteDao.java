package com.zai360.portal.test.dao;

import com.zai360.portal.test.commons.BaseDao;

/**
 * 
 * @author report
 * @date 2015年12月31日
 */
public interface WriteDao extends BaseDao {
	/**
	 * 查询表是否存在
	 * @param sql
	 * @return
	 */
	String havetable(StringBuffer sql); 
	/**
	 * 创建表
	 * @param sql
	 */
	void createTable(StringBuffer sql);
	/**
	 * 插入行
	 * @param sql
	 */
	void insertRow(StringBuffer sql);
}
