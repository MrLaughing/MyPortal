package com.zai360.portal.test.service;

import com.zai360.portal.test.dao.ExportDao;
import com.zai360.portal.test.util.QueryEnum;

public interface ExportService extends ExportDao {

	/**
	 * 执行excel导出业务实现
	 * @param query
	 * @param sql
	 * @throws Exception
	 */
	void export(QueryEnum query,StringBuffer sql) throws Exception;
}
