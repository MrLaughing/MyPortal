package com.zai360.portal.test.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.zai360.portal.test.commons.BaseDaoImpl;
import com.zai360.portal.test.dao.FindDao;
import com.zai360.portal.test.util.SQLAdapter;
@Repository
public class FindDaoImpl extends BaseDaoImpl implements FindDao {

	@Override
	public int getTotal(StringBuffer sql) {
	int total=this.getSqlSession().selectOne("common.findtotal", new SQLAdapter(sql.toString()));
		return total;
	}

	@Override
	public List<HashMap<String, Object>> getList(String mappermethod,
			StringBuffer sql) {
		List<HashMap<String, Object>> list=this.getSqlSession().selectList(mappermethod, new SQLAdapter(sql.toString()));
		return list;
	}

	@Override
	public String havecolumn(StringBuffer sql) {
		return this.getSqlSession().selectOne("common.havecolumn", new SQLAdapter(sql.toString()));
	}

}
