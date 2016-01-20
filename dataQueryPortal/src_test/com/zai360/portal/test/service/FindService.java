package com.zai360.portal.test.service;

import java.util.HashMap;

import com.zai360.portal.test.dao.FindDao;
import com.zai360.portal.test.util.Page;

public interface FindService extends FindDao {
	/**
	 * 
	 * @param page
	 *            page对象
	 * @param sql4count
	 *            查询总条数sql
	 * @param mappermethod
	 *            mapper方法
	 * @param sql
	 *            查询sql
	 * @param pageNumber
	 *            当前页
	 * @param pageSize
	 *            每页记录数
	 * @return
	 */
	Page<HashMap<String, Object>> findPage(StringBuffer sql4count,
			String mappermethod, StringBuffer sql, int pageNumber, int pageSize,int pageIndex);
}
