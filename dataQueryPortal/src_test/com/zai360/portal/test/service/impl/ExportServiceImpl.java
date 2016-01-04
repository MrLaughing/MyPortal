package com.zai360.portal.test.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zai360.portal.test.dao.ExportDao;
import com.zai360.portal.test.dao.impl.ExportDaoImpl;
import com.zai360.portal.test.service.ExportService;
import com.zai360.portal.test.util.ExportMap;
import com.zai360.portal.test.util.QueryEnum;
import com.zai360.portal.test.util.ResultUtil;

@Service
public class ExportServiceImpl extends ExportDaoImpl implements ExportService {
	@Autowired
	private ExportDao exportDao;

	@Override
	public void export(QueryEnum query, StringBuffer sql) throws Exception {
		System.out.println(sql);// 暂时
		List<HashMap<String, Object>> list = exportDao.getList(
				query.getMappermethod(), sql);
		list=ResultUtil.convertList(list);//处理查询结果list
		ExportMap.export(list, query.getColumns(), query.getFilename(),
				query.getTitles(), query.getSheetName(), query.getWidths());
	}

}
