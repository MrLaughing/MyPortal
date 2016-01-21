package com.zai360.portal.test.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zai360.portal.test.dao.AccountDao;
import com.zai360.portal.test.dao.impl.AccountDaoImpl;
import com.zai360.portal.test.service.AccountService;
import com.zai360.portal.test.util.Page;

@Service
public class AccountServiceImpl extends AccountDaoImpl implements
		AccountService {
	@Autowired
	private AccountDao accountDao;

	@Override
	public Page<HashMap<String, Object>> findPage(StringBuffer sql4count,String mappermethod,
			StringBuffer sql, int pageNumber, int pageSize) {
		Page<HashMap<String, Object>> page =new Page<HashMap<String, Object>>();
			page.setTotalNumber(this.accountDao.findTotal(sql4count));// 查询总条数
			if (page.getTotalNumber() % pageSize == 0) {
				page.setTotalPage(page.getTotalNumber() / pageSize);
			} else {
				page.setTotalPage(page.getTotalNumber() / pageSize + 1);// 换算出总页数
			}
		page.setPageNumber(pageNumber);// ?
		page.setPageSize(pageSize);// ?
		List<HashMap<String, Object>> contents = this.accountDao.getList(mappermethod, sql);
//		contents=ResultUtil.convertList(contents);//处理数据库查询结果List
		page.setContents(contents);
		return page;
	}


}
