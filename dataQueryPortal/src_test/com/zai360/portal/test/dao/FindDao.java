package com.zai360.portal.test.dao;

import java.util.HashMap;
import java.util.List;
import com.zai360.portal.test.commons.BaseDao;
/**
 * 分页查询
 * @author report
 *
 */
public interface FindDao extends BaseDao {
	/**
	 * 查询总记录数
	 * 
	 * @param sql
	 * @return
	 */
	int getTotal(StringBuffer sql);

	/**
	 * 根据页码和记录数分页查询
	 */
	List<HashMap<String, Object>> getList(String mappermethod, StringBuffer sql);
}
