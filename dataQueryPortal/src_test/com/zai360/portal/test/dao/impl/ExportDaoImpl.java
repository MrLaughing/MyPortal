package com.zai360.portal.test.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import com.zai360.portal.test.commons.BaseDaoImpl;
import com.zai360.portal.test.dao.ExportDao;
import com.zai360.portal.test.util.ExportMap;
import com.zai360.portal.test.util.SQLAdapter;
/**
 * 报表导出
 * 实现ExportDao接口方法
 * @author report
 *
 */
@Repository
public class ExportDaoImpl  extends BaseDaoImpl implements ExportDao{
	
	@Override
	public List<HashMap<String, Object>> getList(String mappermethod, StringBuffer sql) {		
		List<HashMap<String, Object>> list=this.getSqlSession().selectList(mappermethod,  new SQLAdapter(sql.toString()));
		return list;
	}

	@Override
	public int getLength(StringBuffer sql) {
	int total=this.getSqlSession().selectOne("common.findtotal", new SQLAdapter(sql.toString()));
		return total;
	}
	
}
