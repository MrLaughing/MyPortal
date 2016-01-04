package com.zai360.portal.test.service;

import com.zai360.portal.test.dao.AccountDao;
import com.zai360.portal.test.util.Page;
import com.zai360.portal.test.vo.Admin;

public interface AccountService extends AccountDao{
	/**
	 * 
	 * @param page
	 *            page对象
	 * @param sql4count
	 *            查询总条数sql
	 * @param sql
	 *            查询sql
	 * @param pageNumber
	 *            当前页
	 * @param pageSize
	 *            每页记录数
	 * @return
	 */
	Page findPage(StringBuffer sql4count,String mappermethod,
			 StringBuffer sql, int pageNumber, int pageSize);
}
