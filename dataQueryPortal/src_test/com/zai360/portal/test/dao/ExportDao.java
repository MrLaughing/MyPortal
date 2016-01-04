package com.zai360.portal.test.dao;

import java.util.HashMap;
import java.util.List;

import com.zai360.portal.test.commons.BaseDao;
/**
 * 接口类：报表导出Dao层
 * @author report
 * @param <T>
 *
 */
public interface ExportDao extends BaseDao {
	/**
	 * 查询长度
	 * 
	 * @param sql
	 * @return
	 */
	int getLength(StringBuffer sql);
	/**
	 * 执行数据库查询sql
	 * @param mappermethod
	 * @param sql
	 * @return
	 */
	List<HashMap<String, Object>> getList(String mappermethod,StringBuffer sql);

}
