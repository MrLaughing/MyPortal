package com.zai360.portal.test.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zai360.portal.test.commons.BaseDaoImpl;
import com.zai360.portal.test.dao.WriteDao;
import com.zai360.portal.test.util.SQLAdapter;
@Repository
public class WriteDaoImpl extends BaseDaoImpl implements WriteDao{

	@Override
	public void createTable(StringBuffer sql) {
		this.getSqlSession().update("write.createTable", new SQLAdapter(sql.toString()));
		//mybatis自动提交事务？
	}

	@Override
	public void insertRow(StringBuffer sql) {
		this.getSqlSession().insert("write.insertRow", new SQLAdapter(sql.toString()));
	}

	@Override
	public String havetable(StringBuffer sql) {
		return this.getSqlSession().selectOne("write.havetable", new SQLAdapter(sql.toString()));
	}
	
}
