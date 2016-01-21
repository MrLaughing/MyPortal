package com.zai360.portal.test.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zai360.portal.test.dao.FindDao;
import com.zai360.portal.test.dao.impl.FindDaoImpl;
import com.zai360.portal.test.service.FindService;
import com.zai360.portal.test.util.Page;
import com.zai360.portal.test.util.ResultUtil;

@Service
public class FindServiceImpl extends FindDaoImpl implements FindService {
	@Autowired
	private FindDao findDao;

	@Override
	public Page<HashMap<String, Object>> findPage(StringBuffer sql4count,
			String mappermethod, StringBuffer sql, int pageNumber, int pageSize) {
		Page<HashMap<String,Object>> page =new Page<HashMap<String,Object>>();
			page.setTotalNumber(this.findDao.getTotal(sql4count));// 查询总条数
			if (page.getTotalNumber() % pageSize == 0) {
				page.setTotalPage(page.getTotalNumber() / pageSize);
			} else {
				page.setTotalPage(page.getTotalNumber() / pageSize + 1);// 换算出总页数
			}
		page.setPageNumber(pageNumber);// ?
		page.setPageSize(pageSize);// ?
//		sql.append(" LIMIT " + pageIndex + "," + pageSize);// 拼接分页limit
		List<HashMap<String, Object>> contents = this.findDao.getList(
				mappermethod, sql);
		contents=ResultUtil.convertList(contents);//处理数据库查询结果List
		page.setContents(contents);
		return page;
	}
}
