package com.zai360.portal.test.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zai360.portal.test.dao.FindDao;
import com.zai360.portal.test.dao.impl.FindDaoImpl;
import com.zai360.portal.test.service.FindService;
import com.zai360.portal.test.util.Page;
import com.zai360.portal.test.util.PageUtil;
import com.zai360.portal.test.util.ResultUtil;

@Service
public class FindServiceImpl extends FindDaoImpl implements FindService {
	@Autowired
	private FindDao findDao;

	@Override
	public Page<HashMap<String, Object>> findPage(StringBuffer sql4count,
			String mappermethod, StringBuffer sql, int pageNumber, int pageSize,int pageIndex) {
		Page <HashMap<String,Object>> page =new Page<HashMap<String,Object>>();
		if (pageNumber == 1) {
			PageUtil.TOTALNUMBER = this.findDao.getTotal(sql4count);// 查询总条数
			if (PageUtil.TOTALNUMBER % pageSize == 0) {
				PageUtil.TOTALPAGE = PageUtil.TOTALNUMBER / pageSize;
			} else {
				PageUtil.TOTALPAGE = PageUtil.TOTALNUMBER / pageSize + 1;// 换算出总页数
			}
		}
		page.setTotalNumber(PageUtil.TOTALNUMBER);
		page.setTotalPage(PageUtil.TOTALPAGE);
		page.setPageNumber(pageNumber);// ?
		page.setPageSize(pageSize);// ?
		page.setPageIndex(pageIndex);
//		page.setPageIndex((pageNumber - 1) * pageSize);// /起始记录数(需要修改,做参数，不在此计算)
		sql.append(" LIMIT " + pageIndex + "," + pageSize);// 拼接分页limit
		List<HashMap<String, Object>> content = this.findDao.getList(
				mappermethod, sql);
		content=ResultUtil.convertList(content);//处理数据库查询结果List
		page.setContent(content);
		return page;
	}
}
