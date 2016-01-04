package com.zai360.portal.test.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zai360.portal.test.dao.AccountDao;
import com.zai360.portal.test.dao.impl.AccountDaoImpl;
import com.zai360.portal.test.service.AccountService;
import com.zai360.portal.test.util.Page;
import com.zai360.portal.test.util.PageUtil;

@Service
public class AccountServiceImpl extends AccountDaoImpl implements
		AccountService {
	@Autowired
	private AccountDao accountDao;

	@Override
	public Page findPage(StringBuffer sql4count,String mappermethod,
			StringBuffer sql, int pageNumber, int pageSize) {
		Page page =new Page();
		if (pageNumber == 1) {
			PageUtil.TOTALNUMBER = this.accountDao.findTotal(sql4count);// 查询总条数
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
		page.setPageIndex((pageNumber - 1) * pageSize);// /起始记录数
		List content = this.accountDao.getList(mappermethod, sql);
//		content=ResultUtil.convertList(content);//处理数据库查询结果List
		page.setContent(content);
		return page;
	}


}
